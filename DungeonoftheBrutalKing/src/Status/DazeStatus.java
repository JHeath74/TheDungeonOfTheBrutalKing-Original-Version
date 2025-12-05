
// src/Status/DazeStatus.java
package Status;

public class DazeStatus {
    private final int penalty; // Amount to reduce defense or accuracy
    private int duration; // Turns remaining

    public DazeStatus(int penalty, int duration) {
        this.penalty = penalty;
        this.duration = duration;
    }

    public int applyDefensePenalty(int originalDefense) {
        return Math.max(0, originalDefense - penalty);
    }

    public int applyAccuracyPenalty(int originalAccuracy) {
        return Math.max(0, originalAccuracy - penalty);
    }

    public void tick() {
        if (duration > 0) duration--;
    }

    public boolean isActive() {
        return duration > 0;
    }

    public int getPenalty() {
        return penalty;
    }

    public int getDuration() {
        return duration;
    }
}
