
package Guild.AuroraArcanum.Spells;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;

import java.util.Random;

public class AstralStep implements Spell {
    private static final int MAX_DISTANCE = 3;
    private static final int DUNGEON_WIDTH = 128;
    private static final int DUNGEON_HEIGHT = 128;

    public void cast(Charecter caster) {
        int[] pos = new int[3];
        caster.getPosition(pos);
        Random rand = new Random();

        int attempts = 0;
        int newX = pos[0], newY = pos[1];
        boolean found = false;

        while (attempts < 10) {
            int dx = 0, dy = 0;
            while (dx == 0 && dy == 0) {
                dx = rand.nextInt(2 * MAX_DISTANCE + 1) - MAX_DISTANCE;
                dy = rand.nextInt(2 * MAX_DISTANCE + 1) - MAX_DISTANCE;
            }
            newX = pos[0] + dx;
            newY = pos[1] + dy;
            if (newX >= 0 && newX < DUNGEON_WIDTH && newY >= 0 && newY < DUNGEON_HEIGHT) {
                found = true;
                break;
            }
            attempts++;
        }

        if (found) {
            caster.setPosition(newX, newY, pos[2]);
        }
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
