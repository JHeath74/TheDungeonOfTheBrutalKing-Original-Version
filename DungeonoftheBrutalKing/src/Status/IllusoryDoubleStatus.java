
package Status;

import DungeonoftheBrutalKing.Charecter;

public class IllusoryDoubleStatus extends Status {
    private final double evadeBonus;

    public IllusoryDoubleStatus(int duration, double evadeBonus) {
        super("Illusory Double", duration);
        this.evadeBonus = evadeBonus;
    }

    @Override
    public void applyEffect(Charecter character) {
        character.setEvadeChance(character.getEvadeChance() + evadeBonus);
        reduceDuration(1);
    }

    @Override
    public void onExpire(Charecter character) {
        character.setEvadeChance(character.getEvadeChance() - evadeBonus);
    }
}
