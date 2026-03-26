
// src/Status/AccuracyStatus.java
package DungeonoftheBrutalKing.Status;

import DungeonoftheBrutalKing.Charecter;

public final class AccuracyStatus extends Status {
    private final int accuracyBonus;

    public AccuracyStatus(int durationMinutes, int accuracyBonus) {
        super("Accuracy", durationMinutes, StatusPolarity.POSITIVE, StatusType.ACCURACY_STATUS);
        this.accuracyBonus = Math.max(0, accuracyBonus);
    }

    public int getAccuracyBonus() {
        return accuracyBonus;
    }

    @Override
    public void applyEffect(Charecter charecter) {
        if (charecter == null || accuracyBonus == 0) return;
        charecter.setAccuracy(charecter.getAccuracy() + accuracyBonus);
    }

    @Override
    public void removeEffect(Charecter charecter) {
        if (charecter == null || accuracyBonus == 0) return;
        charecter.setAccuracy(charecter.getAccuracy() - accuracyBonus);
    }

    @Override
    public String getDescription() {
        return "Accuracy: increases accuracy by " + accuracyBonus + " while active.";
    }
}
