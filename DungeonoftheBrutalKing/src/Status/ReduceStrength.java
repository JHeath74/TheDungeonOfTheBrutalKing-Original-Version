
// src/Status/ReduceStrength.java
package Status;

import DungeonoftheBrutalKing.Charecter;

public class ReduceStrength extends Status {
    private static final int DURATION_MINUTES = 3; // example duration

    public ReduceStrength() {
        super("ReduceStrength", DURATION_MINUTES);
    }

    @Override
    public void applyEffect(Charecter character) {
        character.setStrength(character.getStrength() - 10); // reduce strength by 10
    }
}
