package Guild.CelestialArcaneOrder.Spells;

import java.util.List;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import Status.Status;
import Status.DefenseUpStatus;


public class HolyAegis implements Spell {

	private String name;
    private static final String DESCRIPTION = "A radiant shield surrounds the cleric, deflecting arrows and reducing incoming damage.";
    private static final int MANA_COST = 15;
    private static final int DURATION = 5; // turns

    public HolyAegis() {
        this.name = "Holy Aegis";
    }

    @Override
    public void cast(Charecter caster) {
        if (caster.getMagicPoints() < MANA_COST) {
            return;
        }
        caster.setMagicPoints(caster.getMagicPoints() - MANA_COST);
        if (caster.getStatusManager() != null) {
            // Example: +10 defense, adjust as needed
            Status defenseStatus = new DefenseUpStatus(DURATION, 10);
            caster.getStatusManager().addStatus(defenseStatus);
            defenseStatus.applyEffect(caster);
        }
    }

    @Override
    public boolean isGuildSpell() {
        return true;
    }

    @Override
    public Guild getSpellGuild() {
        return Guild.CELESTIAL_ARCANE_ORDER;
    }

    @Override
    public int getRequiredMagicPoints() {
        return 0;
    }

    @Override
    public void cast(int toonWisdom) {}

    @Override
    public void castWithIntelligence(int toonIntelligence) {}

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {}

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {}

    @Override
    public void cast() {}
}
