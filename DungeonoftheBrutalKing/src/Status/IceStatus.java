
package Status;

import DungeonoftheBrutalKing.Charecter;

public class IceStatus extends Status {
    private static final int DURATION_MINUTES = 5; // set your desired duration

    public IceStatus() {
        super("Frozen", DURATION_MINUTES, true, StatusType.ICE_STATUS); // Add StatusType
    }

    @Override
    public void applyEffect(Charecter character) {

    }

    @Override
    public void expireEffect(Charecter character) {

    }

    @Override
    public void removeEffect(Charecter character) {

    }
}
