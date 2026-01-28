
// src/Guild/CrimsonBlades/Spells/GuildSpellsManager.java
package Guild.CrimsonBlades.Spells;

import SharedData.Guild;
import Spells.Spell;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CrimsonBladesGuildSpellsManager {
    private final Guild guild;
    private final Map<String, Spell> guildSpells = new HashMap<>();

    public CrimsonBladesGuildSpellsManager(Guild guild) {
        if (guild == null) throw new IllegalArgumentException("Guild cannot be null.");
        this.guild = guild;
    }

    public void registerSpell(Spell spell) {
        if (spell == null) return;
        if (spell.isGuildSpell() && spell.getSpellGuild() == guild) {
            guildSpells.put(spell.getName().toLowerCase(), spell);
        }
    }

    public Spell getSpell(String name) {
        if (name == null) return null;
        return guildSpells.get(name.toLowerCase());
    }

    public Map<String, Spell> getAllSpells() {
        return Collections.unmodifiableMap(new HashMap<>(guildSpells));
    }

    // Add cast methods as in SpellsManager if needed
}
