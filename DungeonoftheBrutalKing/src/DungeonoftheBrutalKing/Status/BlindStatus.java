
// src/Status/BlindStatus.java
package DungeonoftheBrutalKing.Status;

import DungeonoftheBrutalKing.Character;

public final class BlindStatus extends Status {

    private static final int DEFAULT_DURATION_MINUTES = 2;
    private static final double HIT_CHANCE_MULTIPLIER = 0.5;

    private double originalHitChance = 1.0;

    public BlindStatus() {
        this(DEFAULT_DURATION_MINUTES);
    }

    public BlindStatus(int durationMinutes) {
        super("Blinded", Math.max(0, durationMinutes), StatusPolarity.NEGATIVE, StatusType.BLIND_STATUS);
    }

    @Override
    public void applyEffect(Character character) {
        if (character == null) return;

        originalHitChance = character.getHitChance();
        character.setHitChance(originalHitChance * HIT_CHANCE_MULTIPLIER);
    }

    @Override
    public void removeEffect(Character character) {
        restore(character);
    }

    private void restore(Character character) {
        if (character == null) return;
        character.setHitChance(originalHitChance);
    }

    @Override
    public String getDescription() {
        return "Blinded: reduces hit chance by 50\\% while active\\.";
    }
}
