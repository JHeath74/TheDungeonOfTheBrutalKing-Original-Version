package DungeonoftheBrutalKing.Guild.NightShadeHunters.Spells;

import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Character;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NightShadeHuntersGuildSpellsManager {
    private final Guild guild;
    private final Map<String, Spell> guildSpells = new HashMap<>();

    public NightShadeHuntersGuildSpellsManager(Guild guild) {
        this.guild = guild;
        registerAllDefaultSpells();
    }

    private void registerAllDefaultSpells() {
        // Register NightShade Hunters spells here, e.g.:
        registerSpell(new CrimsonTrailShot());
        registerSpell(new CripplingSnare());
        registerSpell(new DeadeyeFocus());
        registerSpell(new FieldDressing());
        registerSpell(new HuntersMarkShot());
        registerSpell(new SerratedShot());
        registerSpell(new ShadowSnare());
        registerSpell(new ShadowStepVeil());
        registerSpell(new SilencingBolt());
        registerSpell(new VenomTippedShot());
        registerSpell(new VoidFangBolt());
    }

    public void registerSpell(Spell spell) {
        if (spell == null) return;
        if (!spell.isGuildSpell()) return;
        if (spell.getSpellGuild() != guild) return;

        String name = spell.getName();
        if (name == null) return;

        String key = name.trim().toLowerCase();
        if (key.isEmpty()) return;

        guildSpells.put(key, spell);
    }

    public Spell getSpell(String name) {
        if (name == null) return null;
        String key = name.trim().toLowerCase();
        if (key.isEmpty()) return null;
        return guildSpells.get(key);
    }

    public Map<String, Spell> getAllSpells() {
        return new HashMap<>(guildSpells);
    }

    public void cast(String spellName) {
        Spell spell = getSpell(spellName);
        if (spell != null) spell.cast();
    }

    public void cast(String spellName, Character caster) {
        Spell spell = getSpell(spellName);
        if (spell != null) spell.cast(caster);
    }

    public void cast(String spellName, Character caster, Character target) {
        Spell spell = getSpell(spellName);
        if (spell != null) spell.cast(caster, target);
    }

    public void cast(String spellName, Character caster, List<Character> targets) {
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