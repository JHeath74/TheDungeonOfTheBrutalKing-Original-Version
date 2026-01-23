
package Guild.AuroraArcanum.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import Status.MindProbeStatus;

public class MindProbe implements Spell {
    private static final int DURATION = 3; // rounds
    private static final double EVADE_BONUS = 0.15; // 15% increased evade chance

    // Casts Mind Probe on the caster, after reading the target's thoughts
    public void cast(Charecter caster, Charecter target) {
        // Optionally, log or display the target's surface thoughts here
        caster.addStatus(new MindProbeStatus(DURATION, EVADE_BONUS));
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

	@Override
	public void cast(Charecter caster, List<Charecter> allCharacters) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cast(Charecter caster) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}
}
