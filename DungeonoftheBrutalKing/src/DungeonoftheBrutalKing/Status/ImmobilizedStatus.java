
// src/Status/ImmobilizedStatus.java
package Status;

public class ImmobilizedStatus extends Status {

    public ImmobilizedStatus(int duration) {
        super("Immobilized", Math.max(0, duration), StatusPolarity.NEGATIVE, StatusType.IMMOBILIZED_STATUS);
    }

    @Override
    public boolean preventsMovement() {
        return true;
    }

    @Override
    public boolean preventsActions() {
        return false;
    }

    @Override
    public String getDescription() {
        return "Cannot move, but can act.";
    }
}
