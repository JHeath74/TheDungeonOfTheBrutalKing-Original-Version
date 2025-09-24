
// src/Status/Burned.java
package Status;

import DungeonoftheBrutalKing.Charecter;

public class Fire extends Status {
    private static final int DURATION_MINUTES = 4; // example duration
    private static final int ATTACK_REDUCTION = 5;
    private static final int FIRE_DAMAGE_PER_TURN = 7;

    public Fire() {
        super("Burned", DURATION_MINUTES);
    }


 // Fire.java
 @Override
 public void applyEffect(Charecter character) {
     character.setAttack(character.getAttackDamage() - ATTACK_REDUCTION);
     character.setHitPoints(character.getHitPoints() - FIRE_DAMAGE_PER_TURN);
 }

}
