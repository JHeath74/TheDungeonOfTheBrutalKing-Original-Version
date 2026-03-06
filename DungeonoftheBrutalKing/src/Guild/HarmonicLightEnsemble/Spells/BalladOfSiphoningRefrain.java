
// `src/Guild/HarmonicLightEnsemble/Spells/BalladOfSiphoningRefrain.java`
package Guild.HarmonicLightEnsemble.Spells;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;
import Status.StatusType;

public class BalladOfSiphoningRefrain implements Spell {

    private static final String NAME = "Ballad of Siphoning Refrain";
    private static final String DESCRIPTION =
            "A predatory ballad that wounds the target and may infuse the caster with life\\-stealing resonance.";

    private static final Guild SPELL_GUILD = Guild.HARMONILIC_LIGHT_ENSEMBLE;

    private static final int REQUIRED_WISDOM = 5;
    private static final int REQUIRED_MAGIC_POINTS = 6;

    private static final int STATUS_CHANCE_PERCENT = 10;
    private static final StatusType ON_HIT_STATUS = StatusType.LIFE_STEAL_STATUS;
    private static final int STATUS_DURATION_TURNS = 2;
    private static final int STATUS_VALUE = 1;

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

    private static boolean canCast(Charecter caster) {
        return caster != null
                && caster.getWisdom() >= REQUIRED_WISDOM
                && caster.getMagicPoints() >= REQUIRED_MAGIC_POINTS;
    }

    private static void spendMp(Charecter caster) {
        caster.setMagicPoints(Math.max(0, caster.getMagicPoints() - REQUIRED_MAGIC_POINTS));
    }

    private static int computeDamage(Charecter caster) {
        int power = Math.max(caster.getWisdom(), caster.getIntelligence());
        int base = 4;
        return Math.max(1, base + (power / 2));
    }

    private static boolean rollStatusProc() {
        return ThreadLocalRandom.current().nextInt(100) < STATUS_CHANCE_PERCENT;
    }

    @Override
    public void cast(Charecter caster) {
        if (!canCast(caster)) return;
        // No target provided; do nothing.
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster == null || target == null) return;
        if (!canCast(caster)) return;

        spendMp(caster);

        int dmg = computeDamage(caster);
        target.takeDamage(dmg);

        if (rollStatusProc()) {
            target.applyStatusEffect(ON_HIT_STATUS, STATUS_DURATION_TURNS, STATUS_VALUE, caster);
        }
    }

    @Override
    public void cast(int toonWisdom) { }

    @Override
    public void castWithIntelligence(int toonIntelligence) { }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) { }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) { }

    @Override
    public void cast() { }

    @Override
    public void cast(Charecter caster, Enemies target) {
        // Intentionally unused: this implementation attacks `Charecter` targets.
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) { }
}
