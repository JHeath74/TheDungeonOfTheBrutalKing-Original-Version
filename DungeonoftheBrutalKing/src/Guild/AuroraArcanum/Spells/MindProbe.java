
package Guild.AuroraArcanum.Spells;

import DungeonoftheBrutalKing.Charecter;
import Status.MindProbeStatus;

public class MindProbe {
    private static final int DURATION = 3; // rounds
    private static final double EVADE_BONUS = 0.15; // 15% increased evade chance

    // Casts Mind Probe on the caster, after reading the target's thoughts
    public void cast(Charecter caster, Charecter target) {
        // Optionally, log or display the target's surface thoughts here
        caster.addStatus(new MindProbeStatus(DURATION, EVADE_BONUS));
    }
}
