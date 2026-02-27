
package Spells;

import java.util.Map;
import java.util.function.Supplier;

import SharedData.Guild;

// Aurora Arcanum imports (existing)
import Guild.AuroraArcanum.Spells.ArcaneMissile;
import Guild.AuroraArcanum.Spells.AstralStep;
import Guild.AuroraArcanum.Spells.CelestialWard;
import Guild.AuroraArcanum.Spells.EchoOfEternity;
import Guild.AuroraArcanum.Spells.ElementalRay;
import Guild.AuroraArcanum.Spells.EtherealChains;
import Guild.AuroraArcanum.Spells.IllusoryDouble;
import Guild.AuroraArcanum.Spells.ManaSurge;
import Guild.AuroraArcanum.Spells.MindProbe;
import Guild.AuroraArcanum.Spells.Starfall;
import Guild.AuroraArcanum.Spells.TimeDialation;
import Guild.AuroraArcanum.Spells.VoidEcho;
import Guild.CelestialArcaneOrder.Spells.AstralGuidance;
import Guild.CelestialArcaneOrder.Spells.AstralWard;
import Guild.CelestialArcaneOrder.Spells.Conjure_Food;
import Guild.CelestialArcaneOrder.Spells.Cure;
import Guild.CelestialArcaneOrder.Spells.DivineIntervention;
import Guild.CelestialArcaneOrder.Spells.Heal;
import Guild.CelestialArcaneOrder.Spells.HolyAegis;
import Guild.CelestialArcaneOrder.Spells.PurifyingChains;
import Guild.CelestialArcaneOrder.Spells.RadiantBolt;
import Guild.CelestialArcaneOrder.Spells.StellarFlare;
import Guild.CelestialArcaneOrder.Spells.SunfireTouch;
import Guild.CrimsonBlades.Spells.BloodboundVow;
import Guild.CrimsonBlades.Spells.DragonfireLunge;
import Guild.CrimsonBlades.Spells.EchoingBladeDance;
import Guild.CrimsonBlades.Spells.IronheartRally;
import Guild.CrimsonBlades.Spells.RendTheHeavens;
import Guild.CrimsonBlades.Spells.StormcladCharge;
import Guild.CrimsonBlades.Spells.TitanbreakerStrike;
import Guild.CrimsonBlades.Spells.UnyieldingSpirit;
import Guild.CrimsonBlades.Spells.WarlordsCommand;
import Guild.CrimsonVeilRogues.Spells.BlackwireGarrote;
import Guild.CrimsonVeilRogues.Spells.DuskBladeInFusion;
import Guild.CrimsonVeilRogues.Spells.GhosthandLift;
import Guild.CrimsonVeilRogues.Spells.PoisonersWhisper;
import Guild.CrimsonVeilRogues.Spells.RazorwindDart;
import Guild.CrimsonVeilRogues.Spells.ShadowPierce;
import Guild.CrimsonVeilRogues.Spells.ShadowmendTouch;
import Guild.CrimsonVeilRogues.Spells.ShadowstepVeil;
import Guild.CrimsonVeilRogues.Spells.SilentTakedown;
import Guild.CrimsonVeilRogues.Spells.SmokeBloom;
import Guild.DawnwardPaladins.Spells.HolySmite;
import Guild.DawnwardPaladins.Spells.ManaInfusion;
import Guild.DawnwardPaladins.Spells.MysticBarrier;
import Guild.DawnwardPaladins.Spells.PurifyingLight;
import Guild.DawnwardPaladins.Spells.RadiantAegis;
import Guild.DawnwardPaladins.Spells.RestoringLight;
import Guild.DawnwardPaladins.Spells.RighteousFervor;
import Guild.DawnwardPaladins.Spells.SacredWard;
import Guild.DawnwardPaladins.Spells.SanctifiedLeech;
import Guild.DawnwardPaladins.Spells.Shield;
import Guild.DirgeweaversChorus.Spells.CripplingChant;
import Guild.DirgeweaversChorus.Spells.DiscordantDuet;
import Guild.DirgeweaversChorus.Spells.DiscordantHex;
import Guild.DirgeweaversChorus.Spells.DreadVerse;
import Guild.DirgeweaversChorus.Spells.HealingChant;
import Guild.DirgeweaversChorus.Spells.RendingRefrain;
import Guild.DirgeweaversChorus.Spells.RequiemDrain;
import Guild.DirgeweaversChorus.Spells.SappingDirge;
import Guild.DirgeweaversChorus.Spells.SonicRequiem;
import Guild.DirgeweaversChorus.Spells.SunderArmour;

public class SpellFactory {

    private static final Map<String, Supplier<Spell>> AURORA_ARCANUM = Map.ofEntries(
            Map.entry("ArcaneMissile", ArcaneMissile::new),
            Map.entry("AstralStep", AstralStep::new),
            Map.entry("CelestialWard", CelestialWard::new),
            Map.entry("EchoOfEternity", EchoOfEternity::new),
            Map.entry("ElementalRay", ElementalRay::new),
            Map.entry("EtherealChains", EtherealChains::new),
            Map.entry("IllusoryDouble", IllusoryDouble::new),
            Map.entry("ManaSurge", ManaSurge::new),
            Map.entry("MindProbe", MindProbe::new),
            Map.entry("Starfall", Starfall::new),
            Map.entry("TimeDialation", TimeDialation::new),
            Map.entry("VoidEcho", VoidEcho::new)
    );


    
    // TODO: Populate these with your spell classes once created.
    private static final Map<String, Supplier<Spell>> CELESTIAL_ARCANE_ORDER = Map.ofEntries(
            Map.entry("AstralGuidance", AstralGuidance::new),
            Map.entry("AstralWard", AstralWard::new),
            Map.entry("ConjureFood", Conjure_Food::new),
            Map.entry("Cure", Cure::new),
            Map.entry("DivineIntervention", DivineIntervention::new),
            Map.entry("Heal", Heal::new),
            Map.entry("HolyAegis", HolyAegis::new),
            Map.entry("PurifyingChains", PurifyingChains::new),
            Map.entry("RadiantBolt", RadiantBolt::new),
            Map.entry("StellarFlare", StellarFlare::new),
            Map.entry("SunfireTouch", SunfireTouch::new)
    );
    
    private static final Map<String, Supplier<Spell>> CRIMSON_BLADES = Map.ofEntries(
            Map.entry("Bloodboundvow", BloodboundVow::new),
            Map.entry("dragonfirelunge", DragonfireLunge::new),
            Map.entry("echoingbladedance", EchoingBladeDance::new),
            Map.entry("ironheartrally", IronheartRally::new),
            Map.entry("rendtheheavens", RendTheHeavens::new),
            Map.entry("stormcladcharge", StormcladCharge::new),
            Map.entry("titanbreakerstrike", TitanbreakerStrike::new),
            Map.entry("unyieldingspirit", UnyieldingSpirit::new),
            Map.entry("warlords command", WarlordsCommand::new)
    );
    private static final Map<String, Supplier<Spell>> CRIMSON_VEIL_ROGUES = Map.ofEntries(
            Map.<String, Supplier<Spell>>entry("BlackwireGarrote", () -> new BlackwireGarrote()),
            Map.<String, Supplier<Spell>>entry("DuskBladeInFusion", () -> new DuskBladeInFusion()),
            Map.<String, Supplier<Spell>>entry("GhosthandLift", () -> new GhosthandLift()),
            Map.<String, Supplier<Spell>>entry("PoisonersWhisper", () -> new PoisonersWhisper()),
            Map.<String, Supplier<Spell>>entry("RazorwindDart", () -> new RazorwindDart()),
            Map.<String, Supplier<Spell>>entry("ShadowmendTouch", () -> new ShadowmendTouch()),
            Map.<String, Supplier<Spell>>entry("ShadowPierce", () -> new ShadowPierce()),
            Map.<String, Supplier<Spell>>entry("ShadowstepVeil", () -> new ShadowstepVeil()),
            Map.<String, Supplier<Spell>>entry("SilentTakedown", () -> new SilentTakedown()),
            Map.<String, Supplier<Spell>>entry("SmokeBloom", () -> new SmokeBloom())
    );
    
    private static final Map<String, Supplier<Spell>> DAWNWARD_PALADINS = Map.ofEntries(
            Map.entry("MysticBarrier", MysticBarrier::new),
            Map.entry("ManaInfusion", ManaInfusion::new),
            Map.entry("Shield", Shield::new),
            Map.entry("SanctifiedLeech", SanctifiedLeech::new),
            Map.entry("RadiantAegis", RadiantAegis::new),
            Map.entry("PurifyingLight", PurifyingLight::new),
            Map.entry("SacredStrike", SacredWard::new),
            Map.entry("RestoringLight", RestoringLight::new),
            Map.entry("RighteousFervor", RighteousFervor::new),
            Map.entry("HolySmite", HolySmite::new)
    );
    private static final Map<String, Supplier<Spell>> DIRGEWEAVERS_CHORUS = Map.ofEntries(
            Map.entry("CripplingChant", CripplingChant::new),
            Map.entry("DiscordantDuet", DiscordantDuet::new),
            Map.entry("DiscordantHex", DiscordantHex::new),
            Map.entry("DreadVerse", DreadVerse::new),
            Map.entry("HealingChant", HealingChant::new),
            Map.entry("RendingRefrain", RendingRefrain::new),
            Map.entry("RequiemDrain", RequiemDrain::new),
            Map.entry("SappingDirge", SappingDirge::new),
            Map.entry("SonicRequiem", SonicRequiem::new),
            Map.entry("SunderArmour", SunderArmour::new)
    );
    private static final Map<String, Supplier<Spell>> HARMONIC_LIGHT_ENSEMBLE = Map.of();
    private static final Map<String, Supplier<Spell>> NIGHT_SHADE_HUNTERS = Map.of();
    private static final Map<String, Supplier<Spell>> OBSIDIANEX_COVEN = Map.of();
    private static final Map<String, Supplier<Spell>> OBSIDIAN_SHADOW_SYNDICATE = Map.of();
    private static final Map<String, Supplier<Spell>> SILVERWARD_SENTINELS = Map.of();

    public static Spell createGuildSpell(String spellName, Guild guild) {
        if (spellName == null || spellName.isBlank()) {
            throw new IllegalArgumentException("Unknown spell: " + spellName);
        }
        if (guild == null) {
            throw new IllegalArgumentException("Unknown guild: " + guild);
        }

        Supplier<Spell> supplier;
        switch (guild) {
            case AURORA_ARCANUM:
                supplier = AURORA_ARCANUM.get(spellName);
                break;
            case CELESTIAL_ARCANE_ORDER:
                supplier = CELESTIAL_ARCANE_ORDER.get(spellName);
                break;

            // Requested guild cases:
            case CRIMSON_BLADES:
                supplier = CRIMSON_BLADES.get(spellName);
                break;
            case CRIMSON_VEIL_ROGUES:
                supplier = CRIMSON_VEIL_ROGUES.get(spellName);
                break;
            case DAWNWARD_PALADINS:
                supplier = DAWNWARD_PALADINS.get(spellName);
                break;
            case DIRGEWEAVERS_CHORUS:
                supplier = DIRGEWEAVERS_CHORUS.get(spellName);
                break;
            case HARMONILIC_LIGHT_ENSEMBLE:
                supplier = HARMONIC_LIGHT_ENSEMBLE.get(spellName);
                break;
            case NIGHT_SHADE_HUNTERS:
                supplier = NIGHT_SHADE_HUNTERS.get(spellName);
                break;
            case OBSIDIAN_HEX_COVEN:
                supplier = OBSIDIANEX_COVEN.get(spellName);
                break;
            case OBSIDIAN_SHADOW_SYNDICATE:
                supplier = OBSIDIAN_SHADOW_SYNDICATE.get(spellName);
                break;
            case SILVERWARD_SENTINELS:
                supplier = SILVERWARD_SENTINELS.get(spellName);
                break;

            default:
                throw new IllegalArgumentException("Unknown guild: " + guild);
        }

        if (supplier == null) {
            throw new IllegalArgumentException("Unknown spell: " + spellName);
        }
        return supplier.get();
    }
}
