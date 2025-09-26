
// src/Armour/BreastPlate.java
package Armour;

import DungeonoftheBrutalKing.Charecter;

public class BreastPlate extends ArmourManager {

	private static Charecter myChar = Charecter.getInstance();

    public BreastPlate(int requiredStrength, int armourDefense, String effect) {
        super("Breast Plate", requiredStrength, armourDefense, effect);
        allArmour.add(this);
    }

    public static BreastPlate createBreastPlate(Charecter character, int requiredStrength, int armourDefense, String effect) throws NumberFormatException {
        try {
            int strength = myChar.getStrength();
            if (strength >= requiredStrength) {
                return new BreastPlate(requiredStrength, armourDefense, effect);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Breast Plate.");
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public int getRequiredStrength() {
        return myChar.getStrength();
    }

    @Override
    public int getArmourDefense() {
        return super.getArmourDefense();
    }

    public StatusEffect getEffect() {
        return super.getStatusEffect();
    }

    @Override
    public void setEffect(String effect) {
        super.setEffect(effect);
    }
}
