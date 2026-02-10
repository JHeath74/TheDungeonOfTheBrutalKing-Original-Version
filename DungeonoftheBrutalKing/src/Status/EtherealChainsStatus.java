package Status;

import DungeonoftheBrutalKing.Charecter;

public class EtherealChainsStatus extends Status {
    public EtherealChainsStatus(int duration) {
        super("Ethereal Chains", duration, true, StatusType.ETHEREAL_CHAINS_STATUS); // Add StatusType
    }

    public boolean blocksAttack() {
        return true;
    }

    @Override
    public void applyEffect(Charecter character) {
        // Effect logic here (e.g., prevent attacking)
    }

    @Override
    public void expireEffect(Charecter character) {
        // Restore ability to attack if needed
    }

    @Override
    public void removeEffect(Charecter character) {
        // Clean up if needed
    }
}
