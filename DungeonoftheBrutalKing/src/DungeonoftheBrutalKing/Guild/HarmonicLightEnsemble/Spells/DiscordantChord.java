
// `src/Guild/HarmonicLightEnsemble/Spells/DiscordantChord.java`
package DungeonoftheBrutalKing.Guild.HarmonicLightEnsemble.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;

public class DiscordantChord implements Spell {

    private static final String NAME = "Discordant Chord";
    private static final String DESCRIPTION =
            "A discordant chord that rattles the mind, disrupting focus and weakening foes.";

    private static final Guild SPELL_GUILD = Guild.HARMONIC_LIGHT_ENSEMBLE;

    private static final int REQUIRED_WISDOM = 5;
    private static final int REQUIRED_MAGIC_POINTS = 6;

    @Override
    public String getName() { return NAME; }

    @Override
    public String getDescription() { return DESCRIPTION; }

    public boolean canCast(Character caster) {
        return caster != null
                && caster.getWisdom() >= REQUIRED_WISDOM
                && caster.getMagicPoints() >= REQUIRED_MAGIC_POINTS;
    }

    @Override
    public void cast(Character caster) {
        if (caster == null) return;
        if (!canCast(caster)) return;
    }

    @Override
    public void cast(Character caster, Enemies target) {
        if (caster == null || target == null) return;
        if (!canCast(caster)) return;

        int mpAfterCost = caster.getMagicPoints() - REQUIRED_MAGIC_POINTS;
        caster.setMagicPoints(Math.max(0, mpAfterCost));

        int power = Math.max(caster.getWisdom(), caster.getIntelligence());
        int scaling = Math.max(1, power / 6);

        boolean appliedDebuff = false;
        try {
            target.getClass().getMethod("setDazed", boolean.class).invoke(target, true);
            appliedDebuff = true;
        } catch (Exception ignored) {
        }

        if (!appliedDebuff) {
            int damage = Math.max(1, 8 + scaling);
            try {
                int hp = (int) target.getClass().getMethod("getHitPoints").invoke(target);
                int newHp = Math.max(0, hp - damage);
                target.getClass().getMethod("setHitPoints", int.class).invoke(target, newHp);
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public boolean isGuildSpell() { return true; }

    @Override
    public Guild getSpellGuild() { return SPELL_GUILD; }

    @Override
    public int getRequiredMagicPoints() { return REQUIRED_MAGIC_POINTS; }

    @Override
    public void cast(int toonWisdom) { }

    @Override
    public void castWithIntelligence(int toonIntelligence) { }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) { }

    @Override
    public void cast(Character caster, List<Character> allCharacters) { }

    @Override
    public void cast() { }

    @Override
    public void cast(Character caster, Character target) {
        if (caster == null || target == null) return;
        if (!canCast(caster)) return;
    }

    @Override
    public void castWithStrength(Character enemy, double d) { }
}
