
// src/Status/Blind.java
package Status;

import DungeonoftheBrutalKing.Character;

public class BlindStatus extends Status {
    private static final int DURATION_MINUTES = 3; // Example duration
    private static final double HIT_CHANCE_REDUCTION = 0.5; // 50% reduction

    private double originalHitChance = 1.0;

    public BlindStatus() {
        super("Blind", DURATION_MINUTES);
    }

    @Override
    public void applyEffect(Character character) {
        // Store original hit chance if available
        originalHitChance = character.getHitChance();
        character.setHitChance(originalHitChance * HIT_CHANCE_REDUCTION);
    }

    @Override
    public void expireEffect(Character character) {
        // Restore original hit chance
        character.setHitChance(originalHitChance);
    }
}
