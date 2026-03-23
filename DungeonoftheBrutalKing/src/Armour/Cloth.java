// src/Armour/Cloth.java
package Armour;

import DungeonoftheBrutalKing.Charecter;
import Status.StatusType;

public class Cloth extends ArmourManager {

	private static Charecter myChar = Charecter.getInstance();
    private static int armourDefense;
    private static final int REQUIRED_STRENGTH = 1;

    public Cloth(int requiredStrength, int armourDefense, String effect) {
        super("Cloth", requiredStrength, armourDefense, 0, effect);
        Cloth.armourDefense = armourDefense;
    }

    public Cloth(String effect) {
        super("Cloth", REQUIRED_STRENGTH, 1, 0, effect);
        Cloth.armourDefense = 1;
    }

    public static Cloth createCloth(Charecter character, int requiredStrength, int armourDefense, String effect) throws NumberFormatException {
        try {
            int strength = myChar.getStrength();
            if (strength >= requiredStrength) {
                return new Cloth(requiredStrength, armourDefense, effect);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Character does not have the required strength to wear the Cloth.");
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

    public StatusType getEffect() {
        return super.getStatusEffect();
    }

    @Override
    public void setEffect(String effect) {
        super.setEffect(effect);
    }
}