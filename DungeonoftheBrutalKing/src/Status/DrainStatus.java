
// src/Status/DrainStatus.java
package Status;

public class DrainStatus {
    public enum DrainType { MAGIC, ACTION }

    private final double percent; // Percent to drain each turn (e.g., 0.10 for 10%)
    private int duration;
    private final DrainType type;

    public DrainStatus(DrainType type, double percent, int duration) {
        this.type = type;
        this.percent = percent;
        this.duration = duration;
    }

    // Call this each turn to apply the drain effect
    public int applyDrain(int maxValue, int currentValue) {
        int drainAmount = (int) Math.ceil(maxValue * percent);
        return Math.max(0, currentValue - drainAmount);
    }

    public void tick() {
        if (duration > 0) duration--;
    }

    public boolean isActive() {
        return duration > 0;
    }

    public double getPercent() {
        return percent;
    }

    public int getDuration() {
        return duration;
    }

    public DrainType getType() {
        return type;
    }
}
