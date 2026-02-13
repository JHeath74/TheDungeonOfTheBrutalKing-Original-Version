package Guild.CrimsonBlades.Spells;

import SharedData.Guild;
import Spells.Spell;
import DungeonoftheBrutalKing.Charecter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class CrimsonBladesGuildSpellsManager {
    private final Guild guild;
    private final Map<String, Spell> guildSpells = new ConcurrentHashMap<>();

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
        return Collections.unmodifiableMap(guildSpells);
    }

    public void castSpell(String spellName, Charecter caster) {
        Spell spell = getSpell(spellName);
        if (spell != null) {
            spell.cast(caster);
        }
    }

    public void castSpell(String spellName, Charecter caster, Charecter target) {
        Spell spell = getSpell(spellName);
        if (spell != null) {
            spell.cast(caster, target);
        }
    }

    public void castSpell(String spellName, Charecter caster, List<Charecter> allCharacters) {
        Spell spell = getSpell(spellName);
        if (spell != null) {
            spell.cast(caster, allCharacters);
        }
    }
}
