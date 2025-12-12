package Guild.AuroraArcanum.Spells;

import DungeonoftheBrutalKing.Charecter;
import Status.EchoOfEternityAuraStatus;
import java.util.List;

public class EchoOfEternity {
    private static final int DURATION = 8; // seconds

    public void cast(Charecter caster, List<Charecter> allCharacters) {
        caster.addStatus(new EchoOfEternityAuraStatus(DURATION, caster));
    }
}