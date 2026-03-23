package Armour;

import DungeonoftheBrutalKing.Charecter;
import Status.StatusType;

public class Skin extends ArmourManager {

    private static Charecter myChar = Charecter.getInstance();
    private static int armourDefense;
    private static final int REQUIRED_STRENGTH = 12;

    public Skin(int requiredStrength, int armourDefense, String effect) {
        super("Skin", requiredStrength, armourDefense, 0, effect);
        Skin.armourDefense = armourDefense;
    }

    public Skin(String effect) {
        super("Skin", REQUIRED_STRENGTH, 12, 0, effect);
        Skin.armourDefense = 12;
    }

    public static Skin createSkin(Charecter character, int REQUIRED_STRENGTH, int armourDefense, String effect) throws NumberFormatException {
        try {
            int strength = myChar.getStrength();
            if (strength >= REQUIRED_STRENGTH) {
                return new Skin(REQUIRED_STRENGTH, armourDefense, effect);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Character does not have the required strength to wear the Skin.");
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