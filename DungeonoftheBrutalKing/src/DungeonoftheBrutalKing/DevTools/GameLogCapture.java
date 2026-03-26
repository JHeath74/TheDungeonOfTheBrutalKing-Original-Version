
package DevTools;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.logging.*;

/**
 * Captures JVM-wide logs into a dedicated per-run folder under `logs/`.
 * Also mirrors System.out/System.err into JUL so it lands in the same files.
 */
public final class GameLogCapture {
    private static final Object LOCK = new Object();

    private static volatile boolean installed;
    private static volatile Handler fileHandler;
    private static volatile PrintStream originalOut;
    private static volatile PrintStream originalErr;
    private static volatile Path currentRunFolder;

    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private GameLogCapture() { }

    public static boolean isInstalled() {
        return installed;
    }

    public static Path getCurrentRunFolder() {
        return currentRunFolder;
    }

    /**
     * Installs a rotating file handler under `logs/game_yyyyMMdd_HHmmss/`.
     * Safe to call multiple times.
     */
    public static void install() {
        synchronized (LOCK) {
            if (installed) return;

            Path runFolder = ensureRunFolder();
            currentRunFolder = runFolder;

            try {
                // Pattern: logs/game_.../game.%g.log, creating game.0.log, game.1.log, ...
                Path pattern = runFolder.resolve("game.%g.log");
                int limitBytes = 5 * 1024 * 1024; // 5MB per file
                int fileCount = 10;               // keep up to 10 files per run
                boolean append = false;

                fileHandler = new FileHandler(pattern.toString(), limitBytes, fileCount, append);
                fileHandler.setLevel(Level.ALL);
                fileHandler.setEncoding(StandardCharsets.UTF_8.name());
                fileHandler.setFormatter(new SimpleFormatter());

                Logger root = LogManager.getLogManager().getLogger("");
                root.setLevel(Level.ALL);
                root.addHandler(fileHandler);

                originalOut = System.out;
                originalErr = System.err;

                System.setOut(new PrintStream(new JulOutputStream(Logger.getLogger("STDOUT"), Level.INFO), true, StandardCharsets.UTF_8));
                System.setErr(new PrintStream(new JulOutputStream(Logger.getLogger("STDERR"), Level.SEVERE), true, StandardCharsets.UTF_8));

                installed = true;

                Logger.getLogger(GameLogCapture.class.getName())
                        .log(Level.INFO, "Log capture installed. Folder: {0}", runFolder.toAbsolutePath());
            } catch (IOException ex) {
                // If file logging fails, do not break the game.
                ex.printStackTrace();
                cleanupOnFailure();
            }
        }
    }

    public static void uninstall() {
        synchronized (LOCK) {
            if (!installed) return;

            try {
                Logger root = LogManager.getLogManager().getLogger("");
                if (fileHandler != null) {
                    root.removeHandler(fileHandler);
                    fileHandler.flush();
                    fileHandler.close();
                }
            } catch (RuntimeException ignored) {
                // best-effort
            } finally {
                fileHandler = null;
            }

            if (originalOut != null) System.setOut(originalOut);
            if (originalErr != null) System.setErr(originalErr);

            originalOut = null;
            originalErr = null;
            installed = false;
            currentRunFolder = null;
        }
    }

    private static void cleanupOnFailure() {
        try {
            if (fileHandler != null) {
                fileHandler.close();
            }
        } catch (RuntimeException ignored) { }
        fileHandler = null;

        if (originalOut != null) System.setOut(originalOut);
        if (originalErr != null) System.setErr(originalErr);

        originalOut = null;
        originalErr = null;
        installed = false;
    }

    private static Path ensureRunFolder() {
        String sessionId = LocalDateTime.now().format(TS);

        Path base = Paths.get("logs");
        Path run = base.resolve("game_" + sessionId);

        try {
            Files.createDirectories(run);
            return run;
        } catch (IOException ex) {
            Path tmp = Paths.get(System.getProperty("java.io.tmpdir"), "game_logs", "game_" + sessionId);
            try {
                Files.createDirectories(tmp);
                return tmp;
            } catch (IOException ex2) {
                return run; // may fail later, but keep behavior consistent
            }
        }
    }

    private static final class JulOutputStream extends OutputStream {
        private final Logger logger;
        private final Level level;
        private final StringBuilder buffer = new StringBuilder(256);

        private JulOutputStream(Logger logger, Level level) {
            this.logger = Objects.requireNonNull(logger, "logger");
            this.level = Objects.requireNonNull(level, "level");
        }

        @Override
        public void write(int b) {
            char c = (char) b;
            if (c == '\n') {
                flushBuffer();
            } else if (c != '\r') {
                buffer.append(c);
            }
        }

        @Override
        public void flush() {
            flushBuffer();
        }

        private void flushBuffer() {
            if (buffer.length() == 0) return;
            String msg = buffer.toString();
            buffer.setLength(0);
            logger.log(level, msg);
        }
    }
}
