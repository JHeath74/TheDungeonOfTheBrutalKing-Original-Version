package Status;

import DungeonoftheBrutalKing.Charecter;

public class FearStatus extends Status {
    private static final double DEFENSE_REDUCTION = 0.3; // 30% defense reduction
    private int originalDefense;

    public FearStatus(int duration) {
        super("Fear", duration, true); // true: negative effect
    }

    @Override
    public void applyEffect(Charecter character) {
        originalDefense = character.getDefense();
        int reducedDefense = (int) (originalDefense * (1 - DEFENSE_REDUCTION));
        character.setDefense(reducedDefense);
        // Optionally, set flags or call methods to block actions/attacks/spells
    }

    @Override
    public void expireEffect(Charecter character) {
        character.setDefense(originalDefense);
        // Optionally, clear flags or restore abilities
    }

    @Override
    public void removeEffect(Charecter character) {
        character.setDefense(originalDefense);
        // Optionally, clear flags or restore abilities
    }

    // If your system uses these, include them (without @Override)
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
