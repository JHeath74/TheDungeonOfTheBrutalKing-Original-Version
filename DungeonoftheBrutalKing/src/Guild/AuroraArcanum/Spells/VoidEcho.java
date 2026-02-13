
// src/Guild/AuroraArcanum/Spells/VoidEcho.java
package Guild.AuroraArcanum.Spells;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import Status.VoidEchoStatus;
import java.util.List;

public class VoidEcho implements Spell {
    private static final int INTERRUPT_DURATION = 3; // seconds
    private static final int MANA_RESTORE = 10;
    private static final int REQUIRED_MAGIC_POINTS = 9;
    private static final Guild SPELL_GUILD = Guild.AURORA_ARCANUM;

    private boolean canUseSpell(Charecter caster) {
        return caster != null && caster.getGuild() == SPELL_GUILD;
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (canUseSpell(caster) && target != null) {
            target.addStatus(new VoidEchoStatus(INTERRUPT_DURATION, false));
            caster.setMagicPoints(caster.getMagicPoints() + MANA_RESTORE);
        }
    }

    @Override
    public void cast(Charecter caster, List<Charecter> enemies) {
        if (canUseSpell(caster) && enemies != null) {
            for (Charecter enemy : enemies) {
                enemy.addStatus(new VoidEchoStatus(INTERRUPT_DURATION, false));
            }
            caster.setMagicPoints(caster.getMagicPoints() + MANA_RESTORE);
        }
    }

    @Override
    public void cast(Charecter caster) {
        if (canUseSpell(caster)) {
            cast(caster, caster);
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
        return "Void Echo";
    }

    @Override
    public String getDescription() {
        return "Void Echo: Interrupts a target and restores mana. Only available to AuroraArcanum guild members.";
    }

    @Override
    public void castWithStrength(Charecter enemy, double strength) {
        // Not applicable for this spell, so do nothing
    }
}
