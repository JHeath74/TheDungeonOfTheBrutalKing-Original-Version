
package Status;

import DungeonoftheBrutalKing.Charecter;

public class ReduceDefenseStatus extends Status {
    private static final int DURATION_MINUTES = 3; // example duration
    private static final int DEFENSE_REDUCTION = 10;

    public ReduceDefenseStatus() {
        super("ReduceDefense", DURATION_MINUTES, true); // true: negative effect
    }

    @Override
    public void applyEffect(Charecter character) {
        character.setDefense(character.getDefense() - DEFENSE_REDUCTION);
    }

    @Override
    public void expireEffect(Charecter character) {
        // No additional effect on expire
    }

    @Override
    public void removeEffect(Charecter character) {
        // No additional effect on remove
    }
}
