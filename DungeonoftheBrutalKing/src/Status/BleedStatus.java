package Status;

import DungeonoftheBrutalKing.Charecter;

public class BleedStatus extends Status {
    private static final int DURATION_MINUTES = 5;
    private static final int HP_LOSS_PER_TURN = 5;

    public BleedStatus() {
        super("Bleeding", DURATION_MINUTES, true, StatusType.BLEED_STATUS); // Add StatusType
    }

    @Override
    public void applyEffect(Charecter character) {
        character.setHitPoints(character.getHitPoints() - HP_LOSS_PER_TURN);
    }

    @Override
    public void expireEffect(Charecter character) {
        // Nothing to revert for BleedStatus
    }

    @Override
    public void removeEffect(Charecter character) {
        // Nothing to revert for BleedStatus
    }
}
