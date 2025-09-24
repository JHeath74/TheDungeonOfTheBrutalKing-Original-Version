
// src/Status/Frozen.java
package Status;

import DungeonoftheBrutalKing.Charecter;

public class Frozen extends Status {
    private static final int DURATION_MINUTES = 5; // set your desired duration

    public Frozen() {
        super("Frozen", DURATION_MINUTES);
    }

    @Override
    public void applyEffect(Charecter character) {
        character.setCanAct(false);
    }
}
