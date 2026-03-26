
// src/Status/ResilienceStatus.java
package Status;

import DungeonoftheBrutalKing.Charecter;

public final class ResilienceStatus extends Status {
    private final int value;

    public ResilienceStatus(int durationMinutes, int value) {
        super("Resilience", durationMinutes, StatusPolarity.POSITIVE, StatusType.RESILIENCE_STATUS);
        this.value = Math.max(0, value);
    }

    public int getValue() {
        return value;
    }

    @Override
    public void applyEffect(Charecter target) {
        if (target == null || value == 0) return;
        target.increaseResilience(value);
    }

    @Override
    public void removeEffect(Charecter target) {
        if (target == null || value == 0) return;
        target.decreaseResilience(value);
    }

    @Override
    public String getDescription() {
        return "Resilience: increases resilience by " + value + " while active.";
    }
}
