
package Shields;

import DungeonoftheBrutalKing.ShieldManager;

public class Small_Shield extends ShieldManager {

    private int requiredStrength;
   	private int defenseProvided;

    public Small_Shield(int requiredStrength, int defenseProvided ) {
        super("Small Shield", requiredStrength, defenseProvided); // Initialize with name, required strength, and defense provided
        this.requiredStrength = 20;
        this.defenseProvided = 15;

        allShields.add(this);
    }

    public int getRequiredStrength() {
		return requiredStrength;
	}

	@Override
	public void setRequiredStrength(int requiredStrength) {
		this.requiredStrength = requiredStrength;
	}

	public int getDefenseProvided() {
		return defenseProvided;
	}

	public void setDefenseProvided(int defenseProvided) {
		this.defenseProvided = defenseProvided;
	}


}
