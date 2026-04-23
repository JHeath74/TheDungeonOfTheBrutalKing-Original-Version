
// src/Status/IllusoryDoubleStatus.java
package DungeonoftheBrutalKing.Status;

import DungeonoftheBrutalKing.Character;

public final class IllusoryDoubleStatus extends Status {
    private final double evadeBonus;

    public IllusoryDoubleStatus(int durationMinutes, double evadeBonus) {
        super("Illusory Double", durationMinutes, StatusPolarity.POSITIVE, StatusType.ILLUSORY_DOUBLE_STATUS);
        this.evadeBonus = Math.max(0.0, evadeBonus);
    }

    @Override
    public void applyEffect(Character character) {
        if (character == null) return;
        character.setEvadeChance(character.getEvadeChance() + evadeBonus);
    }

    @Override
    public void removeEffect(Character character) {
        if (character == null) return;
        character.setEvadeChance(character.getEvadeChance() - evadeBonus);
    }

    @Override
    public String getDescription() {
        return "Illusory Double: increases evade chance while active.";
    }
}
