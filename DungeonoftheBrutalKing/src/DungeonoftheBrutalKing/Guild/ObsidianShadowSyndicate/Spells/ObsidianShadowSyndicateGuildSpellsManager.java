package DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Spells;

import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Spells.PoisonDagger;
import DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Spells.SmokeStrike;
import DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Spells.ThiefsInsight;
import DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Spells.WhisperLock;
import DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Spells.ShadowSlash;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import DungeonoftheBrutalKing.Charecter;

public final class ObsidianShadowSyndicateGuildSpellsManager {

    private final Guild guild;
    private final Map<String, Spell> guildSpells = new HashMap<>();

    public ObsidianShadowSyndicateGuildSpellsManager(Guild guild) {
        this.guild = guild;
        registerAllSpells();
    }

    // Register all Obsidian Shadow Syndicate spells here
    private void registerAllSpells() {
        // core OSS spells
        registerSpell(new ShadowSlash());
        registerSpell(new CripplingShadows());

        // healing / utility
        registerSpell(new DazingStrike());
        registerSpell(new GreaterHealSpell());
        registerSpell(new MinorHealAndRageSpell());

        // new requested spells
        registerSpell(new PoisonDagger());
        registerSpell(new ShadowStab());
        registerSpell(new SmokeStrike());
        registerSpell(new ThiefsInsight());
        registerSpell(new WhisperLock());
    }

    public void registerSpell(Spell spell) {
        if (spell != null && spell.isGuildSpell() && spell.getSpellGuild() == guild) {
            guildSpells.put(spell.getName().toLowerCase(), spell);
        }
    }

    public Spell getSpell(String name) {
        if (name == null) {
            return null;
        }
        return guildSpells.get(name.toLowerCase());
    }

    public Map<String, Spell> getAllSpells() {
        return Collections.unmodifiableMap(guildSpells);
    }

    // Convenience methods to cast spells by name
    public void castSpell(String name) {
        Spell spell = getSpell(name);
        if (spell != null) {
            spell.cast();
        }
    }

    public void castSpell(String name, int wisdom) {
        Spell spell = getSpell(name);
        if (spell != null) {
            spell.cast(wisdom);
        }
    }

    public void castSpell(String name, int wisdom, int intelligence) {
        Spell spell = getSpell(name);
        if (spell != null) {
            spell.cast(wisdom, intelligence);
        }
    }

    public void castSpell(String name, Charecter caster) {
        Spell spell = getSpell(name);
        if (spell != null) {
            spell.cast(caster);
        }
    }

    public void castSpell(String name, Charecter caster, Charecter target) {
        Spell spell = getSpell(name);
        if (spell != null) {
            spell.cast(caster, target);
        }
    }

    public void castSpell(String name, Charecter caster, List<Charecter> targets) {
        Spell spell = getSpell(name);
        if (spell != null) {
            spell.cast(caster, targets);
        }
    }
}
