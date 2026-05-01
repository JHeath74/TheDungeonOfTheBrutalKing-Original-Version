package DungeonoftheBrutalKing.DevTools;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;

/**
 * Dialog for developers to start/stop JVM log capture into a per-run folder under <b>logs/</b>.
 * <p>
 * Allows toggling log capture, displays the current log folder, and provides a button to open the folder.
 * Integrates with {@link GameLogCapture} for log management.
 * UI updates automatically to reflect the current capture state.
 */
public final class LoggingToolDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private final JLabel statusLabel = new JLabel();
    private final JLabel pathLabel = new JLabel();
    private final JButton start = new JButton("Start Capture");
    private final JButton stop = new JButton("Stop Capture");
    private final JButton openFolder = new JButton("Open Folder");

    @Override
    public void setVisible(boolean b) {
        SwingUtilities.invokeLater(() -> super.setVisible(b));
    }

    public LoggingToolDialog(JFrame parent) {
        super(parent, "Logging Tool", true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JLabel info = new JLabel("<html>Captures JVM logs and System.out/System.err into a per-run folder under <b>logs/</b>.</html>");

        JButton close = new JButton("Close");

        start.addActionListener(_ -> {
            GameLogCapture.install();
            refreshUi();
        });

        stop.addActionListener(_ -> {
            GameLogCapture.uninstall();
            refreshUi();
        });

        openFolder.addActionListener(_ -> {
            openCurrentFolder();
            refreshUi();
        });
        close.addActionListener(_ -> dispose());

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        info.setAlignmentX(Component.LEFT_ALIGNMENT);
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        pathLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        center.add(info);
        center.add(Box.createVerticalStrut(10));
        center.add(statusLabel);
        center.add(Box.createVerticalStrut(6));
        center.add(pathLabel);
        center.add(Box.createVerticalStrut(10));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.add(start);
        buttons.add(stop);
        buttons.add(openFolder);
        buttons.setAlignmentX(Component.LEFT_ALIGNMENT);
        center.add(buttons);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(close);

        setLayout(new BorderLayout(8, 8));
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        refreshUi();

        pack();
        setMinimumSize(new Dimension(520, 220));
        setLocationRelativeTo(parent);
    }

    /** Updates the UI to reflect the current log capture state and folder. */
    private void refreshUi() {
        boolean on = GameLogCapture.isInstalled();
        start.setEnabled(!on);
        stop.setEnabled(on);

        Path folder = GameLogCapture.getCurrentRunFolder();
        boolean folderExists = folder != null && folder.toFile().exists();
        openFolder.setEnabled(folderExists);

        statusLabel.setText("Status: " + (on ? "CAPTURING" : "OFF"));
        String pathText = (folder == null) ? "(none)" : folder.toAbsolutePath().toString();
        pathLabel.setText("Folder: " + pathText);
    }

    /** Attempts to open the current log folder in the system file explorer. */
    private void openCurrentFolder() {
        Path folder = GameLogCapture.getCurrentRunFolder();
        if (folder == null) return;

        try {
            File file = folder.toFile();
            if (file.exists() && Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
        } catch (Exception ignored) {
            // best-effort
        }
    }
}
