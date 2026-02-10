
package Status;

import DungeonoftheBrutalKing.Charecter;

public class DivineInterventionStatus extends Status {
    public DivineInterventionStatus(int durationMinutes) {
        super("DivineIntervention", durationMinutes, false, StatusType.DIVINE_INTERVENTION_STATUS);
    }

    @Override
    public void applyEffect(Charecter charecter) {
        // No immediate effect; triggers on low health
    }

    // Call this in your character's health update logic
    public boolean onLowHealth(Charecter charecter) {
        if (!isExpired()) {
            int maxHp = charecter.getMaxHitPoints();
            if (charecter.getHitPoints() <= maxHp * 0.2) {
                int restoreHp = (int)(maxHp * 0.5);
                charecter.setHitPoints(restoreHp);
                expireEffect(charecter);
                System.out.println(charecter.getName() + " is miraculously healed by Divine Intervention!");
                return true; // Healed
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "If HP drops to 20% or less, heals the cleric to 50% HP automatically (Divine Intervention).";
    }
}
