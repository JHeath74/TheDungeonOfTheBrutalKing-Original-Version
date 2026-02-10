package Status;

import DungeonoftheBrutalKing.Charecter;

public class BlindStatus extends Status {
    private static final int DURATION_MINUTES = 3;
    private static final double HIT_CHANCE_REDUCTION = 0.5;

    private double originalHitChance = 1.0;

    public BlindStatus() {
        super("Blind", DURATION_MINUTES, true, StatusType.BLIND_STATUS); // Add StatusType.BLIND
    }

    @Override
    public void applyEffect(Charecter character) {
        originalHitChance = character.getHitChance();
        character.setHitChance(originalHitChance * HIT_CHANCE_REDUCTION);
    }

    @Override
    public void expireEffect(Charecter character) {
        character.setHitChance(originalHitChance);
    }

    @Override
    public void removeEffect(Charecter character) {
        character.setHitChance(originalHitChance);
    }
}
