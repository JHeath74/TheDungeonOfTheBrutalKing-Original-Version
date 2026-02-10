package Status;

import DungeonoftheBrutalKing.Charecter;

public class DrainStatus extends Status {
    public enum DrainType { MAGIC, ACTION }

    private final double percent; // Percent to drain each turn (e.g., 0.10 for 10%)
    private final DrainType type;

    public DrainStatus(int duration, double percent, DrainType type) {
        super("Drain", duration, true, StatusType.DRAIN_STATUS); // Pass StatusType.DRAIN
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
    // No persistent stat to restore for DrainStatus
}

@Override
public void removeEffect(Charecter character) {
    // No persistent stat to restore for DrainStatus
}


    @Override
    public StatusType getType() {
        return StatusType.DRAIN_STATUS;
    }

    public double getPercent() {
        return percent;
    }

    public DrainType getTypeValue() {
        return type;
    }
}
