package Status;

import DungeonoftheBrutalKing.Charecter;

public class DefenseUpStatus extends Status {
    private final int defenseBonus;
    private int originalDefense;

    public DefenseUpStatus(int duration, int defenseBonus) {
        super("Defense Up", duration, false, StatusType.DEFENSE_UP_STATUS); // Add StatusType.DEFENSE_UP
        this.defenseBonus = defenseBonus;
    }

    @Override
    public void applyEffect(Charecter character) {
        originalDefense = character.getDefense();
        character.setDefense(originalDefense + defenseBonus);
    }

    @Override
    public void expireEffect(Charecter character) {
        character.setDefense(originalDefense);
    }

    @Override
    public void removeEffect(Charecter character) {
        character.setDefense(originalDefense);
    }
}
