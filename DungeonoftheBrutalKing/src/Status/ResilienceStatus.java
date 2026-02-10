
package Status;

import DungeonoftheBrutalKing.Charecter;

public class ResilienceStatus extends Status {
    private final int value;

    public ResilienceStatus(int duration, int value) {
        super("Resilience", duration, false, StatusType.RESILIENCE_STATUS);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void applyEffect(Charecter target) {
        target.increaseResilience(value);
    }

    @Override
    public void removeEffect(Charecter target) {
        target.decreaseResilience(value);
    }

    @Override
    public void expireEffect(Charecter target) {
        // No additional effect on expire
    }
}
