
package Guild.AuroraArcanum.Spells;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;

public class Starfall implements Spell {
    private static final int BASE_DAMAGE = 18;

    // Casts Starfall on a single target
    public void cast(Charecter caster, Charecter target) {
        int radiantDamage = BASE_DAMAGE + (int)(caster.getIntelligence() * 1.3);
        target.setHitPoints(target.getHitPoints() - radiantDamage);
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
