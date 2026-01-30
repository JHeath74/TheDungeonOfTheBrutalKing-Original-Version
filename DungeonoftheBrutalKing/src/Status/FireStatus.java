
java
package Status;

import DungeonoftheBrutalKing.Charecter;

public class FireStatus extends Status {
    private static final int DURATION_MINUTES = 4;
    private static final int ATTACK_REDUCTION = 5;
    private static final int FIRE_DAMAGE_PER_TURN = 7;
    private int originalAttack;

    public FireStatus() {
        super("Burned", DURATION_MINUTES, true); // true: negative effect
    }

    @Override
    public void applyEffect(Charecter character) {
        originalAttack = character.getAttackDamage();
        character.setAttack(originalAttack - ATTACK_REDUCTION);
        character.setHitPoints(character.getHitPoints() - FIRE_DAMAGE_PER_TURN);
    }

    @Override
    public void expireEffect(Charecter character) {
        character.setAttack(originalAttack);
    }

    @Override
    public void removeEffect(Charecter character) {
        character.setAttack(originalAttack);
    }
}
