
package Guild.AuroraArcanum.Spells;

import DungeonoftheBrutalKing.Charecter;
import Status.IllusoryDoubleStatus;

public class IllusoryDouble {
    private static final int DURATION = 3; // rounds
    private static final double EVADE_BONUS = 0.25; // 25% increased evade chance

    // Casts Illusory Double on the caster
    public void cast(Charecter caster) {
        caster.addStatus(new IllusoryDoubleStatus(DURATION, EVADE_BONUS));
        // Optionally, add visual or log effect here
    }
}
