
package Status;

import DungeonoftheBrutalKing.Charecter;

public class FearStatus extends Status {
    private static final double DEFENSE_REDUCTION = 0.3; // 30% defense reduction
    private int originalDefense;

    public FearStatus(int duration) {
        super("Fear", duration, true, StatusType.FEAR_STATUS); // Add StatusType
    }

    @Override
    public void applyEffect(Charecter character) {
        originalDefense = character.getDefense();
        int reducedDefense = (int) (originalDefense * (1 - DEFENSE_REDUCTION));
        character.setDefense(reducedDefense);
    }

    @Override
    public void expireEffect(Charecter character) {
        character.setDefense(originalDefense);
    }

    @Override
    public void removeEffect(Charecter character) {
        character.setDefense(originalDefense);
    }

    public boolean canAttack() {
        return false;
    }

    public boolean canCastSpells() {
        return false;
    }

    public boolean canAct() {
        return false;
    }
}
