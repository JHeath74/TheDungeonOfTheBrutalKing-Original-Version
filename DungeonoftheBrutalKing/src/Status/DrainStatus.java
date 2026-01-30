// src/Status/DrainStatus.java
package Status;

import DungeonoftheBrutalKing.Charecter;

public class DrainStatus extends Status {
    public enum DrainType { MAGIC, ACTION }

    private final double percent; // Percent to drain each turn (e.g., 0.10 for 10%)
    private final DrainType type;

    public DrainStatus(int duration, double percent, DrainType type) {
        super("Drain", duration, true); // true for negative effect
        this.percent = percent;
        this.type = type;
    }

    // Call this each turn to apply the drain effect
    public int applyDrain(int maxValue, int currentValue) {
        int drainAmount = (int) Math.ceil(maxValue * percent);
        return Math.max(0, currentValue - drainAmount);
    }

    @Override
    public void applyEffect(Charecter character) {
        // Effect is applied each turn, so nothing to do here
    }

    @Override
    public void expireEffect(Charecter character) {
        // No stat to restore
    }

    @Override
    public void removeEffect(Charecter character) {
        // No stat to restore
    }

    public double getPercent() {
        return percent;
    }

    public DrainType getType() {
        return type;
    }
}
