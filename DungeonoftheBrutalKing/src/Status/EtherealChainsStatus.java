
package Status;

import DungeonoftheBrutalKing.Charecter;

public class EtherealChainsStatus extends Status {
    public EtherealChainsStatus(int duration) {
        super("Ethereal Chains", duration);
    }

    @Override
    public boolean blocksAttack() {
        return true;
    }

	@Override
	public void applyEffect(Charecter character) {
		// TODO Auto-generated method stub
		
	}
}
