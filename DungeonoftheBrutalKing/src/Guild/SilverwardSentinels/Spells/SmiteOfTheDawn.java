
// src/Guild/SilverwardSentinels/Spells/SmiteOfTheDawn.java
package Guild.SilverwardSentinels.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;

public final class SmiteOfTheDawn implements Spell {

    private static final String NAME = "Smite of the Dawn";

    private static final int REQUIRED_MP = 6;

    // Base damage and STR scaling (Paladins: primary WIS, secondary STR,
    // but this is a weapon\-based smite so it uses Strength here)
    private static final int BASE_DAMAGE = 8;
    private static final double STRENGTH_SCALING = 0.6;

    // Extra damage only if the enemy is undead
    private static final double UNDEAD_BONUS_MULTIPLIER = 1.75;

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
        return "Smite of the Dawn: a radiant weapon strike that channels the first light of creation. "
             + "Deals radiant damage based on Strength, with extra power against undead foes, "
             + "briefly illuminating the area and weakening minor magical darkness.";
    }

    private void applySmite(Charecter caster, Enemies target) {
        if (caster == null || target == null) {
            return;
        }

        // Only Silverward Sentinels can use this spell
        Guild casterGuild = caster.getGuild();
        if (casterGuild == null || casterGuild != getSpellGuild()) {
            return;
        }

        int damage = computeDamage(caster, target);
        if (damage <= 0) {
            return;
        }

        int currentHp = target.getHitPoints();
        int newHp = Math.max(0, currentHp - damage);
        target.setHitPoints(newHp);

        // Hook for light / darkness\-dispelling logic could go here.
    }

    private int computeDamage(Charecter caster, Enemies target) {
        int strength = Math.max(0, caster.getStrength());
        double result = BASE_DAMAGE + (strength * STRENGTH_SCALING);

        // Apply bonus only if target is undead
        if (isUndead(target)) {
            result *= UNDEAD_BONUS_MULTIPLIER;
        }

        return (int) Math.max(1, Math.round(result));
    }

    // Replace with a better type check if your Enemies API supports it
    private boolean isUndead(Enemies target) {
        if (target == null) {
            return false;
        }
        String name = String.valueOf(target.getName()).toLowerCase();
        return name.contains("Ghost")
            || name.contains("Ghoul")
            || name.contains("Liches")
            || name.contains("Skeleton")
            || name.contains("Spectre")
            || name.contains("Vampire")
            || name.contains("Waith");
    }

    // --- Spell interface implementations ---

    @Override
    public void cast() {
        // no\-op
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
        // offensive smite; does not target allies
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        // unused
    }

    @Override
    public void castWithStrength(Charecter enemy, double strengthScaling) {
        // unused legacy overload
    }

    @Override
    public void cast(Charecter caster, Enemies target) {
        applySmite(caster, target);
    }
}
