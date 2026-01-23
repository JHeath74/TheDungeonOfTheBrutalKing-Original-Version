package Guild.AuroraArcanum.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import Status.IllusoryDoubleStatus;

public class IllusoryDouble implements Spell {
    private static final int DURATION = 3; // rounds
    private static final double EVADE_BONUS = 0.25; // 25% increased evade chance
    private static final int REQUIRED_MAGIC_POINTS = 8;

    @Override
    public void cast(Charecter caster) {
        caster.addStatus(new IllusoryDoubleStatus(DURATION, EVADE_BONUS));
        // Optionally, add visual or log effect here
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
        return REQUIRED_MAGIC_POINTS;
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
    public String getName() {
        return "Illusory Double";
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        cast(caster);
    }

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}
}
