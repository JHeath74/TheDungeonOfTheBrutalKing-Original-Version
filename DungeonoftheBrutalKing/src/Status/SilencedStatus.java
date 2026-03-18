
// src/Status/SilencedStatus.java
package Status;

import DungeonoftheBrutalKing.Charecter;

public final class SilencedStatus extends Status {

    private boolean applied;
    private boolean previousSilenced;

    public SilencedStatus(int durationTurns) {
        super("Silenced", Math.max(0, durationTurns), StatusPolarity.NEGATIVE, StatusType.SILENCED_STATUS);
    }

    @Override
    public void applyEffect(Charecter target) {
        if (target == null) return;
        if (applied) return;

        previousSilenced = target.isSilenced();
        target.setSilenced(true);
        applied = true;
    }

    @Override
    public void removeEffect(Charecter target) {
        if (target == null) return;
        if (!applied) return;

        target.setSilenced(previousSilenced);
        applied = false;
    }

    @Override
    public String getDescription() {
        return "Silenced: Unable to cast spells or use special abilities\\.";
    }
}
