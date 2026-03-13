
// Example weapon-side proc helper (returns an instance you can apply)
// Use your real apply-status pipeline elsewhere.
package Guild.ObsidianHexCoven.Weapon;

import java.util.concurrent.ThreadLocalRandom;

import DungeonoftheBrutalKing.Charecter;
import Status.DazeStatus;
import Status.Status;

public class NightSigilWand /* extends WeaponManager */ {

    private static final int STATUS_PROC_CHANCE_PERCENT = 20;
    private static final int DAZE_DURATION_TURNS = 2;

    /**
     * Call this after a successful hit.
     * @return a new DazeStatus to apply, or null if it did not proc.
     */
    public Status tryProcDazeStatus(Charecter attacker, Charecter target) {
        if (attacker == null || target == null) return null;

        int roll = ThreadLocalRandom.current().nextInt(100);
        if (roll >= STATUS_PROC_CHANCE_PERCENT) return null;

        return new DazeStatus(DAZE_DURATION_TURNS);
    }
}
