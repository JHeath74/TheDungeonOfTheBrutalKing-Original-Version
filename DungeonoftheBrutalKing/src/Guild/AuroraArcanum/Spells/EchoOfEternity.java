
// src/Guild/AuroraArcanum/Spells/EchoOfEternity.java
package Guild.AuroraArcanum.Spells;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import Status.EchoOfEternityAuraStatus;
import java.util.List;

public class EchoOfEternity implements Spell {
    private static final int DURATION = 8; // seconds

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        if (caster != null) {
            caster.addStatus(new EchoOfEternityAuraStatus(DURATION, caster));
        }
    }

    @Override
    public boolean isGuildSpell() {
        return true;
    }

    @Override
    public Guild getSpellGuild() {
        return Guild.AURORA_ARCANUM;
    }

    @Override
    public int getRequiredMagicPoints() {
        return 10;
    }

    @Override
    public void cast(int toonWisdom) {
        // No effect for wisdom in current implementation
    }

    @Override
    public void castWithIntelligence(int toonIntelligence) {
        // No effect for intelligence in current implementation
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        // No effect for both stats in current implementation
    }

    @Override
    public String getName() {
        return "Echo of Eternity";
    }

    @Override
    public void cast(Charecter caster) {
        if (caster != null) {
            caster.addStatus(new EchoOfEternityAuraStatus(DURATION, caster));
        }
    }

    @Override
    public void cast() {
        // No caster provided, nothing to apply
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (target != null) {
            target.addStatus(new EchoOfEternityAuraStatus(DURATION, caster));
        }
    }
}
