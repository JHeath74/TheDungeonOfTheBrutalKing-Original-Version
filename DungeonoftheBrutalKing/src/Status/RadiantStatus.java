
package Status;

import DungeonoftheBrutalKing.Charecter;

public class RadiantStatus extends Status {
    private static final double HEAL_PERCENT = 0.10; // 10% of max HP per turn

    public RadiantStatus(int duration) {
        super("Radiant", duration, false); // false: not a negative effect
    }

    @Override
    public void applyEffect(Charecter character) {
        int maxHP = character.getMaxHitPoints();
        int healAmount = (int) Math.ceil(maxHP * HEAL_PERCENT);
        character.restoreHitPoints(healAmount);
    }

    @Override
    public void expireEffect(Charecter character) {
        // No additional effect on expire
    }

    @Override
    public void removeEffect(Charecter character) {
        // No additional effect on remove
    }
}
