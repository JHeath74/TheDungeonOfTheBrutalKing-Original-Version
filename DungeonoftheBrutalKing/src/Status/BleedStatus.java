
// src/Status/BleedStatus.java
package Status;

import DungeonoftheBrutalKing.Charecter;

public final class BleedStatus extends Status {
    private static final int DURATION_MINUTES = 5;
    private static final int HP_LOSS_PER_TURN = 5;

    public BleedStatus() {
        super("Bleeding", DURATION_MINUTES, StatusPolarity.NEGATIVE, StatusType.BLEED_STATUS);
    }

    @Override
    public void applyEffect(Charecter character) {
        if (character == null) return;
        character.setHitPoints(character.getHitPoints() - HP_LOSS_PER_TURN);
    }

    @Override
    public void removeEffect(Charecter character) {
        // Nothing to revert for BleedStatus
    }

    @Override
    public String getDescription() {
        return "Bleeding: loses " + HP_LOSS_PER_TURN + " HP per turn while active.";
    }
}
