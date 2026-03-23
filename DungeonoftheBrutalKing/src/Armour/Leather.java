// src/Armour/Leather.java
package Armour;

import DungeonoftheBrutalKing.Charecter;
import Status.StatusType;

public class Leather extends ArmourManager {

    private static Charecter myChar = Charecter.getInstance();
    private static int armourDefense;
    private static final int REQUIRED_STRENGTH = 8;

    public Leather(int requiredStrength, int armourDefense, String effect) {
        super("Leather", requiredStrength, armourDefense, 0, effect);
        Leather.armourDefense = armourDefense;
    }

    public Leather(String effect) {
        super("Leather", REQUIRED_STRENGTH, 8, 0, effect);
        Leather.armourDefense = 8;
    }

    public static Leather createLeather(Charecter character, int REQUIRED_STRENGTH, int armourDefense, String effect) throws NumberFormatException {
        try {
            int strength = myChar.getStrength();
            if (strength >= REQUIRED_STRENGTH) {
                return new Leather(REQUIRED_STRENGTH, armourDefense, effect);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Character does not have the required strength to wear the Leather.");
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