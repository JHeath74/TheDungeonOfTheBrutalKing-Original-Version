
// src/Status/EtherealChainsStatus.java
package DungeonoftheBrutalKing.Status;

import DungeonoftheBrutalKing.Character;

public final class EtherealChainsStatus extends Status {

    public EtherealChainsStatus(int duration) {
        super("Ethereal Chains", Math.max(0, duration), StatusPolarity.NEGATIVE, StatusType.ETHEREAL_CHAINS_STATUS);
    }

    public boolean blocksAttack() {
        return true;
    }

    @Override
    public void applyEffect(Character character) {
        // Effect logic here (e.g., prevent attacking)
        // If you modify character state here, revert it in removeEffect(...).
    }

    @Override
    public void removeEffect(Character character) {
        // Restore ability to attack if you changed character state in applyEffect(...).
    }

    @Override
    public String getDescription() {
        return "Ethereal Chains: prevents attacking while active\\.";
    }
}
