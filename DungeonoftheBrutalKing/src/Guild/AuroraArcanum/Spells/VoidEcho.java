package Guild.AuroraArcanum.Spells;

import DungeonoftheBrutalKing.Charecter;
import Status.VoidEchoStatus;
import java.util.List;

public class VoidEcho {
    private static final int INTERRUPT_DURATION = 3; // seconds
    private static final int MANA_RESTORE = 10;

    // Casts Void Echo on a list of enemies and restores mana to the caster
    public void cast(Charecter caster, List<Charecter> enemies) {
        for (Charecter enemy : enemies) {
            // If you do not have summoned/illusion logic, just pass false
            enemy.addStatus(new VoidEchoStatus(
                INTERRUPT_DURATION,
                false
            ));
        }
        caster.setMagicPoints(caster.getMagicPoints() + MANA_RESTORE);
    }
}
