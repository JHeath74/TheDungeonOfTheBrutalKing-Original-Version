
// src/Status/ReduceDefenseStatus.java
package DungeonoftheBrutalKing.Status;

import DungeonoftheBrutalKing.Charecter;

public final class ReduceDefenseStatus extends Status {
    private static final int DEFAULT_DURATION = 3;
    private static final int DEFENSE_REDUCTION = 10;

    private boolean applied;

    public ReduceDefenseStatus() {
        this(DEFAULT_DURATION);
    }

    public ReduceDefenseStatus(int duration) {
        super("ReduceDefense", Math.max(0, duration), StatusPolarity.NEGATIVE, StatusType.REDUCE_DEFENSE_STATUS);
    }

    @Override
    public void applyEffect(Charecter character) {
        if (character == null) return;
        if (applied) return;

        character.setDefense(character.getDefense() - DEFENSE_REDUCTION);
        applied = true;
    }

    @Override
    public void removeEffect(Charecter character) {
        if (character == null) return;
        if (!applied) return;

        character.setDefense(character.getDefense() + DEFENSE_REDUCTION);
        applied = false;
    }

    @Override
    public String getDescription() {
        return "Reduce Defense: lowers defense while active\\.";
    }
}
