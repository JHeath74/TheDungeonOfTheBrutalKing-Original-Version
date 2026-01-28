
// src/Guild/AuroraArcanum/Spells/EtherealChains.java
package Guild.AuroraArcanum.Spells;

import java.util.List;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import Status.EtherealChainsStatus;

public class EtherealChains implements Spell {
    private static final int DURATION = 3; // rounds

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (target != null) {
            target.addStatus(new EtherealChainsStatus(DURATION));
        }
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        if (allCharacters != null && !allCharacters.isEmpty()) {
            cast(caster, allCharacters.get(0));
        }
    }

    @Override
    public void cast(Charecter caster) {
        // Not applicable: requires a target
    }

    @Override
    public void cast() {
        // Not applicable: requires a caster and target
    }

    @Override
    public void cast(int toonWisdom) {
        // Not applicable for this spell
    }

    @Override
    public void castWithIntelligence(int toonIntelligence) {
        // Not applicable for this spell
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        // Not applicable for this spell
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
    public String getName() {
        return "Ethereal Chains";
    }
}
