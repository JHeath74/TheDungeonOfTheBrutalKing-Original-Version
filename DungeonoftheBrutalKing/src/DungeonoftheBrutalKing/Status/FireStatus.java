
// src/Status/FireStatus.java
package Status;

import DungeonoftheBrutalKing.Charecter;

public final class FireStatus extends Status {
    private static final int DEFAULT_DURATION_MINUTES = 4;
    private static final int ATTACK_REDUCTION = 5;
    private static final int FIRE_DAMAGE_PER_TURN = 7;

    private int originalAttackDamage;

    public FireStatus() {
        this(DEFAULT_DURATION_MINUTES);
    }

    public FireStatus(int durationMinutes) {
        super("Burned", Math.max(0, durationMinutes), StatusPolarity.NEGATIVE, StatusType.FIRE_STATUS);
    }

    @Override
    public void applyEffect(Charecter character) {
        if (character == null) return;

        originalAttackDamage = character.getAttackDamage();
        int reducedAttack = Math.max(0, originalAttackDamage - ATTACK_REDUCTION);
        character.setAttack(reducedAttack);

        int newHp = Math.max(0, character.getHitPoints() - FIRE_DAMAGE_PER_TURN);
        character.setHitPoints(newHp);
    }

    @Override
    public void removeEffect(Charecter character) {
        if (character == null) return;
        character.setAttack(Math.max(0, originalAttackDamage));
    }

    @Override
    public String getDescription() {
        return "Burned: reduces attack by " + ATTACK_REDUCTION + " and deals "
                + FIRE_DAMAGE_PER_TURN + " damage each turn while active\\.";
    }
}
