
// `src/Status/DrainStatus.java`
package Status;

import DungeonoftheBrutalKing.Charecter;

public class DrainStatus extends Status {
    public enum DrainType { MAGIC, ACTION }

    private final double percent; // e.g. 0.10 for 10%
    private final DrainType type;

    public DrainStatus(int duration, double percent, DrainType type) {
        super("Drain", duration, true, StatusType.DRAIN_STATUS);
        this.percent = Math.max(0.0, Math.min(1.0, percent));
        this.type = type == null ? DrainType.MAGIC : type;
    }

    // Per-turn drain helper (kept for reuse/tests)
    public int applyDrain(int maxValue, int currentValue) {
        int safeMax = Math.max(0, maxValue);
        int safeCur = Math.max(0, currentValue);
        int drainAmount = (int) Math.ceil(safeMax * percent);
        return Math.max(0, safeCur - drainAmount);
    }

    @Override
    public void applyEffect(Charecter character) {
        if (character == null) return;

        switch (type) {
            case MAGIC -> {
                // Assumes your Charecter exposes magic resource getters/setters.
                int newValue = applyDrain(character.getMaxMagicPoints(), character.getMagicPoints());
                character.setMagicPoints(newValue);
            }
            case ACTION -> {
                // Assumes your Charecter exposes action resource getters/setters.
                int newValue = applyDrain(character.getMaxActionPoints(), character.getActionPoints());
                character.setActionPoints(newValue);
            }
        }
    }

    @Override
    public void expireEffect(Charecter character) {
        // No persistent stat to restore
    }

    @Override
    public void removeEffect(Charecter character) {
        // No persistent stat to restore
    }

    @Override
    public StatusType getType() {
        return StatusType.DRAIN_STATUS;
    }

    public double getPercent() {
        return percent;
    }

    public DrainType getDrainType() {
        return type;
    }
}
