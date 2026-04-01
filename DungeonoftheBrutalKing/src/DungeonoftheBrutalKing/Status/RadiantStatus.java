
// src/Status/RadiantStatus.java
package DungeonoftheBrutalKing.Status;

import DungeonoftheBrutalKing.Charecter;

public final class RadiantStatus extends Status {
    private static final double HEAL_PERCENT = 0.10; // 10% of max HP per turn

    public RadiantStatus(int durationMinutes) {
        super("Radiant", durationMinutes, StatusPolarity.POSITIVE, StatusType.RADIANT_STATUS);
    }

    @Override
    public void applyEffect(Charecter character) {
        if (character == null) return;

        int maxHP = Math.max(0, character.getMaxHitPoints());
        int healAmount = (int) Math.ceil(maxHP * HEAL_PERCENT);

        if (healAmount <= 0) return;
        character.restoreHitPoints(healAmount);
    }

    @Override
    public void removeEffect(Charecter character) {
        // No additional effect on remove
    }

    @Override
    public String getDescription() {
        return "Radiant: restores " + (int) (HEAL_PERCENT * 100) + "% of max HP per turn while active.";
    }
}
