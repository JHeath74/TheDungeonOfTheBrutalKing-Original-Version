package Spells;

import java.util.Map;
import java.util.function.Supplier;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

import SharedData.Guild;
import Guild.SilverwardSentinels.Spells.BlessingOfPurity;
import Guild.SilverwardSentinels.Spells.BlessingofRestoration;
import Guild.SilverwardSentinels.Spells.Dawnbind;
import Guild.SilverwardSentinels.Spells.JudgementBrand;
import Guild.SilverwardSentinels.Spells.Location;
import Guild.SilverwardSentinels.Spells.OathbreakersRuin;
import Guild.SilverwardSentinels.Spells.Port;
import Guild.SilverwardSentinels.Spells.RadiantStrike;
import Guild.SilverwardSentinels.Spells.SanctifiedPurge;
import Guild.SilverwardSentinels.Spells.SmiteOfTheDawn;

// Import Obsidian guild spells so typed suppliers compile like the Silverward ones
import Guild.ObsidianShadowSyndicate.Spells.CripplingShadows;
import Guild.ObsidianShadowSyndicate.Spells.DazingStrike;
import Guild.ObsidianShadowSyndicate.Spells.GreaterHealSpell;
import Guild.ObsidianShadowSyndicate.Spells.MinorHealAndRageSpell;
import Guild.ObsidianShadowSyndicate.Spells.PoisonDagger;
import Guild.ObsidianShadowSyndicate.Spells.ShadowSlash;
import Guild.ObsidianShadowSyndicate.Spells.ShadowStab;
import Guild.ObsidianShadowSyndicate.Spells.SmokeStrike;
import Guild.ObsidianShadowSyndicate.Spells.ThiefsInsight;
import Guild.ObsidianShadowSyndicate.Spells.WhisperLock;

import Guild.ObsidianHexCoven.Spells.ArcaneMend;
import Guild.ObsidianHexCoven.Spells.AstralRift;
import Guild.ObsidianHexCoven.Spells.ChaosHex;
import Guild.ObsidianHexCoven.Spells.Chill_Touch;
import Guild.ObsidianHexCoven.Spells.Cold_Blast;
import Guild.ObsidianHexCoven.Spells.EmberlanceSurge;
import Guild.ObsidianHexCoven.Spells.Fireball;
import Guild.ObsidianHexCoven.Spells.Firebolt;
import Guild.ObsidianHexCoven.Spells.IceBarrier;
import Guild.ObsidianHexCoven.Spells.Light;

// NightShadeHunters spells
import Guild.NightShadeHunters.Spells.CrimsonTrailShot;
import Guild.NightShadeHunters.Spells.CripplingSnare;
import Guild.NightShadeHunters.Spells.DeadeyeFocus;
import Guild.NightShadeHunters.Spells.FieldDressing;
import Guild.NightShadeHunters.Spells.HuntersMarkShot;
import Guild.NightShadeHunters.Spells.SerratedShot;
import Guild.NightShadeHunters.Spells.ShadowSnare;
import Guild.NightShadeHunters.Spells.ShadowStepVeil;
import Guild.NightShadeHunters.Spells.SilencingBolt;
import Guild.NightShadeHunters.Spells.VenomTippedShot;
import Guild.NightShadeHunters.Spells.VoidFangBolt;

/**
 * Lightweight, reflection-friendly SpellFactory used for dev tooling.
 *
 * Note: this variant avoids compile-time dependencies on every Spell class by
 * registering suppliers that construct objects via reflection. It returns
 * Object from createGuildSpell so consumers should cast to the expected type
 * (your runtime `Spell` interface) when running against the full compiled
 * project.
 */
public final class SpellFactory {

    // Use Object suppliers to avoid requiring the Spell interface at compile time
    private static final Map<String, Supplier<Spell>> AURORA_ARCANUM = new HashMap<>();
    private static final Map<String, Supplier<Spell>> CELESTIAL_ARCANE_ORDER = new HashMap<>();
    private static final Map<String, Supplier<Spell>> CRIMSON_BLADES = new HashMap<>();
    private static final Map<String, Supplier<Spell>> CRIMSON_VEIL_ROGUES = new HashMap<>();
    private static final Map<String, Supplier<Spell>> DAWNWARD_PALADINS = new HashMap<>();
    private static final Map<String, Supplier<Spell>> DIRGEWEAVERS_CHORUS = new HashMap<>();
    private static final Map<String, Supplier<Spell>> HARMONIC_LIGHT_ENSEMBLE = new HashMap<>();
    private static final Map<String, Supplier<Spell>> NIGHT_SHADE_HUNTERS = new HashMap<>();
    private static final Map<String, Supplier<Spell>> OBSIDIAN_HEX_COVEN = new HashMap<>();
    private static final Map<String, Supplier<Spell>> OBSIDIAN_SHADOW_SYNDICATE = new HashMap<>();
    private static final Map<String, Supplier<Spell>> SILVERWARD_SENTINELS = new HashMap<>();

    // Auto-discovery: at class-load time (development environment), scan src/Guild/*/Spells
    // and automatically register any classes that appear to be spell implementations.
    static {
        try {
            Path root = Paths.get(System.getProperty("user.dir"));
            Path guildsDir = root.resolve("src").resolve("Guild");
            if (Files.exists(guildsDir) && Files.isDirectory(guildsDir)) {
                Files.list(guildsDir).filter(Files::isDirectory).forEach(guildDir -> {
                    Path spellsDir = guildDir.resolve("Spells");
                    if (!Files.exists(spellsDir) || !Files.isDirectory(spellsDir)) return;
                    try {
                        Files.list(spellsDir).filter(p -> p.toString().endsWith(".java")).forEach(javaFile -> {
                            String className = javaFile.getFileName().toString().replaceFirst("\\.java$", "");
                            String pkg = "Guild." + guildDir.getFileName().toString() + ".Spells";
                            String fqcn = pkg + "." + className;
                            try {
                                Class<?> cls = Class.forName(fqcn);
                                Supplier<Spell> supplier = () -> {
                                    try { return (Spell) cls.getDeclaredConstructor().newInstance(); } catch (Exception e) { return null; }
                                };
                                Map<String, Supplier<Spell>> map = mapForGuildName(guildDir.getFileName().toString());
                                if (map != null) map.putIfAbsent(className, supplier);
                            } catch (ClassNotFoundException cnf) {
                                // not available on classpath yet; ignore
                            } catch (Throwable t) {
                                try { System.out.println("SpellFactory auto-register failed for " + fqcn + " -> " + t.getMessage()); } catch (Exception ignored) { }
                            }
                        });
                    } catch (IOException ioe) {
                        // ignore per-directory listing errors
                    }
                });
            }
        } catch (Exception e) {
            try { System.out.println("SpellFactory auto-discovery failed: " + e.getMessage()); } catch (Exception ignored) {}
        }

        // Ensure common Silverward spells are registered (best-effort):
        try {
            Map<String, Supplier<Spell>> sMap = SILVERWARD_SENTINELS;
            String[] ensure = new String[] {
                "BlessingOfPurity",
                "BlessingofRestoration",
                "Dawnbind",
                "JudgementBrand",
                "Location",
                "OathbreakersRuin",
                "Port",
                "RadiantStrike",
                "SanctifiedPurge",
                "SmiteOfTheDawn"
            };
            for (String n : ensure) {
                sMap.putIfAbsent(n, () -> tryCreateByConvention(n, SharedData.Guild.SILVERWARD_SENTINELS));
            }

            // Also register concrete suppliers for known Silverward spells (typed, no-reflection fallback)
            try {
                sMap.putIfAbsent("BlessingOfPurity", () -> new BlessingOfPurity());
                sMap.putIfAbsent("BlessingofRestoration", () -> new BlessingofRestoration());
                sMap.putIfAbsent("Dawnbind", () -> new Dawnbind());
                sMap.putIfAbsent("JudgementBrand", () -> new JudgementBrand());
                sMap.putIfAbsent("Location", () -> new Location());
                sMap.putIfAbsent("OathbreakersRuin", () -> new OathbreakersRuin());
                sMap.putIfAbsent("Port", () -> new Port());
                sMap.putIfAbsent("RadiantStrike", () -> new RadiantStrike());
                sMap.putIfAbsent("SanctifiedPurge", () -> new SanctifiedPurge());
                sMap.putIfAbsent("SmiteOfTheDawn", () -> new SmiteOfTheDawn());
            } catch (Throwable t) {
                // Ignore if any concrete class is missing/unavailable at runtime
            }
        } catch (Throwable t) {
            try { System.out.println("SpellFactory: failed to ensure Silverward defaults -> " + t.getMessage()); } catch (Exception ignored) {}
        }

        // Ensure ObsidianShadowSyndicate spells are registered (best-effort):
        try {
            Map<String, Supplier<Spell>> oMap = OBSIDIAN_SHADOW_SYNDICATE;
            String[] ensureOb = new String[] {
                "CripplingShadows",
                "DazingStrike",
                "GreaterHealSpell",
                "MinorHealAndRageSpell",
                "PoisonDagger",
                "ShadowSlash",
                "ShadowStab",
                "SmokeStrike",
                "ThiefsInsight",
                "WhisperLock"
            };
            for (String n : ensureOb) {
                oMap.putIfAbsent(n, () -> tryCreateByConvention(n, SharedData.Guild.OBSIDIAN_SHADOW_SYNDICATE));
            }
            // If concrete classes exist on the classpath, optionally add typed suppliers here.
            try {
                oMap.putIfAbsent("CripplingShadows", () -> new CripplingShadows());
                oMap.putIfAbsent("DazingStrike", () -> new DazingStrike());
                oMap.putIfAbsent("GreaterHealSpell", () -> new GreaterHealSpell());
                oMap.putIfAbsent("MinorHealAndRageSpell", () -> new MinorHealAndRageSpell());
                oMap.putIfAbsent("PoisonDagger", () -> new PoisonDagger());
                oMap.putIfAbsent("ShadowSlash", () -> new ShadowSlash());
                oMap.putIfAbsent("ShadowStab", () -> new ShadowStab());
                oMap.putIfAbsent("SmokeStrike", () -> new SmokeStrike());
                oMap.putIfAbsent("ThiefsInsight", () -> new ThiefsInsight());
                oMap.putIfAbsent("WhisperLock", () -> new WhisperLock());
            } catch (Throwable t) {
                // If concrete classes are not present at compile-time this block will not cause runtime failure,
                // but since the concrete classes are imported, compilation will fail if they don't exist — that's
                // intentional when you want compile-time checking. We still wrap to be defensive at runtime.
            }
        } catch (Throwable t) {
            try { System.out.println("SpellFactory: failed to ensure ObsidianShadow defaults -> " + t.getMessage()); } catch (Exception ignored) {}
        }

        // Ensure ObsidianHexCoven spells are registered (best-effort):
        try {
            Map<String, Supplier<Spell>> hMap = OBSIDIAN_HEX_COVEN;
            String[] ensureHex = new String[] {
                "ArcaneMend",
                "AstralRift",
                "ChaosHex",
                "Chill_Touch",
                "Cold_Blast",
                "EmberlanceSurge",
                "Fireball",
                "Firebolt",
                "IceBarrier",
                "Light"
            };
            for (String n : ensureHex) {
                hMap.putIfAbsent(n, () -> tryCreateByConvention(n, SharedData.Guild.OBSIDIAN_HEX_COVEN));
            }
            // Optionally add concrete suppliers if the classes are available on the classpath.
            try {
                hMap.putIfAbsent("ArcaneMend", () -> new ArcaneMend());
                hMap.putIfAbsent("AstralRift", () -> new AstralRift());
                hMap.putIfAbsent("ChaosHex", () -> new ChaosHex());
                hMap.putIfAbsent("Chill_Touch", () -> new Chill_Touch());
                hMap.putIfAbsent("Cold_Blast", () -> new Cold_Blast());
                hMap.putIfAbsent("EmberlanceSurge", () -> new EmberlanceSurge());
                hMap.putIfAbsent("Fireball", () -> new Fireball());
                hMap.putIfAbsent("Firebolt", () -> new Firebolt());
                hMap.putIfAbsent("IceBarrier", () -> new IceBarrier());
                hMap.putIfAbsent("Light", () -> new Light());
            } catch (Throwable t) {
                // defensive: ignore if concrete classes are missing at runtime
            }
         } catch (Throwable t) {
             try { System.out.println("SpellFactory: failed to ensure ObsidianHexCoven defaults -> " + t.getMessage()); } catch (Exception ignored) {}
         }

        // Ensure DawnwardPaladins spells are registered (best-effort):
        try {
            Map<String, Supplier<Spell>> dwpMap = DAWNWARD_PALADINS;
            String[] ensureDawn = new String[] {
                "HolySmite",
                "ManaInfusion",
                "MysticBarrier",
                "PurifyingLight",
                "RadiantAegis",
                "RestoringLight",
                "RighteousFervor",
                "SacredWard",
                "Shield",
                "SancifiedLeech",
                "SanctifiedLeech"
            };
            for (String n : ensureDawn) {
                dwpMap.putIfAbsent(n, () -> tryCreateByConvention(n, SharedData.Guild.DAWNWARD_PALADINS));
            }
            // Optionally add concrete suppliers if Dawnward spell classes exist on the classpath.
            try {
                // no typed suppliers to avoid compile-time dependency
            } catch (Throwable t) {
                // ignore
            }
        } catch (Throwable t) {
            try { System.out.println("SpellFactory: failed to ensure DawnwardPaladins defaults -> " + t.getMessage()); } catch (Exception ignored) {}
        }

         // Ensure NightShadeHunters spells are registered (best-effort):
         try {
             Map<String, Supplier<Spell>> nMap = NIGHT_SHADE_HUNTERS;
             String[] ensureNight = new String[] {
                 "CrimsonTrailShot",
                 "CripplingSnare",
                 "DeadeyeFocus",
                 "FieldDressing",
                 "HuntersMarkShot",
                 "SerratedShot",
                 "ShadowSnare",
                 "ShadowStepVeil",
                 "SilencingBolt",
                 "VenomTippedShot",
                 "VoidFangBolt",
                 
             
             };
             for (String n : ensureNight) {
                 nMap.putIfAbsent(n, () -> tryCreateByConvention(n, SharedData.Guild.NIGHT_SHADE_HUNTERS));
             }
             // Add typed suppliers (compile-time checked) for known NightShade spells.
             try {
                 // Register both the correct 'CrimsonTrailShot' and a common typo 'CrimsonTrailShow' to be forgiving.
                 nMap.putIfAbsent("CrimsonTrailShot", () -> new CrimsonTrailShot());
                 nMap.putIfAbsent("CripplingSnare", () -> new CripplingSnare());
                 nMap.putIfAbsent("DeadeyeFocus", () -> new DeadeyeFocus());
                 nMap.putIfAbsent("FieldDressing", () -> new FieldDressing());
                 nMap.putIfAbsent("HuntersMarkShot", () -> new HuntersMarkShot());
                 nMap.putIfAbsent("SerratedShot", () -> new SerratedShot());
                 nMap.putIfAbsent("ShadowSnare", () -> new ShadowSnare());
                 nMap.putIfAbsent("ShadowStepVeil", () -> new ShadowStepVeil());
                 nMap.putIfAbsent("SilencingBolt", () -> new SilencingBolt());
                 nMap.putIfAbsent("VenomTippedShot", () -> new VenomTippedShot());
                 nMap.putIfAbsent("VoidFangBolt", () -> new VoidFangBolt());
             } catch (Throwable t) {
                 // If concrete classes are missing this will cause compile-time errors when removed; keep defensive.
             }
         } catch (Throwable t) {
             try { System.out.println("SpellFactory: failed to ensure NightShadeHunters defaults -> " + t.getMessage()); } catch (Exception ignored) {}
         }

        // Ensure HarmonicLightEnsemble spells are registered (best-effort):
        try {
            Map<String, Supplier<Spell>> hleMap = HARMONIC_LIGHT_ENSEMBLE;
            String[] ensureHLE = new String[] {
                "AriaOfManasunder",
                "BalladOfMending",
                "BalladOfSiphoningRefrain",
                "BalladOfVenomousVerse",
                "ChantOfPurification",
                "DiscordantChord",
                "DuetofMinfire",
                "ReelOfNervousTremors",
                "RhapsodyOfShatteringChords",
                "ScorchingPreludeRefrain"
            };
            for (String n : ensureHLE) {
                hleMap.putIfAbsent(n, () -> tryCreateByConvention(n, SharedData.Guild.HARMONIC_LIGHT_ENSEMBLE));
            }
            // Optionally add concrete suppliers if the classes are available on the classpath.
            try {
                // Defensive: attempt to add typed suppliers if classes exist (wrapped in try to avoid hard dependency)
                // (No explicit imports added; this will only succeed if classes are present and imported elsewhere.)
            } catch (Throwable t) {
                // ignore
            }
        } catch (Throwable t) {
            try { System.out.println("SpellFactory: failed to ensure HarmonicLightEnsemble defaults -> " + t.getMessage()); } catch (Exception ignored) {}
        }

        // Ensure CrimsonVeilRogues spells are registered (best-effort):
        try {
            Map<String, Supplier<Spell>> cvrMap = CRIMSON_VEIL_ROGUES;
            String[] ensureCvr = new String[] {
                "BlackwireGarrote",
                "DuskBladeInFusion",
                "GhosthandLift",
                "PoisonersWhisper",
                "RazorwindDart",
                "ShadowmendTouch",
                "ShadowPierce",
                "ShadowstepVeil",
                "SilentTakedown",
                "SmokeBloom"
            };
            for (String n : ensureCvr) {
                cvrMap.putIfAbsent(n, () -> tryCreateByConvention(n, SharedData.Guild.CRIMSON_VEIL_ROGUES));
            }
            // Add a couple of forgiving aliases
            try {
                cvrMap.putIfAbsent("ShadowStepVeil", () -> tryCreateByConvention("ShadowstepVeil", SharedData.Guild.CRIMSON_VEIL_ROGUES));
                cvrMap.putIfAbsent("Poisoners Whisper", () -> tryCreateByConvention("PoisonersWhisper", SharedData.Guild.CRIMSON_VEIL_ROGUES));
            } catch (Throwable t) {
                // ignore
            }
        } catch (Throwable t) {
            try { System.out.println("SpellFactory: failed to ensure CrimsonVeilRogues defaults -> " + t.getMessage()); } catch (Exception ignored) {}
        }

        // Ensure CrimsonBlades spells are registered (best-effort):
        try {
            Map<String, Supplier<Spell>> cbMap = CRIMSON_BLADES;
            String[] ensureCb = new String[] {
                "BloodboundVow",
                "DragonfireLunge",
                "EchoingBladeDance",
                "IronheartRally",
                "RendTheHeavens",
                "StormcladCharge",
                "TitanbreakerStrike",
                "UnyieldingSpirit",
                "WarlordsCommand",
                "CrimsonFury" // small extra to help compatibility
            };
            for (String n : ensureCb) {
                cbMap.putIfAbsent(n, () -> tryCreateByConvention(n, SharedData.Guild.CRIMSON_BLADES));
            }
            // forgiving aliases
            try {
                cbMap.putIfAbsent("Dragonfire Lunge", () -> tryCreateByConvention("DragonfireLunge", SharedData.Guild.CRIMSON_BLADES));
                cbMap.putIfAbsent("Echoing Blade Dance", () -> tryCreateByConvention("EchoingBladeDance", SharedData.Guild.CRIMSON_BLADES));
            } catch (Throwable t) {
                // ignore
            }
        } catch (Throwable t) {
            try { System.out.println("SpellFactory: failed to ensure CrimsonBlades defaults -> " + t.getMessage()); } catch (Exception ignored) {}
        }

        // Ensure AuroraArcanum spells are registered (best-effort):
        try {
            Map<String, Supplier<Spell>> aaMap = AURORA_ARCANUM;
            String[] ensureAa = new String[] {
                "ArcaneMissile",
                "AstralStep",
                "CelestialWard",
                "EchoOfEternity",
                "ElementalRay",
                "EtherealChains",
                "IllusoryDouble",
                "ManaSurge",
                "MindProbe",
                "Starfall",
                "TimeDialation",
                "VoidEcho"
            };
            for (String n : ensureAa) {
                aaMap.putIfAbsent(n, () -> tryCreateByConvention(n, SharedData.Guild.AURORA_ARCANUM));
            }
            // forgiving aliases and common corrections
            try {
                aaMap.putIfAbsent("Arcan Missile", () -> tryCreateByConvention("ArcanMissile", SharedData.Guild.AURORA_ARCANUM));
                aaMap.putIfAbsent("Astral Step", () -> tryCreateByConvention("AstralStep", SharedData.Guild.AURORA_ARCANUM));
                aaMap.putIfAbsent("CelestialWard", () -> tryCreateByConvention("celestialWard", SharedData.Guild.AURORA_ARCANUM));
                aaMap.putIfAbsent("EchoOfEternity", () -> tryCreateByConvention("EchoOfEternigt", SharedData.Guild.AURORA_ARCANUM));
                aaMap.putIfAbsent("TimeDilation", () -> tryCreateByConvention("TimeDialation", SharedData.Guild.AURORA_ARCANUM));
            } catch (Throwable t) {
                // ignore
            }
        } catch (Throwable t) {
            try { System.out.println("SpellFactory: failed to ensure AuroraArcanum defaults -> " + t.getMessage()); } catch (Exception ignored) {}
        }
    }

    // Map directory name to the appropriate in-memory registry map.
    private static Map<String, Supplier<Spell>> mapForGuildName(String folderName) {
        if (folderName == null) return null;
        return switch (folderName) {
            case "AuroraArcanum" -> AURORA_ARCANUM;
            case "CelestialArcaneOrder" -> CELESTIAL_ARCANE_ORDER;
            case "CrimsonBlades" -> CRIMSON_BLADES;
            case "CrimsonVeilRogues" -> CRIMSON_VEIL_ROGUES;
            case "DawnwardPaladins" -> DAWNWARD_PALADINS;
            case "DirgeweaversChorus" -> DIRGEWEAVERS_CHORUS;
            case "HarmonicLightEnsemble" -> HARMONIC_LIGHT_ENSEMBLE;
            case "NightShadeHunters" -> NIGHT_SHADE_HUNTERS;
            case "ObsidianHexCoven" -> OBSIDIAN_HEX_COVEN;
            case "ObsidianShadowSyndicate" -> OBSIDIAN_SHADOW_SYNDICATE;
            case "SilverwardSentinels" -> SILVERWARD_SENTINELS;
            default -> null;
        };
    }

    // Try to instantiate a spell class using the common package convention.
    private static Spell tryCreateByConvention(String className, Guild guild) {
        if (className == null || guild == null) return null;
        String pkgBase;
        switch (guild) {
            case AURORA_ARCANUM: pkgBase = "Guild.AuroraArcanum.Spells."; break;
            case CELESTIAL_ARCANE_ORDER: pkgBase = "Guild.CelestialArcaneOrder.Spells."; break;
            case CRIMSON_BLADES: pkgBase = "Guild.CrimsonBlades.Spells."; break;
            case CRIMSON_VEIL_ROGUES: pkgBase = "Guild.CrimsonVeilRogues.Spells."; break;
            case DAWNWARD_PALADINS: pkgBase = "Guild.DawnwardPaladins.Spells."; break;
            case DIRGEWEAVERS_CHORUS: pkgBase = "Guild.DirgeweaversChorus.Spells."; break;
            case HARMONIC_LIGHT_ENSEMBLE: pkgBase = "Guild.HarmonicLightEnsemble.Spells."; break;
            case NIGHT_SHADE_HUNTERS: pkgBase = "Guild.NightShadeHunters.Spells."; break;
            case OBSIDIAN_HEX_COVEN: pkgBase = "Guild.ObsidianHexCoven.Spells."; break;
            case OBSIDIAN_SHADOW_SYNDICATE: pkgBase = "Guild.ObsidianShadowSyndicate.Spells."; break;
            case SILVERWARD_SENTINELS: pkgBase = "Guild.SilverwardSentinels.Spells."; break;
            default: return null;
        }
        String fqcn = pkgBase + className;
        try {
            Class<?> cls = Class.forName(fqcn);
            Object o = cls.getDeclaredConstructor().newInstance();
            if (o instanceof Spell) return (Spell) o;
            return null;
        } catch (Throwable t) {
            return null;
        }
    }

    // Public factory method used throughout the project.
    public static Spell createGuildSpell(String spellName, Guild guild) {
        if (spellName == null || guild == null) return null;
        Map<String, Supplier<Spell>> map = switch (guild) {
            case AURORA_ARCANUM -> AURORA_ARCANUM;
            case CELESTIAL_ARCANE_ORDER -> CELESTIAL_ARCANE_ORDER;
            case CRIMSON_BLADES -> CRIMSON_BLADES;
            case CRIMSON_VEIL_ROGUES -> CRIMSON_VEIL_ROGUES;
            case DAWNWARD_PALADINS -> DAWNWARD_PALADINS;
            case DIRGEWEAVERS_CHORUS -> DIRGEWEAVERS_CHORUS;
            case HARMONIC_LIGHT_ENSEMBLE -> HARMONIC_LIGHT_ENSEMBLE;
            case NIGHT_SHADE_HUNTERS -> NIGHT_SHADE_HUNTERS;
            case OBSIDIAN_HEX_COVEN -> OBSIDIAN_HEX_COVEN;
            case OBSIDIAN_SHADOW_SYNDICATE -> OBSIDIAN_SHADOW_SYNDICATE;
            case SILVERWARD_SENTINELS -> SILVERWARD_SENTINELS;
            default -> null;
        };

        if (map == null) return null;

        Supplier<Spell> supplier = map.get(spellName);
        if (supplier == null) {
            // attempt by convention (package + class name)
            supplier = () -> tryCreateByConvention(spellName, guild);
        }

        try {
            return supplier == null ? null : supplier.get();
        } catch (Throwable t) {
            return null;
        }
    }
}
