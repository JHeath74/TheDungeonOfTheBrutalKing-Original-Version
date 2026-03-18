// src/Status/Status.java
package Status;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.TimeClock;
import Enemies.Enemies;

public class Status {
    private String name;
    private int durationSeconds;
    private StatusPolarity negative;
    private int startTimeSeconds = -1;
    protected StatusType type;
    private StatusPolarity polarity = null;

    public Status(String name, int durationMinutes, StatusPolarity positive, StatusType type) {
        this.polarity = polarity;
		this.name = name;
        this.setDurationSeconds(durationMinutes * 60);
        this.negative = positive;
        this.type = type;
    }

    public String getName() {
        return name;
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
        return 1.0;
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

	public void onRemove(Enemies target) {
		// TODO Auto-generated method stub
		
	}

	public void onApply(Character target) {
		// TODO Auto-generated method stub
		
	}

	public void onExpire(Character target) {
		// TODO Auto-generated method stub
		
	}

	public void onApply(Charecter target) {
		// TODO Auto-generated method stub
		
	}
	
	public StatusPolarity getPolarity() { return polarity; }
    public boolean isPositive() { return polarity == StatusPolarity.POSITIVE; }
    public boolean isNegative() { return polarity == StatusPolarity.NEGATIVE; }

	public StatusType getStatusType() {
		// TODO Auto-generated method stub
		return null;
	}

	public void onApply(Enemies target) {
		// TODO Auto-generated method stub
		
	}

	public void onTurnStart(Enemies target) {
		// TODO Auto-generated method stub
		
	}

}