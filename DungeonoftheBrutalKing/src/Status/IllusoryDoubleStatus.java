package Status;

import DungeonoftheBrutalKing.Charecter;

public class IllusoryDoubleStatus extends Status {
    private final double evadeBonus;

    public IllusoryDoubleStatus(int duration, double evadeBonus) {
        super("Illusory Double", duration, false); // false: not a negative effect
        this.evadeBonus = evadeBonus;
    }

    @Override
    public void applyEffect(Charecter character) {
        character.setEvadeChance(character.getEvadeChance() + evadeBonus);
    }

    @Override
    public void expireEffect(Charecter character) {
        character.setEvadeChance(character.getEvadeChance() - evadeBonus);
    }

    @Override
    public void removeEffect(Charecter character) {
        character.setEvadeChance(character.getEvadeChance() - evadeBonus);
    }
}
