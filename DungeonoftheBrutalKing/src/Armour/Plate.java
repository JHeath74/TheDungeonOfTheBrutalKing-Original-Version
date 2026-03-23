package Armour;

import DungeonoftheBrutalKing.Charecter;
import Status.StatusType;

public class Plate extends ArmourManager {

    private static Charecter myChar = Charecter.getInstance();
    private static int armourDefense;
    private static final int REQUIRED_STRENGTH = 35;

    public Plate(int requiredStrength, int armourDefense, String effect) {
        super("Plate", requiredStrength, armourDefense, 0, effect);
        Plate.armourDefense = armourDefense;
    }

    public Plate(String effect) {
        super("Plate", REQUIRED_STRENGTH, 50, 0, effect);
        Plate.armourDefense = 50;
    }

    public static Plate createPlate(Charecter character, int REQUIRED_STRENGTH, int armourDefense, String effect) throws NumberFormatException {
        try {
            int strength = myChar.getStrength();
            if (strength >= REQUIRED_STRENGTH) {
                return new Plate(REQUIRED_STRENGTH, armourDefense, effect);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Character does not have the required strength to wear the Plate.");
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