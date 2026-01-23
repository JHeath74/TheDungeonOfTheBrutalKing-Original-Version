
package Guild.AuroraArcanum.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.TimeClock;
import SharedData.Guild;
import Spells.Spell;

public class TimeDialation implements Spell {
    private int baseSlowPercent = 20; // slows by 20% base
    SharedData.GuildType guildType = SharedData.GuildType.MAGE;
    private TimeClock timer;
    private boolean active = false;
    private int enemySilenceRounds = 0;

    public int calculateSlowAmount(Charecter charecter) {
        int intelligence = charecter.getIntelligence();
        int level = charecter.getLevel();
        return baseSlowPercent + (int)(intelligence * 0.5) + (level * 1);
    }

    public void activate() {
        timer = new TimeClock(TimeClock.Month.REBIRTH, null, null);
        timer.startClock();
        enemySilenceRounds = 2;
        active = true;
    }

    public boolean isActive() {
        if (!active) return false;
        if (timer.getElapsedSeconds() >= 15) {
            active = false;
        }
        return active;
    }

    // Call this at the end of each enemy round
    public void decrementEnemySilence() {
        if (enemySilenceRounds > 0) {
            enemySilenceRounds--;
        }
    }

    // Call this before the enemy tries to attack
    public boolean isEnemySilenced() {
        return enemySilenceRounds > 0;
    }

    // Example: slows enemy speed by the calculated percentage
    public int applySlow(Charecter enemy, int originalSpeed, Charecter caster) {
        if (isActive()) {
            int slowPercent = calculateSlowAmount(caster);
            int slowedSpeed = originalSpeed - (originalSpeed * slowPercent / 100);
            return Math.max(1, slowedSpeed);
        }
        return originalSpeed;
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
