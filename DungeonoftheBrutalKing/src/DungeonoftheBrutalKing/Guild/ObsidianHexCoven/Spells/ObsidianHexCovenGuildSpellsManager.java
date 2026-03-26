package Guild.ObsidianHexCoven.Spells;

import SharedData.Guild;
import Spells.Spell;
import DungeonoftheBrutalKing.Charecter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObsidianHexCovenGuildSpellsManager {
    private final Guild guild;
    private final Map<String, Spell> guildSpells = new HashMap<>();

    public ObsidianHexCovenGuildSpellsManager(Guild guild) {
        this.guild = guild;

        // If this manager is for the Obsidian Hex Coven, pre-register the coven's spells.
        // We add them directly into the internal map to ensure they're available for lookup
        // even if a particular Spell declares itself as non-guild (e.g. Chaos Hex).
        if (this.guild == Guild.OBSIDIAN_HEX_COVEN) {
            Spell[] defaults = new Spell[] {
                new ArcaneMend(),
                new ChaosHex(),
                new Chill_Touch(),
                new Cold_Blast(),
                new Fireball(),
                new Firebolt(),
                new Light(),
                new AstralRift(),
                new IceBarrier(),
                new EmberlanceSurge()
            };
            for (Spell s : defaults) {
                if (s != null) {
                    guildSpells.put(s.getName().toLowerCase(), s);
                }
            }
        }
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

    // Cast spell by name (no parameters)
    public void cast(String spellName) {
        Spell spell = getSpell(spellName);
        if (spell != null) {
            spell.cast();
        }
    }

    // Cast spell by name and caster
    public void cast(String spellName, Charecter caster) {
        Spell spell = getSpell(spellName);
        if (spell != null) {
            spell.cast(caster);
        }
    }

    // Cast spell by name, caster, and target
    public void cast(String spellName, Charecter caster, Charecter target) {
        Spell spell = getSpell(spellName);
        if (spell != null) {
            spell.cast(caster, target);
        }
    }

    // Cast spell by name, caster, and list of targets
    public void cast(String spellName, Charecter caster, List<Charecter> targets) {
        Spell spell = getSpell(spellName);
        if (spell != null) {
            spell.cast(caster, targets);
        }
    }

    // Cast spell by name with intelligence
    public void castWithIntelligence(String spellName, int intelligence) {
        Spell spell = getSpell(spellName);
        if (spell != null) {
            spell.castWithIntelligence(intelligence);
        }
    }

    // Cast spell by name with wisdom
    public void castWithWisdom(String spellName, int wisdom) {
        Spell spell = getSpell(spellName);
        if (spell != null) {
            spell.cast(wisdom);
        }
    }

    // Cast spell by name with wisdom and intelligence
    public void castWithWisdomAndIntelligence(String spellName, int wisdom, int intelligence) {
        Spell spell = getSpell(spellName);
        if (spell != null) {
            spell.cast(wisdom, intelligence);
        }
    }
}