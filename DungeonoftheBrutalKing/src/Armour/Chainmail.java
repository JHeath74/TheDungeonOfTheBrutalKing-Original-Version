
// src/Armour/BreastPlate.java
package Armour;

import DungeonoftheBrutalKing.ArmourManager;
import DungeonoftheBrutalKing.Charecter;


public class Chainmail extends ArmourManager {

    public Chainmail(String name, int requiredStrength, int armourDefense, String effect) {
		super(name, requiredStrength, armourDefense, effect);
		// TODO Auto-generated constructor stub
	}

	private static Charecter myChar = Charecter.Singleton();
    private static int armourDefense;
    private static final int REQUIRED_STRENGTH = 15;

    public Chainmail(String effect) {
        super("Chainmail", REQUIRED_STRENGTH, armourDefense, effect);
        Chainmail.armourDefense = 15;
    }

    public Chainmail(int requiredStrength, int armourDefense, String effect) {
        super("Breast Plate", requiredStrength, armourDefense, effect);
        Chainmail.armourDefense = 15;
    }

    public static BreastPlate createBreastPlate(Character character, int REQUIRED_STRENGTH, int armourDefense, String effect) throws NumberFormatException {
    	 int requiredStrength = REQUIRED_STRENGTH;
         try {
             int strength = Integer.parseInt(myChar.CharInfo.get(8));
             if (strength >= requiredStrength) {
                 return new BreastPlate(armourDefense, requiredStrength, effect);
             }
         } catch (NumberFormatException e) {
             e.printStackTrace();
         }
         throw new IllegalArgumentException("Character does not have the required strength to wield the Battle Axe.");
    }

    public String getName() {
        return super.getName();
    }

    public int getRequiredStrength() {
        return Integer.parseInt(myChar.CharInfo.get(8));
    }

    public void setRequiredStrength(int requiredStrength) {
        super.setRequiredStrength(requiredStrength);
    }

    public int getArmourDefense() {
        return super.getArmourDefense();
    }

    public StatusEffect getEffect() {
        return super.getStatusEffect();
    }

    public void setEffect(String effect) {
        super.setEffect(effect);
    }
}
