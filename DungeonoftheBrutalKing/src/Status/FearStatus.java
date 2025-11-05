
package Status;

import DungeonoftheBrutalKing.Charecter;

public class FearStatus extends Status {
    private static final double DEFENSE_REDUCTION = 0.3; // 30% defense reduction
    private boolean applied = false;
    private int originalDefense;

    public FearStatus(int duration) {
        super("Fear", duration);
    }

    @Override
    public void apply(Charecter character) {
        if (!applied) {
            originalDefense = character.getDefense();
            int reducedDefense = (int) (originalDefense * (1 - DEFENSE_REDUCTION));
            character.setDefense(reducedDefense);
            applied = true;
        }
    }

    @Override
    public void remove(Charecter character) {
        if (applied) {
            character.setDefense(originalDefense);
            applied = false;
        }
    }

    @Override
    public boolean canAttack() {
        return false;
    }

    @Override
    public boolean canCastSpells() {
        return false;
    }

    @Override
    public boolean canAct() {
        return false;
    }

	@Override
	public void applyEffect(Charecter character) {
		// TODO Auto-generated method stub
		
	}
}
