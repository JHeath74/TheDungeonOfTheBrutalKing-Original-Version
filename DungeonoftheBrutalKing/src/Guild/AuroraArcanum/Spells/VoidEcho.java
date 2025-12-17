package Guild.AuroraArcanum.Spells;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import Status.VoidEchoStatus;
import java.util.List;

public class VoidEcho implements Spell {
    private static final int INTERRUPT_DURATION = 3; // seconds
    private static final int MANA_RESTORE = 10;

    // Casts Void Echo on a list of enemies and restores mana to the caster
    public void cast(Charecter caster, List<Charecter> enemies) {
        for (Charecter enemy : enemies) {
            // If you do not have summoned/illusion logic, just pass false
            enemy.addStatus(new VoidEchoStatus(
                INTERRUPT_DURATION,
                false
            ));
        }
        caster.setMagicPoints(caster.getMagicPoints() + MANA_RESTORE);
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
