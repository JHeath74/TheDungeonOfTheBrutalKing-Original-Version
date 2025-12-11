
package Guild.AuroraArcanum.Spells;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.TimeClock;

public class CelestialWard {
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
}
