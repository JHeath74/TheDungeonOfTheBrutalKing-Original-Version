
// `src/Guild/HarmonicLightEnsemble/Spells/SongOfMindfireDuet.java`
package DungeonoftheBrutalKing.Guild.HarmonicLightEnsemble.Spells;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Status.StatusType;

public class DuetOfMindfire implements Spell {

    private static final String NAME = "Duet of Mindfire";
    private static final String DESCRIPTION =
            "A searing duet that wounds the target and may ignite them, probe their mind, or conjure an illusory double.";

    private static final Guild SPELL_GUILD = Guild.HARMONIC_LIGHT_ENSEMBLE;

    private static final int REQUIRED_WISDOM = 5;
    private static final int REQUIRED_MAGIC_POINTS = 6;

    private static final int STATUS_CHANCE_PERCENT = 10;
    private static final StatusType ON_HIT_STATUS = StatusType.FIRE_STATUS;
    private static final int STATUS_DURATION_TURNS = 3;
    private static final int STATUS_VALUE = 1;

    private static final int MINDPROBE_CHANCE_PERCENT = 10;
    private static final StatusType MINDPROBE_STATUS = StatusType.MIND_PROBE_STATUS;
    private static final int MINDPROBE_DURATION_TURNS = 2;
    private static final int MINDPROBE_VALUE = 1;

    private static final int ILLUSORY_DOUBLE_CHANCE_PERCENT = 8;
    private static final StatusType ILLUSORY_DOUBLE_STATUS = StatusType.ILLUSORY_DOUBLE_STATUS;
    private static final int ILLUSORY_DOUBLE_DURATION_TURNS = 2;
    private static final int ILLUSORY_DOUBLE_VALUE = 1;

    @Override
    public String getName() { return NAME; }

    @Override
    public String getDescription() { return DESCRIPTION; }

    @Override
    public boolean isGuildSpell() { return true; }

    @Override
    public Guild getSpellGuild() { return SPELL_GUILD; }

    @Override
    public int getRequiredMagicPoints() { return REQUIRED_MAGIC_POINTS; }

    private static boolean canCast(Character caster) {
        return caster != null
                && caster.getWisdom() >= REQUIRED_WISDOM
                && caster.getMagicPoints() >= REQUIRED_MAGIC_POINTS;
    }

    private static void spendMp(Character caster) {
        caster.setMagicPoints(Math.max(0, caster.getMagicPoints() - REQUIRED_MAGIC_POINTS));
    }

    private static int computeDamage(Character caster) {
        int power = Math.max(caster.getWisdom(), caster.getIntelligence());
        int base = 4;
        return Math.max(1, base + (power / 2));
    }

    private static boolean rollPercent(int percent) {
        int p = Math.max(0, Math.min(100, percent));
        return ThreadLocalRandom.current().nextInt(100) < p;
    }

    @Override
    public void cast(Character caster) {
        if (!canCast(caster)) return;
        // No target provided; do nothing.
    }

    @Override
    public void cast(Character caster, Character target) {
        if (caster == null || target == null) return;
        if (!canCast(caster)) return;

        spendMp(caster);

        int dmg = computeDamage(caster);
        target.takeDamage(dmg);

        if (rollPercent(STATUS_CHANCE_PERCENT)) {
            target.applyStatusEffect(ON_HIT_STATUS, STATUS_DURATION_TURNS, STATUS_VALUE, caster);
        }
        if (rollPercent(MINDPROBE_CHANCE_PERCENT)) {
            target.applyStatusEffect(MINDPROBE_STATUS, MINDPROBE_DURATION_TURNS, MINDPROBE_VALUE, caster);
        }
        if (rollPercent(ILLUSORY_DOUBLE_CHANCE_PERCENT)) {
            target.applyStatusEffect(ILLUSORY_DOUBLE_STATUS, ILLUSORY_DOUBLE_DURATION_TURNS, ILLUSORY_DOUBLE_VALUE, caster);
        }
    }

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
    public void cast(Character caster, Enemies target) {
        // Intentionally unused: this implementation attacks `Charecter` targets.
    }

    @Override
    public void castWithStrength(Character enemy, double d) { }
}
