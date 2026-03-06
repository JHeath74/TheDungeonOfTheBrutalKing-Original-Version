
// `src/Guild/HarmonicLightEnsemble/Spells/ReelOfNervousTremors.java`
package Guild.HarmonicLightEnsemble.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;
import Status.StatusType;

public class ReelOfNervousTremors implements Spell {

    private static final String NAME = "Reel of Nervous Tremors";
    private static final String DESCRIPTION =
            "A jittery reel that unsettles the target’s footing and focus, leaving them dazed.";

    private static final Guild SPELL_GUILD = Guild.HARMONILIC_LIGHT_ENSEMBLE;

    private static final int REQUIRED_WISDOM = 5;
    private static final int REQUIRED_MAGIC_POINTS = 6;

    private static final int DURATION_TURNS = 3;

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

    private static int computePenalty(Charecter caster) {
        int power = Math.max(caster.getWisdom(), caster.getIntelligence());
        return Math.max(1, power / 6);
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

        int penalty = computePenalty(caster);
        target.applyStatusEffect(StatusType.DAZE_STATUS, DURATION_TURNS, penalty, caster);
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
        // Intentionally unused: this spell applies statuses to `Charecter` targets.
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) { }
}
