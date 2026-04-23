
// src/Status/DrainStatus.java
package DungeonoftheBrutalKing.Status;

import DungeonoftheBrutalKing.Character;

public class DrainStatus extends Status {
    public enum DrainType { MAGIC, ACTION }

    private final double percent; // e.g. 0.10 for 10%
    private final DrainType type;

    public DrainStatus(int duration, double percent, DrainType type) {
        super("Drain", Math.max(0, duration), StatusPolarity.NEGATIVE, StatusType.DRAIN_STATUS);
        this.percent = clamp01(percent);
        this.type = (type == null) ? DrainType.MAGIC : type;
    }

    private static double clamp01(double v) {
        if (Double.isNaN(v) || Double.isInfinite(v)) return 0.0;
        return Math.max(0.0, Math.min(1.0, v));
    }

    // Per-turn drain helper (kept for reuse/tests)
    public int applyDrain(int maxValue, int currentValue) {
        int safeMax = Math.max(0, maxValue);
        int safeCur = Math.max(0, currentValue);
        if (percent <= 0.0 || safeMax == 0 || safeCur == 0) return safeCur;

        int drainAmount = (int) Math.ceil(safeMax * percent);
        return Math.max(0, safeCur - Math.max(0, drainAmount));
    }

    @Override
    public void applyEffect(Character character) {
        if (character == null) return;

        switch (type) {
            case MAGIC -> {
                int newValue = applyDrain(character.getMaxMagicPoints(), character.getMagicPoints());
                character.setMagicPoints(newValue);
            }
            case ACTION -> {
                int newValue = applyDrain(character.getMaxActionPoints(), character.getActionPoints());
                character.setActionPoints(newValue);
            }
        }
    }

    @Override
    public void removeEffect(Character character) {
        // No persistent stat to restore.
    }

    @Override
    public String getDescription() {
        int pct = (int) Math.round(percent * 100.0);
        String resource = (type == DrainType.ACTION) ? "action points" : "magic points";
        return "Drain: reduces current " + resource + " by " + pct + "\\% of max each turn while active\\.";
    }

    public double getPercent() {
        return percent;
    }

    public DrainType getDrainType() {
        return type;
    }
}
