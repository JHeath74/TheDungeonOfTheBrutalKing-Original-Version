
// src/Status/Blind.java
package Status;

import DungeonoftheBrutalKing.Charecter;

public class BlindStatus extends Status {
    private static final int DURATION_MINUTES = 3; // Example duration
    private static final double HIT_CHANCE_REDUCTION = 0.5; // 50% reduction

    private double originalHitChance = 1.0;

    public BlindStatus() {
        super("Blind", DURATION_MINUTES);
    }

    @Override
    public void applyEffect(Charecter character) {
        // Store original hit chance if available
        originalHitChance = character.getHitChance();
        character.setHitChance(originalHitChance * HIT_CHANCE_REDUCTION);
    }

    @Override
    public void expireEffect(Charecter character) {
        // Restore original hit chance
        character.setHitChance(originalHitChance);
    }
}
