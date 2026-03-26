
// src/Status/LightningStatus.java
package Status;

import DungeonoftheBrutalKing.Charecter;

/**
 * Negative status: applies lightning damage each tick/turn while active.
 */
public final class LightningStatus extends Status {

    private static final int DEFAULT_DAMAGE_PER_TURN = 1;

    private final int damagePerTurn;

    public LightningStatus(int durationTurns) {
        this(durationTurns, DEFAULT_DAMAGE_PER_TURN);
    }

    public LightningStatus(int durationTurns, int damagePerTurn) {
        super("Lightning", Math.max(0, durationTurns), StatusPolarity.NEGATIVE, StatusType.LIGHTNING_STATUS);
        this.damagePerTurn = Math.max(0, damagePerTurn);
    }

    @Override
    public void applyEffect(Charecter character) {
        if (character == null) return;

        if (damagePerTurn > 0) {
            int newHp = Math.max(0, character.getHitPoints() - damagePerTurn);
            character.setHitPoints(newHp);
        }
    }

    @Override
    public void removeEffect(Charecter character) {
        // No persistent stat changes to restore.
    }

    @Override
    public String getDescription() {
        return "Shocked: takes " + damagePerTurn + " lightning damage each turn while active\\.";
    }
}
