
package Status;

import DungeonoftheBrutalKing.Character;

public class PoisonStatus extends Status {
    private static final int POISON_DAMAGE = 5; // HP lost per turn

    public PoisonStatus(int duration) {
        super("Poison", duration);
    }

    @Override
    public void applyEffect(Character character) {
        character.reduceHitPoints(POISON_DAMAGE);
    }
}
