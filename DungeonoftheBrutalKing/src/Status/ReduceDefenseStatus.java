
// src/Status/ReduceDefense.java
package Status;

import DungeonoftheBrutalKing.Character;

public class ReduceDefenseStatus extends Status {
    private static final int DURATION_MINUTES = 3; // example duration

    public ReduceDefenseStatus() {
        super("ReduceDefense", DURATION_MINUTES);
    }

    @Override
    public void applyEffect(Character character) {
        character.setDefense(character.getDefense() - 10); // reduce defense by 10
    }
}
