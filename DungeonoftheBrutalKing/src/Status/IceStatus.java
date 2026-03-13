
package Status;

import DungeonoftheBrutalKing.Charecter;

public class IceStatus extends Status {
    private static final int DURATION_MINUTES = 5;

    // Tuning values (keep small; this runs per-turn/tick depending on your engine).
    private static final int ATTACK_REDUCTION = 3;
    private static final int ICE_DAMAGE_PER_TURN = 3;

    private Integer originalAttack; // null = not applied yet

    public IceStatus() {
        super("Frozen", DURATION_MINUTES, true, StatusType.ICE_STATUS);
    }

    @Override
    public void applyEffect(Charecter character) {
        if (character == null) return;

        // Capture baseline once so repeated apply calls don't keep reducing attack.
        if (originalAttack == null) {
            originalAttack = character.getAttackDamage();
        }

        int reducedAttack = Math.max(0, originalAttack - ATTACK_REDUCTION);
        character.setAttack(reducedAttack);

        // Apply per-turn damage (clamp at 0 HP minimum).
        int newHp = Math.max(0, character.getHitPoints() - ICE_DAMAGE_PER_TURN);
        character.setHitPoints(newHp);
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
        if (originalAttack != null) {
            character.setAttack(Math.max(0, originalAttack));
        }
        originalAttack = null;
    }
}
