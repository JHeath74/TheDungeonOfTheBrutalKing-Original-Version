
// `src/Guild/HarmonicLightEnsemble/Spells/HarmonicLightEnsembleGuildSpellsManager.java`
package Guild.HarmonicLightEnsemble.Spells;

import SharedData.Guild;
import Spells.Spell;
import DungeonoftheBrutalKing.Charecter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HarmonicLightEnsembleGuildSpellsManager {
    private final Guild guild;
    private final Map<String, Spell> guildSpells = new HashMap<>();

    public HarmonicLightEnsembleGuildSpellsManager(Guild guild) {
        this.guild = guild;
        registerAllDefaultSpells();
    }

    private void registerAllDefaultSpells() {
        // Register renamed minstrel-style spells (implementations should remain unchanged).
        registerSpell(new BalladOfMending());
        registerSpell(new BalladOfSiphoningRefrain());
        registerSpell(new BalladOfVenomousVerse());
        registerSpell(new ChantOfPurification());
        registerSpell(new DiscordantChord());
        registerSpell(new DuetOfMindfire());
        registerSpell(new ReelOfNervousTremors());
        registerSpell(new RhapsodyOfShatteringChords());
        registerSpell(new ScorchingPreludeRefrain());
        registerSpell(new AriaOfManasunder());
    }

    public void registerSpell(Spell spell) {
        if (spell == null) return;
        if (spell.isGuildSpell() && spell.getSpellGuild() == guild) {
            String key = (spell.getName() == null) ? "" : spell.getName().toLowerCase();
            if (!key.isEmpty()) {
                guildSpells.put(key, spell);
            }
        }
    }

    public Spell getSpell(String name) {
        if (name == null) return null;
        return guildSpells.get(name.toLowerCase());
    }

    public Map<String, Spell> getAllSpells() {
        return new HashMap<>(guildSpells);
    }

    public void cast(String spellName) {
        Spell spell = getSpell(spellName);
        if (spell != null) spell.cast();
    }

    public void cast(String spellName, Charecter caster) {
        Spell spell = getSpell(spellName);
        if (spell != null) spell.cast(caster);
    }

    public void cast(String spellName, Charecter caster, Charecter target) {
        Spell spell = getSpell(spellName);
        if (spell != null) spell.cast(caster, target);
    }

    public void cast(String spellName, Charecter caster, List<Charecter> targets) {
        Spell spell = getSpell(spellName);
        if (spell != null) spell.cast(caster, targets);
    }

    public void castWithIntelligence(String spellName, int intelligence) {
        Spell spell = getSpell(spellName);
        if (spell != null) spell.castWithIntelligence(intelligence);
    }

    public void castWithWisdom(String spellName, int wisdom) {
        Spell spell = getSpell(spellName);
        if (spell != null) spell.cast(wisdom);
    }

    public void castWithWisdomAndIntelligence(String spellName, int wisdom, int intelligence) {
        Spell spell = getSpell(spellName);
        if (spell != null) spell.cast(wisdom, intelligence);
    }
}
