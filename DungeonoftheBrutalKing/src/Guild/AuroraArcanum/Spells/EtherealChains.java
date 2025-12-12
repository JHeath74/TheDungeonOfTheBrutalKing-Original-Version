
package Guild.AuroraArcanum.Spells;

import DungeonoftheBrutalKing.Charecter;
import Status.EtherealChainsStatus;

public class EtherealChains {
    private static final int DURATION = 3; // rounds

    // Binds the target, preventing attacks for DURATION rounds
    public void cast(Charecter caster, Charecter target) {
        target.addStatus(new EtherealChainsStatus(DURATION));
        // Optionally, add visual or log effect here
    }
}
