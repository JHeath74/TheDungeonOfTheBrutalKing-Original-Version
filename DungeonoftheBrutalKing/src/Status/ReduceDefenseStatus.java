
package Status;

import DungeonoftheBrutalKing.Charecter;

public class ReduceDefenseStatus extends Status {
    private static final int DEFAULT_DURATION = 3;
    private static final int DEFENSE_REDUCTION = 10;

    public ReduceDefenseStatus() {
        this(DEFAULT_DURATION);
    }

    public ReduceDefenseStatus(int duration) {
        super("ReduceDefense", Math.max(0, duration), true, StatusType.REDUCE_DEFENSE_STATUS);
    }

    @Override
    public void applyEffect(Charecter character) {
        if (character == null) return;
        character.setDefense(character.getDefense() - DEFENSE_REDUCTION);
    }

    @Override
    public void expireEffect(Charecter character) {
        restore(character);
    }

    @Override
    public void removeEffect(Charecter character) {
        restore(character);
    }

    private void restore(Charecter character) {
        if (character == null) return;
        character.setDefense(character.getDefense() + DEFENSE_REDUCTION);
    }
}
