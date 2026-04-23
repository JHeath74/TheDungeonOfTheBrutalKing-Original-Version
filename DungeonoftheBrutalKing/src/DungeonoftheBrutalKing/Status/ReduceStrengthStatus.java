
// src/Status/ReduceStrengthStatus.java
package DungeonoftheBrutalKing.Status;

import DungeonoftheBrutalKing.Character;

public final class ReduceStrengthStatus extends Status {
    private static final int DEFAULT_DURATION_TURNS = 3;
    private static final int STRENGTH_REDUCTION = 10;

    private boolean applied;

    public ReduceStrengthStatus() {
        this(DEFAULT_DURATION_TURNS);
    }

    public ReduceStrengthStatus(int durationTurns) {
        super(
                "ReduceStrength",
                Math.max(0, durationTurns),
                StatusPolarity.NEGATIVE,
                StatusType.REDUCE_STRENGTH_STATUS
        );
    }

    @Override
    public void applyEffect(Character character) {
        if (character == null) return;
        if (applied) return;

        character.setStrength(character.getStrength() - STRENGTH_REDUCTION);
        applied = true;
    }

    @Override
    public void removeEffect(Character character) {
        if (character == null) return;
        if (!applied) return;

        character.setStrength(character.getStrength() + STRENGTH_REDUCTION);
        applied = false;
    }

    @Override
    public String getDescription() {
        return "Reduce Strength: lowers strength while active\\.";
    }
}
