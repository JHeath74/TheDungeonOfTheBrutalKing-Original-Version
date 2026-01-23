
package Guild.CelestialArcaneOrder.Spells;

import SharedData.Guild;
import Spells.Spell;
import java.util.HashMap;
import java.util.Map;



public class CelestialArcaneOrderGuildSpellsManager {
    private final Guild guild;
    private final Map<String, Spell> guildSpells = new HashMap<>();

    public CelestialArcaneOrderGuildSpellsManager(Guild guild) {
        this.guild = guild;
        // Register core spells for this guild
        registerSpell(new Conjure_Food());
        registerSpell(new Cure());
        registerSpell(new Heal());
        registerSpell(new HolyAegis());
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
