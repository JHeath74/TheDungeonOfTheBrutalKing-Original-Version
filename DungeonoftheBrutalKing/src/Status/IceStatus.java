
// src/Status/Frozen.java
package Status;

import DungeonoftheBrutalKing.Character;

public class IceStatus extends Status {
    private static final int DURATION_MINUTES = 5; // set your desired duration

    public IceStatus() {
        super("Frozen", DURATION_MINUTES);
    }

    @Override
    public void applyEffect(Character character) {
        character.setCanAct(false);
    }
}
