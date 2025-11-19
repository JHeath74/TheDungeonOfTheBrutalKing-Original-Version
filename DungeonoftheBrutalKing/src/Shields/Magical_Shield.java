
package Shields;

import DungeonoftheBrutalKing.Character;

public class Magical_Shield extends ShieldManager {

    private int requiredStrength;
   	private int defenseProvided;

   	private static Character myChar = Character.getInstance();

    public Magical_Shield(int requiredStrength, int defenseProvided ) {
        super("Magical Shield", requiredStrength, defenseProvided); // Initialize with name, required strength, and defense provided
        this.requiredStrength = 20;
        this.defenseProvided = 15;

        allShields.add(this);
    }

    public static Magical_Shield createMagical_Shield(Character character, int REQUIRED_STRENGTH, int armourDefense, String effect) throws NumberFormatException {
   	 int requiredStrength = REQUIRED_STRENGTH;
        try {

        	int strength = Integer.parseInt(myChar.getCharInfo().get(8));

            if (strength >= requiredStrength) {
                return new Magical_Shield(armourDefense, requiredStrength);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Battle Axe.");
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
