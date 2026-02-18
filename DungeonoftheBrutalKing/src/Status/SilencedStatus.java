
// src/Status/SilencedStatus.java
package Status;

import DungeonoftheBrutalKing.Charecter;

public class SilencedStatus extends Status {

    public SilencedStatus(int durationMinutes) {
        super("Silenced", durationMinutes, true, StatusType.SILENCED_STATUS);
    }

    @Override
    public void applyEffect(Charecter target) {
        target.setSilenced(true);
    }

    @Override
    public void removeEffect(Charecter target) {
        target.setSilenced(false);
    }

    @Override
    public String getDescription() {
        return "Silenced: Unable to cast spells or use special abilities.";
    }
}
