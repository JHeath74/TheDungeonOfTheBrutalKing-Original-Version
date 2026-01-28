package Guild.CelestialArcaneOrder.Spells;

import java.util.List;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import Status.Status;
import Status.DefenseUpStatus;

public class HolyAegis implements Spell {

    private static final String SPELL_NAME = "Holy Aegis";
    private static final String DESCRIPTION = "A radiant shield surrounds the cleric, deflecting arrows and reducing incoming damage.";
    private static final int REQUIRED_MAGIC_POINTS = 15;
    private static final int DURATION = 5; // turns
    private static final int DEFENSE_BONUS = 10;
    private static final Guild SPELL_GUILD = Guild.CELESTIAL_ARCANE_ORDER;

    public HolyAegis() {}

    // Core spell logic: applies defense status to target
    private void applyAegis(Charecter target) {
        if (target == null) return;
        if (target.getMagicPoints() < REQUIRED_MAGIC_POINTS) {
            System.out.println(target.getName() + " does not have enough magic points to cast Holy Aegis!");
            return;
        }
        target.setMagicPoints(target.getMagicPoints() - REQUIRED_MAGIC_POINTS);
        if (target.getStatusManager() != null) {
            Status defenseStatus = new DefenseUpStatus(DURATION, DEFENSE_BONUS);
            target.getStatusManager().addStatus(defenseStatus);
            defenseStatus.applyEffect(target);
            System.out.println(target.getName() + " is shielded by Holy Aegis (+10 defense for 5 turns).");
        }
    }

    @Override
    public void cast(Charecter caster) {
        applyAegis(caster);
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        applyAegis(target != null ? target : caster);
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        if (allCharacters != null && !allCharacters.isEmpty()) {
            for (Charecter ch : allCharacters) {
                applyAegis(ch);
            }
        }
    }

    @Override
    public void cast() {
        // Not applicable: requires a character
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
        return SPELL_NAME;
    }
}
