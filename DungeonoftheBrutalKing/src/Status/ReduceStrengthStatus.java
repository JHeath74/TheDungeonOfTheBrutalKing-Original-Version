
package Status;

import DungeonoftheBrutalKing.Charecter;

public class ReduceStrengthStatus extends Status {
    private static final int DURATION_MINUTES = 3; // example duration

    public ReduceStrengthStatus() {
        super("ReduceStrength", DURATION_MINUTES, true); // true: negative effect
    }

    @Override
    public void applyEffect(Charecter character) {
        character.setStrength(character.getStrength() - 10); // reduce strength by 10
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
