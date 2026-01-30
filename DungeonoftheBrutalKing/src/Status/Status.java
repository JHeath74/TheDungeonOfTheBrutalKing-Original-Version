
// src/Status/Status.java
package Status;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.TimeClock;

public class Status {
    private String name;
    private int durationSeconds;
    private boolean negative;
    private int startTimeSeconds = -1;

    public Status(String name, int durationMinutes, boolean negative) {
        this.name = name;
        this.durationSeconds = durationMinutes * 60;
        this.negative = negative;
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
        durationSeconds -= timeElapsed;
        if (isExpired()) {
            expireEffect(null); // Pass character if needed
        }
    }

    public boolean isExpired() {
        return durationSeconds <= 0;
    }

	public void onExpire(Charecter charecter) {
		// TODO Auto-generated method stub
		
	}

	public boolean blocksSpellcasting() {
		// TODO Auto-generated method stub
		return false;
	}

	public double damageTakenMultiplier() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void applyEffect(Charecter charecter) {
		// TODO Auto-generated method stub
		
	}
}
