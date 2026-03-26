package DevTools;

import Spells.SpellFactory;
import SharedData.Guild;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.PrintWriter;

public final class SpellFactoryDevRunner {

    public static void main(String[] args) {
        System.out.println("Starting SpellFactory dev-runner...");
        Path root = Paths.get(System.getProperty("user.dir"));
        Path guildsDir = root.resolve("src").resolve("Guild");
        List<String> successes = new ArrayList<>();
        List<String> failures = new ArrayList<>();

        if (!Files.exists(guildsDir) || !Files.isDirectory(guildsDir)) {
            System.out.println("Cannot find src/Guild directory at: " + guildsDir.toAbsolutePath());
            return;
        }

        try {
            List<Path> guildDirs = Files.list(guildsDir).filter(Files::isDirectory).collect(Collectors.toList());
            for (Path guildDir : guildDirs) {
                String folderName = guildDir.getFileName().toString();
                Guild guild = folderNameToGuild(folderName);
                if (guild == null) {
                    System.out.println("Skipping unknown guild folder: " + folderName);
                    continue;
                }

                Path spellsDir = guildDir.resolve("Spells");
                if (!Files.exists(spellsDir) || !Files.isDirectory(spellsDir)) continue;

                List<Path> javaFiles = Files.list(spellsDir).filter(p -> p.toString().endsWith(".java")).collect(Collectors.toList());
                for (Path jf : javaFiles) {
                    String className = jf.getFileName().toString().replaceFirst("\\.java$", "");
                    String canonical = guild.name() + ":" + className;
                    try {
                        Object s = SpellFactory.createGuildSpell(className, guild);
                        if (s == null) throw new RuntimeException("Factory returned null");
                        successes.add(canonical + " -> " + s.getClass().getName());
                        System.out.println("OK: " + canonical + " -> " + s.getClass().getName());
                    } catch (Throwable t) {
                        failures.add(canonical + " -> " + t.toString());
                        System.out.println("FAIL: " + canonical + " -> " + t.toString());
                    }
                }
            }
        } catch (IOException ioe) {
            System.out.println("IO error scanning guilds: " + ioe.getMessage());
        }

        Path report = root.resolve("spell-instantiation-report.txt");
        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(report))) {
            pw.println("SpellFactory Dev Runner Report\n");
            pw.println("Successes:");
            for (String s : successes) pw.println("  " + s);
            pw.println();
            pw.println("Failures:");
            for (String f : failures) pw.println("  " + f);
        } catch (IOException ioe) {
            System.out.println("Failed to write report: " + ioe.getMessage());
        }

        System.out.println("Done. Report written to: " + report.toAbsolutePath());
    }

    private static Guild folderNameToGuild(String folderName) {
        if (folderName == null) return null;
        return switch (folderName) {
            case "AuroraArcanum" -> Guild.AURORA_ARCANUM;
            case "CelestialArcaneOrder" -> Guild.CELESTIAL_ARCANE_ORDER;
            case "CrimsonBlades" -> Guild.CRIMSON_BLADES;
            case "CrimsonVeilRogues" -> Guild.CRIMSON_VEIL_ROGUES;
            case "DawnwardPaladins" -> Guild.DAWNWARD_PALADINS;
            case "DirgeweaversChorus" -> Guild.DIRGEWEAVERS_CHORUS;
            case "HarmonicLightEnsemble" -> Guild.HARMONILIC_LIGHT_ENSEMBLE;
            case "NightShadeHunters" -> Guild.NIGHT_SHADE_HUNTERS;
            case "ObsidianHexCoven" -> Guild.OBSIDIAN_HEX_COVEN;
            case "ObsidianShadowSyndicate" -> Guild.OBSIDIAN_SHADOW_SYNDICATE;
            case "SilverwardSentinels" -> Guild.SILVERWARD_SENTINELS;
            default -> null;
        };
    }
}