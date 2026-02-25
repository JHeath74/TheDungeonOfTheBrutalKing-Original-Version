
package Status;

import DungeonoftheBrutalKing.Charecter;

public class BlindStatus extends Status {

    private static final int DEFAULT_DURATION = 2;
    private static final double HIT_CHANCE_MULTIPLIER = 0.5;

    private double originalHitChance = 1.0;

    public BlindStatus() {
        this(DEFAULT_DURATION);
    }

    public BlindStatus(int duration) {
        super("Blinded", Math.max(0, duration), true, StatusType.BLIND_STATUS);
    }

    @Override
    public void applyEffect(Charecter character) {
        if (character == null) return;

        originalHitChance = character.getHitChance();
        character.setHitChance(originalHitChance * HIT_CHANCE_MULTIPLIER);
    }

    @Override
    public void expireEffect(Charecter character) {
        restore(character);
    }

    @Override
    public void removeEffect(Charecter character) {
        restore(character);
    }

    private void restore(Charecter character) {
        if (character == null) return;
        character.setHitChance(originalHitChance);
    }
}
