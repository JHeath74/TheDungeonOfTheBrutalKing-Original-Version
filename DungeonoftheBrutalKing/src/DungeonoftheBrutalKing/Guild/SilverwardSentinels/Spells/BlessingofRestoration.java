package DungeonoftheBrutalKing.Guild.SilverwardSentinels.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.Spells.SpellBalanceManager;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;


/**
 * BlessingofRestoration - a Paladin / Silverward Sentinels self-only healing prayer.
 * This spell heals the caster only (solo spell). It requires membership of the
 * Silverward Sentinels guild and consumes magic points when cast.
 */
public final class BlessingofRestoration implements DungeonoftheBrutalKing.Spells.Spell {

    private static final String NAME = "Blessing of Restoration";

    // Load tuning from central manager with fallback defaults
    private static final double BASE_HEAL_PERCENT = SpellBalanceManager.getDouble("BlessingofRestoration.baseHealPercent", 0.18);
    private static final double WISDOM_SCALING_PER_10 = SpellBalanceManager.getDouble("BlessingofRestoration.wisdomScalingPer10", 0.03);
    private static final int MIN_FLAT_HEAL = SpellBalanceManager.getInt("BlessingofRestoration.minFlatHeal", 6);
    private static final int REQUIRED_MP = SpellBalanceManager.getInt("BlessingofRestoration.requiredMp", 5);

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isGuildSpell() {
        return true;
    }

    @Override
    public DungeonoftheBrutalKing.SharedData.Guild getSpellGuild() {
        return DungeonoftheBrutalKing.SharedData.Guild.SILVERWARD_SENTINELS;
    }

    @Override
    public int getRequiredMagicPoints() {
        return REQUIRED_MP;
    }

    @Override
    public String getDescription() {
        return "A holy Silverward Sentinel prayer that restores a solid amount of health to the caster, scaling with Wisdom.";
    }

    // --- Internal heal logic ---

    /**
     * Compute the heal amount for the caster (heals 'target', but for this solo spell
     * caster and target are the same). Uses caster Wisdom to scale the percent heal.
     */
    private int computeHealAmount(Character caster, Character target) {
        if (target == null) return 0;

        // Use getMaxHitPoints() which is the project's canonical max HP accessor
        int maxHp = Math.max(0, target.getMaxHitPoints());
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
     * Apply the heal to the caster only. Verifies caster is non-null, a guild member,
     * and has sufficient MP. Prints informative messages on failure.
     */
    private void applyHeal(Character caster) {
        if (caster == null) {
            System.out.println("No caster provided for " + NAME + ". Spell requires a caster.");
            return; // require a caster
        }

        // Guild membership check: only full/guild members may cast
        DungeonoftheBrutalKing.SharedData.Guild casterGuild = caster.getGuild();
        if (casterGuild == null || casterGuild != getSpellGuild()) {
            try { System.out.println(caster.getName() + " is not a member of " + getSpellGuild() + " and cannot cast " + NAME + "."); } catch (Exception ignored) { }
            return; // not allowed to cast
        }

        // MP check and consumption
        if (caster.getMagicPoints() < REQUIRED_MP) {
            try { System.out.println(caster.getName() + " lacks the magic points to cast " + NAME + "."); } catch (Exception ignored) { }
            return;
        }
        caster.setMagicPoints(Math.max(0, caster.getMagicPoints() - REQUIRED_MP));

        // Solo spell: always heal the caster
        Character target = caster;

        int heal = computeHealAmount(caster, target);
        if (heal <= 0) {
            try { System.out.println(caster.getName() + " is already at full health or cannot be healed by " + NAME + "."); } catch (Exception ignored) { }
            return;
        }

        int newHp = Math.min(target.getMaxHitPoints(), target.getHitPoints() + heal);
        target.setHitPoints(newHp);

        try { System.out.println(caster.getName() + " is healed for " + heal + " HP by " + NAME + "."); } catch (Exception ignored) { }
    }

    // --- Spell interface implementations ---

    @Override
    public void cast() {
        // No-op: engine must call cast(caster) when a character is casting
    }

    @Override
    public void cast(int toonWisdom) {
        // Not applicable for this spell
    }

    @Override
    public void castWithIntelligence(int toonIntelligence) {
        // Not used
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        // Not used
    }

    @Override
    public void cast(Character caster) {
        applyHeal(caster);
    }

    @Override
    public void cast(Character caster, Character target) {
        // Solo spell: ignore provided target and heal caster only
        applyHeal(caster);
    }

    @Override
    public void cast(Character caster, List<Character> allCharacters) {
        // Solo spell: ignore list and heal caster only
        applyHeal(caster);
    }

    @Override
    public void castWithStrength(Character enemy, double d) {
        // No-op for heal
    }

    @Override
    public void cast(Character caster, Enemies target) {
        // Healing spell does not affect enemies; no-op
    }
}