
package Guild.AuroraArcanum.Spells;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.TimeClock;
import SharedData.Guild;
import Spells.Spell;

public class CelestialWard implements Spell {
    private int baseDefense = 8;
    SharedData.GuildType guildType = SharedData.GuildType.CLERIC;
    private TimeClock timer;
    private boolean active = false;

    public int calculateDefenseBoost(Charecter charecter) {
        int intelligence = charecter.getIntelligence();
        int level = charecter.getLevel();
        return baseDefense + (intelligence * 1) + (level * 2);
    }

    public void activate() {
        timer = new TimeClock(TimeClock.Month.REBIRTH, null, null);
        timer.startClock();
        active = true;
    }

    public boolean isActive() {
        if (!active) return false;
        if (timer.getElapsedSeconds() >= 15) {
            active = false;
        }
        return active;
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
