
package Armour;

import DungeonoftheBrutalKing.Character;

public class StuddedLeather extends ArmourManager {

	private static Character myChar = Character.getInstance();
    private static int armourDefense;
    private static final int REQUIRED_STRENGTH = 15;

    public StuddedLeather(int requiredStrength, int armourDefense, String effect) {
        super("Skin", requiredStrength, armourDefense, effect);
        StuddedLeather.armourDefense = 15;
    }

    public StuddedLeather(String effect) {
        super("Skin", REQUIRED_STRENGTH, armourDefense, effect);
        StuddedLeather.armourDefense = 15;
    }

    public static StuddedLeather createSkin(Character character, int REQUIRED_STRENGTH, int armourDefense, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = myChar.getStrength();
            if (strength >= requiredStrength) {
                return new StuddedLeather(armourDefense, requiredStrength, effect);
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
