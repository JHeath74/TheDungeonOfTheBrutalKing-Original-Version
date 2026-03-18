
// src/Status/HiddenStatus.java
package Status;

import DungeonoftheBrutalKing.Charecter;

public final class HiddenStatus extends Status {

    public HiddenStatus(int durationMinutes) {
        super("Hidden", durationMinutes, StatusPolarity.POSITIVE, StatusType.HIDDEN_STATUS);
    }

    @Override
    public void applyEffect(Charecter target) {
        if (target == null) return;
        target.setHidden(true);
    }

    @Override
    public void removeEffect(Charecter target) {
        if (target == null) return;
        target.setHidden(false);
    }

    @Override
    public String getDescription() {
        return "Hidden: You are concealed, increasing your chance to critically strike and avoid detection.";
    }
}
