
package Status;

import DungeonoftheBrutalKing.Charecter;

public class PoisonStatus extends Status {
    private static final int POISON_DAMAGE = 5; // HP lost per turn

    public PoisonStatus(int duration) {
        super("Poison", duration);
    }

    @Override
    public void applyEffect(Charecter character) {
        character.reduceHitPoints(POISON_DAMAGE);
    }
}
