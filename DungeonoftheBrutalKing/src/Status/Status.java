// src/Status/Status.java
package Status;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.TimeClock;

public class Status {
    private String name;
    private int durationSeconds;
    private boolean negative;
    private int startTimeSeconds = -1;
    protected StatusType type;

    public Status(String name, int durationMinutes, boolean negative, StatusType type) {
        this.name = name;
        this.setDurationSeconds(durationMinutes * 60);
        this.negative = negative;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isNegative() {
        return negative;
    }

    public void applyStatusEffect(Charecter character) {
        if (startTimeSeconds == -1) {
            startTimeSeconds = TimeClock.Singleton().getElapsedSeconds();
        }
        // To be overridden by subclasses
    }

    public void expireEffect(Charecter character) {
        // To be overridden by subclasses
    }

    public void removeEffect(Charecter character) {
        // To be overridden by subclasses
    }

    // Reduces duration by timeElapsed (in seconds)
    public void reduceDuration(int timeElapsed) {
        setDurationSeconds(getDurationSeconds() - timeElapsed);
        if (isExpired()) {
            expireEffect(null); // Pass character if needed
        }
    }

    public boolean isExpired() {
        return getDurationSeconds() <= 0;
    }

    public void onExpire(Charecter charecter) {
        // To be overridden by subclasses
    }

    public boolean blocksSpellcasting() {
        return false;
    }

    public double damageTakenMultiplier() {
        return 0;
    }

    public void applyEffect(Charecter charecter) {
        // To be overridden by subclasses
    }

    public boolean preventsMovement() {
        return false;
    }

    public String getDescription() {
        return null;
    }

    public boolean preventsActions() {
        return false;
    }

    public StatusType getType() {
        return type;
    }

	public int getDurationSeconds() {
		return durationSeconds;
	}

	public void setDurationSeconds(int durationSeconds) {
		this.durationSeconds = durationSeconds;
	}
}
