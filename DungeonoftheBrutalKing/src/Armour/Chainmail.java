
// src/Armour/Chainmail.java
package Armour;

import DungeonoftheBrutalKing.Character;

public class Chainmail extends ArmourManager {

    public Chainmail(String name, int requiredStrength, int armourDefense, String effect) {
        super(name, requiredStrength, armourDefense, effect);
        // TODO Auto-generated constructor stub
    }

    private static Character myChar = Character.getInstance();
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
            int strength = myChar.getStrength();
            if (strength >= requiredStrength) {
                return new BreastPlate(requiredStrength, armourDefense, effect);
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
