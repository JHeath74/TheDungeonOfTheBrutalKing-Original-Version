
package Status;

import DungeonoftheBrutalKing.Charecter;

public final class DivineInterventionStatus extends Status {

    private static final double TRIGGER_THRESHOLD = 0.20; // 20%
    private static final double HEAL_TO_FRACTION = 0.50;  // 50%

    public DivineInterventionStatus(int durationMinutes) {
        super("Divine Intervention", durationMinutes, StatusPolarity.POSITIVE, StatusType.DIVINE_INTERVENTION_STATUS);
    }

    @Override
    public void applyEffect(Charecter charecter) {
        // No immediate effect; checked via onLowHealth(...)
    }

    @Override
    public void removeEffect(Charecter charecter) {
        // No stat changes to undo
    }

    /**
     * Call this from the character's HP update logic.
     * Returns true if the intervention triggered and consumed the status.
     */
    public boolean onLowHealth(Charecter charecter) {
        if (charecter == null || isExpired()) return false;

        int maxHp = charecter.getMaxHitPoints();
        if (maxHp <= 0) return false;

        if (charecter.getHitPoints() <= (int) Math.floor(maxHp * TRIGGER_THRESHOLD)) {
            int newHp = (int) Math.floor(maxHp * HEAL_TO_FRACTION);
            charecter.setHitPoints(newHp);

            // Consume the status immediately
            reduceDuration(getDurationSeconds());

            System.out.println(charecter.getName() + " is miraculously healed by Divine Intervention!");
            return true;
        }

        return false;
    }

    @Override
    public String getDescription() {
        return "If HP drops to 20% or less, heals the cleric to 50% HP automatically (Divine Intervention).";
    }
}
