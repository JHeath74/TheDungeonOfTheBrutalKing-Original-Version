
// src/Status/HiddenStatus.java
package Status;

import DungeonoftheBrutalKing.Charecter;

public class HiddenStatus extends Status {

    public HiddenStatus(int durationMinutes) {
        super("Hidden", durationMinutes, false, StatusType.HIDDEN_STATUS);
    }

    @Override
    public void applyEffect(Charecter target) {
        target.setHidden(true);
    }

    @Override
    public void removeEffect(Charecter target) {
        target.setHidden(false);
    }

    @Override
    public String getDescription() {
        return "Hidden: You are concealed, increasing your chance to critically strike and avoid detection.";
    }
}
