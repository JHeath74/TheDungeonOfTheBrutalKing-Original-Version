// src/Status/Status.java
package DungeonoftheBrutalKing.Status;

import DungeonoftheBrutalKing.Character;
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

    public void applyStatusEffect(Character character) {
        if (startTimeSeconds == -1) {
            startTimeSeconds = TimeClock.Singleton().getElapsedSeconds();
        }
        // To be overridden by subclasses
    }

    public void expireEffect(Character character) {
        // To be overridden by subclasses
    }

    public void removeEffect(Character character) {
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

    public void applyEffect(Character charecter) {
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

	public void onExpire(Character target) {
		// Optional override in subclasses
	}

	public void onApply(Character target) {
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

	public void expire(Character target) {
		onExpire(target);
	}

	public void onTurnStart(Character target) {
		// Optional override in subclasses
	}

	public int getAttackBonus() {
		// TODO Auto-generated method stub
		return 0;
	}

}