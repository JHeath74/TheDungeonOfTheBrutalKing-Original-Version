package Guild.CelestialArcaneOrder.Spells;

import SharedData.Guild;
import Spells.Spell;
import DungeonoftheBrutalKing.Charecter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CelestialArcaneOrderGuildSpellsManager {
    private final Guild guild;
    private final Map<String, Spell> guildSpells = new HashMap<>();

    public CelestialArcaneOrderGuildSpellsManager(Guild guild) {
        this.guild = guild;
        registerDefaultSpells();
    }

    public void registerSpell(Spell spell) {
        if (spell != null && spell.isGuildSpell() && spell.getSpellGuild() == guild) {
            guildSpells.put(spell.getName().toLowerCase(), spell);
        }
    }

    public Spell getSpell(String name) {
        if (name == null) return null;
        return guildSpells.get(name.toLowerCase());
    }

    public Map<String, Spell> getAllSpells() {
        return new HashMap<>(guildSpells);
    }

    private void registerDefaultSpells() {
        String[] defaultSpells = {
            "Conjure_Food",
            "Cure",
            "Heal",
            "HolyAegis",
            "PurifyingChains",
            "RadiantBolt",
            "StellarFlare",
            "SunfireTouch",
            "DivineIntervention",
            "AstralWard"
        };
        for (String spellName : defaultSpells) {
            Spell spell = Spell.createGuildSpell(spellName, guild);
            if (spell != null) {
                registerSpell(spell);
            }
        }
    }

    // Cast spell by name with various overloads
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

    public void castSpell(String name, Charecter caster) {
        Spell spell = getSpell(name);
        if (spell != null) {
            spell.cast(caster);
        }
    }

    public void castSpell(String name) {
        Spell spell = getSpell(name);
        if (spell != null) {
            spell.cast();
        }
    }

    public void castSpell(String name, int toonWisdom) {
        Spell spell = getSpell(name);
        if (spell != null) {
            spell.cast(toonWisdom);
        }
    }

    public void castSpellWithIntelligence(String name, int toonIntelligence) {
        Spell spell = getSpell(name);
        if (spell != null) {
            spell.castWithIntelligence(toonIntelligence);
        }
    }

    public void castSpell(String name, int toonWisdom, int toonIntelligence) {
        Spell spell = getSpell(name);
        if (spell != null) {
            spell.cast(toonWisdom, toonIntelligence);
        }
    }
}
