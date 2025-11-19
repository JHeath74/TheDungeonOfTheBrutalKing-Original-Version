
package Armour;

import DungeonoftheBrutalKing.Character;

public class Plate extends ArmourManager {

	private static Character myChar = Character.getInstance();
    private static int armourDefense;
    private static final int REQUIRED_STRENGTH = 15;

    public Plate(String effect) {
        super("Plate", REQUIRED_STRENGTH, Plate.armourDefense, effect);
        Plate.armourDefense = 15;
    }

    public Plate(int requiredStrength, int armourDefense, String effect) {
        super("Plate", requiredStrength, Plate.armourDefense, effect);
        Plate.armourDefense = 15;
    }

    public static Plate createPlate(Character character, int REQUIRED_STRENGTH, int armourDefense, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = myChar.getStrength();
            if (strength >= requiredStrength) {
                return new Plate(armourDefense, requiredStrength, effect);
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
