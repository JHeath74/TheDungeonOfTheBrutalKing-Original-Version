
// `src/Guild/DawnwardPaladins/Spells/DawnwardPaladinsGuildSpellsManager.java`

package Guild.DawnwardPaladins.Spells;

import SharedData.Guild;
import Spells.Spell;
import Spells.SpellFactory;
import DungeonoftheBrutalKing.Charecter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DawnwardPaladinsGuildSpellsManager {
    private final Guild guild;
    private final Map<String, Spell> guildSpells = new HashMap<>();

    public DawnwardPaladinsGuildSpellsManager(Guild guild) {
        this.guild = guild;
        registerDefaultSpells();
    }

    public void registerSpell(Spell spell) {
        if (spell != null && spell.isGuildSpell() && spell.getSpellGuild() == guild) {
            guildSpells.put(spell.getName().toLowerCase(), spell);
        }
    }

    private void registerDefaultSpells() {
        String[] defaultSpells = {
                "MysticBarrier",
                "ManaInfusion",
                "Shield",
                "SanctifiedLeech",
                "RadiantAegis",
                "PurifyingLight",
                "SacredStrike",
                "RestoringLight",
                "RighteousFervor",
                "HolySmite",
        };

        for (String spellName : defaultSpells) {
            Spell spell = SpellFactory.createGuildSpell(spellName, guild);
            if (spell != null) {
                registerSpell(spell);
            }
        }
    }

    public Spell getSpell(String name) {
        return guildSpells.get(name.toLowerCase());
    }

    public Map<String, Spell> getAllSpells() {
        return new HashMap<>(guildSpells);
    }

    public void cast(String spellName) {
        Spell spell = getSpell(spellName);
        if (spell != null) {
            spell.cast();
        }
    }

    public void cast(String spellName, Charecter caster) {
        Spell spell = getSpell(spellName);
        if (spell != null) {
            spell.cast(caster);
        }
    }

    public void cast(String spellName, Charecter caster, Charecter target) {
        Spell spell = getSpell(spellName);
        if (spell != null) {
            spell.cast(caster, target);
        }
    }

    public void cast(String spellName, Charecter caster, List<Charecter> targets) {
        Spell spell = getSpell(spellName);
        if (spell != null) {
            spell.cast(caster, targets);
        }
    }

    public void castWithIntelligence(String spellName, int intelligence) {
        Spell spell = getSpell(spellName);
        if (spell != null) {
            spell.castWithIntelligence(intelligence);
        }
    }

    public void castWithWisdom(String spellName, int wisdom) {
        Spell spell = getSpell(spellName);
        if (spell != null) {
            spell.cast(wisdom);
        }
    }

    public void castWithWisdomAndIntelligence(String spellName, int wisdom, int intelligence) {
        Spell spell = getSpell(spellName);
        if (spell != null) {
            spell.cast(wisdom, intelligence);
        }
    }
}
