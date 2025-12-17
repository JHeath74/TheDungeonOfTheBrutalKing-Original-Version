
package Guild.AuroraArcanum.Spells;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import Status.IllusoryDoubleStatus;

public class IllusoryDouble implements Spell {
    private static final int DURATION = 3; // rounds
    private static final double EVADE_BONUS = 0.25; // 25% increased evade chance

    // Casts Illusory Double on the caster
    public void cast(Charecter caster) {
        caster.addStatus(new IllusoryDoubleStatus(DURATION, EVADE_BONUS));
        // Optionally, add visual or log effect here
    }

	@Override
	public boolean isGuildSpell() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Guild getSpellGuild() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRequiredMagicPoints() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void cast(int toonWisdom) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void castWithIntelligence(int toonIntelligence) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cast(int toonWisdom, int toonIntelligence) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}
