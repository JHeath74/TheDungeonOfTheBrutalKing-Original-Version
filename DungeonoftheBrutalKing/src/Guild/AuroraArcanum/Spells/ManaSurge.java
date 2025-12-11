
java
package Guild.AuroraArcanum.Spells;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.TimeClock;

public class ManaSurge {
    private int baseMagicBoost = 10;
    SharedData.GuildType guildType = SharedData.GuildType.MAGE;
    private TimeClock timer;
    private boolean active = false;

    public int calculateMagicBoost(Charecter charecter) {
        int intelligence = charecter.getIntelligence();
        int level = charecter.getLevel();
        return baseMagicBoost + (intelligence * 2) + (level * 1);
    }

    public void activate(Charecter charecter) {
        timer = new TimeClock(TimeClock.Month.REBIRTH, null, null);
        timer.startClock();
        healCaster(charecter);
        active = true;
    }

    private void healCaster(Charecter charecter) {
        int maxHealth = charecter.getMaxHitPoints();
        int healAmount = (int) Math.ceil(maxHealth * 0.05);
        int newHealth = Math.min(charecter.getHitPoints() + healAmount, maxHealth);
        charecter.setHitPoints(newHealth);
    }

    public boolean isActive() {
        if (!active) return false;
        if (timer.getElapsedSeconds() >= 15) {
            active = false;
        }
        return active;
    }
}
