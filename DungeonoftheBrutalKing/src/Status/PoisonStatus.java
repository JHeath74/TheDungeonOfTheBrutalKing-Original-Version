
// src/Status/PoisonStatus.java
package Status;

import DungeonoftheBrutalKing.Charecter;

public class PoisonStatus extends Status {
    private static final int POISON_DAMAGE = 5; // HP lost per turn

    public PoisonStatus(int duration) {
        super("Poison", duration, true, StatusType.POISON_STATUS); // Added StatusType
    }

    @Override
    public void applyEffect(Charecter character) {
        character.reduceHitPoints(POISON_DAMAGE);
    }

    @Override
    public void expireEffect(Charecter character) {
        // No additional effect on expire
    }

    @Override
    public void removeEffect(Charecter character) {
        // No additional effect on remove
    }
}
