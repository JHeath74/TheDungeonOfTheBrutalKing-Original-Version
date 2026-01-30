
// src/Status/EtherealChainsStatus.java
package Status;

import DungeonoftheBrutalKing.Charecter;

public class EtherealChainsStatus extends Status {
    public EtherealChainsStatus(int duration) {
        super("Ethereal Chains", duration, true); // true: negative effect
    }

    // Remove @Override since blocksAttack is not in the superclass
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
