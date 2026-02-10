
package Status;

import DungeonoftheBrutalKing.Charecter;

public class AccuracyStatus extends Status {
    private int accuracyBonus;

    public AccuracyStatus(String name, int duration, boolean negative, int accuracyBonus) {
        super(name, duration, negative, StatusType.ACCURACY_STATUS); // Pass the correct StatusType here
        this.accuracyBonus = accuracyBonus;
    }

    @Override
    public void applyEffect(Charecter charecter) {
        int current = charecter.getAccuracy();
        charecter.setAccuracy(current + accuracyBonus);
    }

    @Override
    public void removeEffect(Charecter charecter) {
        int current = charecter.getAccuracy();
        charecter.setAccuracy(current - accuracyBonus);
    }
}
