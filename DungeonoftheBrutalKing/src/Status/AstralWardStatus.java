
// src/Status/AstralWardStatus.java
package Status;

public class AstralWardStatus extends Status {
    public AstralWardStatus(int durationMinutes) {
        super("Astral Ward", durationMinutes, false, StatusType.ASTRAL_WARD_STATUS);
    }

    public int getDurationMinutes() {
        return super.isExpired() ? 0 : (super.getDurationSeconds() + 59) / 60;
    }

    @Override
    public String getDescription() {
        return "Grants a temporary shield that absorbs damage for " + getDurationMinutes() + " minutes.";
    }

    // Override other methods as needed for shield logic
}
