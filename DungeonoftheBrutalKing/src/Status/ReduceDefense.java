
// src/Status/ReduceDefense.java
package Status;

import DungeonoftheBrutalKing.Charecter;

public class ReduceDefense extends Status {
    private static final int DURATION_MINUTES = 3; // example duration

    public ReduceDefense() {
        super("ReduceDefense", DURATION_MINUTES);
    }

    @Override
    public void applyEffect(Charecter character) {
        character.setDefense(character.getDefense() - 10); // reduce defense by 10
    }
}
