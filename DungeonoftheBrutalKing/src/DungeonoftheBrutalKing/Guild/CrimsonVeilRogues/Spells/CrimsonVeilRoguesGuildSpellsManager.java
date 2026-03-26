
// src/Guild/CrimsonVeilRogues/Spells/CrimsonVeilRoguesGuildSpellsManager.java
package Guild.CrimsonVeilRogues.Spells;

import SharedData.Guild;
import Spells.Spell;
import DungeonoftheBrutalKing.Charecter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrimsonVeilRoguesGuildSpellsManager {
    private final Guild guild;
    private final Map<String, Spell> guildSpells = new HashMap<>();

    public CrimsonVeilRoguesGuildSpellsManager(Guild guild) {
        if (guild == null) throw new IllegalArgumentException("Guild cannot be null.");
        this.guild = guild;
        registerDefaultSpells();
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
        return new HashMap<>(guildSpells);
    }

    private void registerDefaultSpells() {
        String[] defaultSpells = {
            "BlackwireGarrote",
            "DuskBladeInFusion",
            "GhosthandLift",
            "PoisonersWhisper",
            "RazorwindDart",
            "ShadowmendTouch",
            "ShadowPierce",
            "ShadowstepVeil",
            "SilentTakedown",
            "SmokeBloom"
        };
        for (String spellName : defaultSpells) {
            Spell spell = Spell.createGuildSpell(spellName, guild);
            if (spell != null) {
                registerSpell(spell);
            }
        }
    }

    // Basic cast methods for consistency
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
