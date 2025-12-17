
package Spells;

import SharedData.Guild;
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


// Add other CelestialArcanOrder spells as needed

public class SpellFactory {
    public static Spell createGuildSpell(String spellName, Guild guild) {
        switch (guild) {
            case AURORA_ARCNUM:
                switch (spellName) {
                    case "ArcaneMissile": return new ArcaneMissile();
                    case "AstralStep": return new AstralStep();
                    case "CelestialWard": return new CelestialWard();
                    case "EchoOfEternity": return new EchoOfEternity();
                    case "ElementalRay": return new ElementalRay();
                    case "EtherealChains": return new EtherealChains();
                    case "IllusoryDouble": return new IllusoryDouble();
                    case "ManaSurge": return new ManaSurge();
                    case "MindProbe": return new MindProbe();
                    case "Starfall": return new Starfall();
                    case "TimeDialation": return new TimeDialation();
                    case "VoidEcho": return new VoidEcho();
                    default: throw new IllegalArgumentException("Unknown spell: " + spellName);
                }
            case CELESTIAL_ARCANE_ORDER:
                switch (spellName) {
                 
                    // Add other spells here
                    default: throw new IllegalArgumentException("Unknown spell: " + spellName);
                }
            default:
                throw new IllegalArgumentException("Unknown guild: " + guild);
        }
    }
}
