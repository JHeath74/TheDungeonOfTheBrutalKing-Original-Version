
// src/Status/DefenseUpStatus.java
package DungeonoftheBrutalKing.Status;

import DungeonoftheBrutalKing.Character;

public final class DefenseUpStatus extends Status {

    private final int defenseBonus;

    public DefenseUpStatus(int durationMinutes, int defenseBonus) {
        super("Defense Up", durationMinutes, StatusPolarity.POSITIVE, StatusType.DEFENSE_UP_STATUS);
        this.defenseBonus = Math.max(0, defenseBonus);
    }

    @Override
    public void applyEffect(Character target) {
        if (target == null) return;
        target.setDefense(target.getDefense() + defenseBonus);
    }

    @Override
    public void removeEffect(Character target) {
        if (target == null) return;
        target.setDefense(target.getDefense() - defenseBonus);
    }
}
