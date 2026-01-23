
// src/Status/DefenseUpStatus.java
package Status;

import DungeonoftheBrutalKing.Charecter;

public class DefenseUpStatus extends Status {
    private final int defenseBonus;

    public DefenseUpStatus(int duration, int defenseBonus) {
        super("Defense Up", duration);
        this.defenseBonus = defenseBonus;
    }

    @Override
    public void applyEffect(Charecter character) {
        character.setDefense(character.getDefense() + defenseBonus);
        reduceDuration(1);
    }

    @Override
    public void onExpire(Charecter character) {
        character.setDefense(character.getDefense() - defenseBonus);
    }
}
