
// src/Guild/AuroraArcanum/Spells/AuroraArcanumGuildSpellsManager.java
package Guild.AuroraArcanum.Spells;

import SharedData.Guild;
import Spells.Spell;
import java.util.HashMap;
import java.util.Map;

public class AuroraArcanumGuildSpellsManager {
    private final Guild guild;
    private final Map<String, Spell> guildSpells = new HashMap<>();

    public AuroraArcanumGuildSpellsManager(Guild guild) {
        this.guild = guild;
    }

    public void registerSpell(Spell spell) {
        if (spell.isGuildSpell() && spell.getSpellGuild() == guild) {
            guildSpells.put(spell.getName().toLowerCase(), spell);
        }
    }

    public Spell getSpell(String name) {
        return guildSpells.get(name.toLowerCase());
    }

    public Map<String, Spell> getAllSpells() {
        return new HashMap<>(guildSpells);
    }

    public void registerDefaultSpells() {
        String[] defaultSpells = {
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
            "TimeDilation",
            "VoidEcho",
        };
        for (String spellName : defaultSpells) {
            Spell spell = Spell.createGuildSpell(spellName, guild);
            registerSpell(spell);
        }
    }
}
