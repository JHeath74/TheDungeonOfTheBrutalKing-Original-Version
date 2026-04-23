
// src/Status/PoisonStatus.java
package DungeonoftheBrutalKing.Status;

import DungeonoftheBrutalKing.Character;

public final class PoisonStatus extends Status {
    private static final int POISON_DAMAGE = 5; // HP lost per turn

    public PoisonStatus(int duration) {
        super("Poison", Math.max(0, duration), StatusPolarity.NEGATIVE, StatusType.POISON_STATUS);
    }

    @Override
    public void applyEffect(Character character) {
        if (character == null) return;
        character.takeDamage(Math.max(0, POISON_DAMAGE));
    }

    @Override
    public void removeEffect(Character character) {
        // No persistent stat changes to restore.
    }

    @Override
    public String getDescription() {
        return "Poison: loses " + POISON_DAMAGE + " HP each turn while active\\.";
    }
}
