
// src/Status/StunStatus.java
package DungeonoftheBrutalKing.Status;

import DungeonoftheBrutalKing.Character;

public final class StunStatus extends Status {
    private boolean applied;

    public StunStatus(int durationTurns) {
        super("Stun", Math.max(0, durationTurns), StatusPolarity.NEGATIVE, StatusType.STUN_STATUS);
    }

    @Override
    public void applyEffect(Character character) {
        if (character == null) return;
        if (applied) return;

        character.setStunned(true);
        applied = true;
    }

    @Override
    public void removeEffect(Character character) {
        if (character == null) return;
        if (!applied) return;

        character.setStunned(false);
        applied = false;
    }

    @Override
    public String getDescription() {
        return "Stun: Cannot act while active\\.";
    }
}
