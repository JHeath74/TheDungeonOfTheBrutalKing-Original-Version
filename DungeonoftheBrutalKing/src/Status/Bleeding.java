
// src/Status/Bleeding.java
package Status;

import DungeonoftheBrutalKing.Charecter;

public class Bleeding extends Status {
    private static final int DURATION_MINUTES = 5; // example duration
    private static final int HP_LOSS_PER_TURN = 5;

    public Bleeding() {
        super("Bleeding", DURATION_MINUTES);
    }

    @Override
    public void applyEffect(Charecter character) {
        character.setHitPoints(character.getHitPoints() - HP_LOSS_PER_TURN);
    }
}
