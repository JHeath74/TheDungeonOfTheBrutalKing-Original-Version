
// src/Armour/Cloth.java
package Armour;

import DungeonoftheBrutalKing.Charecter;

public class Cloth extends ArmourManager {

    private static Charecter myChar = Charecter.Singleton();
    private static int armourDefense;
    private static final int REQUIRED_STRENGTH = 15;

    public Cloth(String effect) {
        super("Cloth", REQUIRED_STRENGTH, armourDefense, effect);
        Cloth.armourDefense = 15;
    }

    public Cloth(int requiredStrength, int armourDefense, String effect) {
        super("Cloth", requiredStrength, armourDefense, effect);
        Cloth.armourDefense = 15;
    }

    public static BreastPlate createBreastPlate(Charecter character, int REQUIRED_STRENGTH, int armourDefense, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = myChar.getStrength();
            if (strength >= requiredStrength) {
                return new BreastPlate(armourDefense, requiredStrength, effect);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Battle Axe.");
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public int getRequiredStrength() {
        return myChar.getStrength();
    }

    @Override
    public void setRequiredStrength(int requiredStrength) {
        super.setRequiredStrength(requiredStrength);
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
