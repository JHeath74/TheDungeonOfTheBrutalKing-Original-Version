
// src/Guild/AuroraArcanum/Spells/MindProbe.java
package Guild.AuroraArcanum.Spells;

import java.util.List;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import Status.MindProbeStatus;

public class MindProbe implements Spell {
    private static final int DURATION = 3; // rounds
    private static final double EVADE_BONUS = 0.15; // 15% increased evade chance
    private static final int REQUIRED_MAGIC_POINTS = 7;
    private static final Guild SPELL_GUILD = Guild.AURORA_ARCANUM;

    private boolean canUseSpell(Charecter caster) {
        return caster != null && caster.getGuild() == SPELL_GUILD;
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (canUseSpell(caster)) {
            caster.addStatus(new MindProbeStatus(DURATION, EVADE_BONUS));
            // Optionally: read/display target's thoughts here
        }
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        if (canUseSpell(caster)) {
            cast(caster, (Charecter) null);
        }
    }

    @Override
    public void cast(Charecter caster) {
        if (canUseSpell(caster)) {
            cast(caster, (Charecter) null);
        }
    }

    @Override
    public void cast() {
        // Not applicable: requires a caster
    }

    @Override
    public void cast(int toonWisdom) {
        // Not used for this spell
    }

    @Override
    public void castWithIntelligence(int toonIntelligence) {
        // Not used for this spell
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        // Not used for this spell
    }

    @Override
    public boolean isGuildSpell() {
        return true;
    }

    @Override
    public Guild getSpellGuild() {
        return SPELL_GUILD;
    }

    @Override
    public int getRequiredMagicPoints() {
        return REQUIRED_MAGIC_POINTS;
    }

    @Override
    public String getName() {
        return "Mind Probe";
    }

    @Override
    public String getDescription() {
        return "Mind Probe: Allows the caster to read thoughts and gain an evade bonus. Only available to AuroraArcanum guild members.";
    }

    @Override
    public void castWithStrength(Charecter enemy, double strength) {
        // Not applicable for this spell, so do nothing
    }
}
