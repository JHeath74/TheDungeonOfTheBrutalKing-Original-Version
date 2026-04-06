// src/Status/Status.java
package DungeonoftheBrutalKing.Status;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.TimeClock;
import DungeonoftheBrutalKing.Enemies.Enemies;

public class Status {
    private String name;
    private int durationSeconds;
    protected StatusType type;
    private StatusPolarity polarity = null;
    private int startTimeSeconds = -1;

    public Status(String name, int durationMinutes, StatusPolarity polarity, StatusType type) {
        this.polarity = polarity;
        this.name = name;
        this.setDurationSeconds(durationMinutes * 60);
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
		// Optional override in subclasses
	}

	public void onExpire(Charecter target) {
		// Optional override in subclasses
	}

	public void onApply(Charecter target) {
		// TODO Auto-generated method stub
		
	}
	
	public StatusPolarity getPolarity() { return polarity; }
    public boolean isPositive() { return polarity == StatusPolarity.POSITIVE; }
    public boolean isNegative() { return polarity == StatusPolarity.NEGATIVE; }

	public StatusType getStatusType() {
		return type;
	}

	public void onApply(Enemies target) {
		// Optional override in subclasses
	}

	public void onTurnStart(Enemies target) {
		// Optional override in subclasses
	}

	public void expire(Charecter target) {
		onExpire(target);
	}

	public void onTurnStart(Charecter target) {
		// Optional override in subclasses
	}

}