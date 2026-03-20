package Guild.SilverwardSentinels.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import SharedData.GuildType;
import Spells.Spell;

/**
 * AureateAegisHeal - a Paladin / Silverward Sentinels healing prayer.
 * Heals a single ally (or self) for a moderate amount scaling with caster Wisdom.
 */
public final class BlessingofRestoration implements Spell {

    private static final String NAME = "Blessing of Restoration";

    // Base % of target max HP healed
    private static final double BASE_HEAL_PERCENT = 0.18; // 18%

    // Extra heal per 10 points of Wisdom
    private static final double WISDOM_SCALING_PER_10 = 0.03; // +3% per 10 Wisdom

    // Minimum flat heal
    private static final int MIN_FLAT_HEAL = 6;

    // MP cost
    private static final int REQUIRED_MP = 5;

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
        // map your paladin guild type to the proper Guild enum value
        // adjust this to the actual constant you use for Silverward Sentinels
        return Guild.SILVERWARD_SENTINELS;
    }

    @Override
    public int getRequiredMagicPoints() {
        return REQUIRED_MP;
    }

    @Override
    public String getDescription() {
        return "A holy Silverward Sentinel prayer that restores a solid amount of health "
             + "to a single ally, scaling with the caster's Wisdom.";
    }

    // --- Internal heal logic ---

    private int computeHealAmount(Charecter caster, Charecter target) {
        if (target == null) return 0;

        int maxHp = Math.max(0, target.getMaxHealth());
        int currentHp = Math.max(0, target.getHitPoints());
        if (maxHp <= 0 || currentHp >= maxHp) return 0;

        int wisdom = (caster != null) ? Math.max(0, caster.getWisdom()) : 0;

        double wisdomSteps = wisdom / 10.0;
        double bonusPercent = wisdomSteps * WISDOM_SCALING_PER_10;
        double totalPercent = BASE_HEAL_PERCENT + bonusPercent;

        int amountFromPercent = (int) Math.round(maxHp * totalPercent);
        int rawAmount = Math.max(MIN_FLAT_HEAL, amountFromPercent);

        int heal = Math.min(rawAmount, maxHp - currentHp);
        return Math.max(0, heal);
    }

    /**
     * Performs guild check and applies heal if allowed.
     */
    private void applyHeal(Charecter caster, Charecter target) {
        if (target == null) return;

        // --- guild check: only members of this spell's guild may cast it ---
        if (caster != null) {
            Guild casterGuild = caster.getGuild(); // adjust if your getter differs
            if (casterGuild == null || casterGuild != getSpellGuild()) {
                // caster is not from the correct guild -> do nothing
                return;
            }
        }

        int heal = computeHealAmount(caster, target);
        if (heal <= 0) return;

        int newHp = Math.min(target.getMaxHealth(), target.getHitPoints() + heal);
        target.setHitPoints(newHp);
    }

    // --- Spell interface implementations ---

    @Override
    public void cast() {
        // no-op; your engine likely uses other overloads
    }

    @Override
    public void cast(int toonWisdom) {
        // Left as no-op: engine does not define a global caster/target here
    }

    @Override
    public void castWithIntelligence(int toonIntelligence) {
        // Not used for this spell
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        // Not used for this spell
    }

    @Override
    public void cast(Charecter caster) {
        // Self-heal with guild check
        applyHeal(caster, caster);
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        // Heal ally or self with guild check
        applyHeal(caster, target);
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        // Simple behavior: heal caster only, with guild check
        applyHeal(caster, caster);
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) {
        // Not meaningful for a pure heal; no-op
    }

    @Override
    public void cast(Charecter caster, Enemies target) {
        // Healing spell does not affect enemies; no-op
    }
}
