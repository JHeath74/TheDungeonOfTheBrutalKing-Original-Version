package Guild.SilverwardSentinels.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;
import Status.JudgementBrandStatus;
import Status.StatusType;

public final class JudgementBrand implements Spell {

    private static final String NAME = "Judgement Brand";

    private static final int REQUIRED_MP = 6;

    // Hybrid radiant / fire style numbers
    private static final int BASE_DAMAGE = 6;
    private static final double WISDOM_SCALING = 0.6;   // primary stat
    private static final double STRENGTH_SCALING = 0.3; // secondary stat

    // How strong the "marked" effect is in your downstream damage code
    // (this class just applies the status; your combat system should read it)
    private static final int BRAND_DURATION_ROUNDS = 3;
    private static final int BRAND_INTENSITY_PERCENT = 25; // or whatever you like

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isGuildSpell() {
        return true;
    }

    @Override
    public Guild getSpellGuild() {
        return Guild.SILVERWARD_SENTINELS;
    }

    @Override
    public int getRequiredMagicPoints() {
        return REQUIRED_MP;
    }

    @Override
    public String getDescription() {
        return "Judgement Brand: a holy mark that condemns the wicked to divine retribution. "
             + "On hit, deals radiant / fire hybrid damage and brands the target, "
             + "causing them to take extra damage from you and your allies for a short time.";
    }

    private void applyJudgement(Charecter caster, Enemies target) {
        if (caster == null || target == null) {
            return;
        }

        Guild casterGuild = caster.getGuild();
        if (casterGuild == null || casterGuild != getSpellGuild()) {
            return;
        }

        int damage = computeDamage(caster, target);
        if (damage > 0) {
            int currentHp = target.getHitPoints();
            int newHp = Math.max(0, currentHp - damage);
            target.setHitPoints(newHp);
        }

        applyJudgementBrandStatus(caster);
    }

    private int computeDamage(Charecter caster, Enemies target) {
        int wis = Math.max(0, caster.getWisdom());
        int str = Math.max(0, caster.getStrength());

        double result = BASE_DAMAGE
                + (wis * WISDOM_SCALING)
                + (str * STRENGTH_SCALING);

        return (int) Math.max(1, Math.round(result));
    }

    /**
     * Applies a `JudgementBrandStatus` to the caster (or wherever you track marks).
     * Adjust to your own status system as needed.
     */
    private void applyJudgementBrandStatus(Charecter caster) {
        // durationMinutes: reuse BRAND_DURATION_ROUNDS as minutes, or map rounds->minutes as needed
        int durationMinutes = BRAND_DURATION_ROUNDS;

        JudgementBrandStatus brandStatus =
            new JudgementBrandStatus(durationMinutes, BRAND_INTENSITY_PERCENT, caster);

        // Assuming Charecter.applyStatusEffect(StatusType type, int durationMinutes, int value, Charecter source)
        caster.applyStatusEffect(
                StatusType.JUDGEMENT_BRAND,
                durationMinutes,
                BRAND_INTENSITY_PERCENT,
                caster
        );
    }

    // --- Spell interface implementations ---

    @Override
    public void cast() {
        // no-op
    }

    @Override
    public void cast(int toonWisdom) {
        // unused
    }

    @Override
    public void castWithIntelligence(int toonIntelligence) {
        // unused
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        // unused
    }

    @Override
    public void cast(Charecter caster) {
        // needs an enemy target
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        // offensive mark; not for allies
    }

    @Override
    public void cast(Charecter caster, java.util.List<Charecter> allCharacters) {
        // unused
    }

    @Override
    public void castWithStrength(Charecter enemy, double strengthScaling) {
        // unused legacy overload
    }

    @Override
    public void cast(Charecter caster, Enemies target) {
        applyJudgement(caster, target);
    }
}
