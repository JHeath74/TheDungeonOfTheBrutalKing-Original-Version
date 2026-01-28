
package Guild.DawnwardPaladins.Spells;

import SharedData.Guild;
import Spells.Spell;
import java.util.HashMap;
import java.util.Map;

public class DawnwardPaladinsGuildSpellsManager {
    private final Guild guild;
    private final Map<String, Spell> guildSpells = new HashMap<>();

    public DawnwardPaladinsGuildSpellsManager(Guild guild) {
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

    // Add cast methods as in SpellsManager if needed
}
