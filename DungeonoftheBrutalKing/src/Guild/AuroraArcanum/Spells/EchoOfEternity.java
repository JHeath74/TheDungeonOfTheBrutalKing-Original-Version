package Guild.AuroraArcanum.Spells;

import DungeonoftheBrutalKing.Charecter;
import Spells.Spell;
import Status.EchoOfEternityAuraStatus;
import java.util.List;

public class EchoOfEternity implements Spell {
    private static final int DURATION = 8; // seconds

    public void cast(Charecter caster, List<Charecter> allCharacters) {
        caster.addStatus(new EchoOfEternityAuraStatus(DURATION, caster));
    }
}