// src/Armour/Chainmail.java
package DungeonoftheBrutalKing.Armour;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Status.StatusType;

public class Chainmail extends ArmourManager {

    private static Charecter myChar = Charecter.getInstance();
    private static int armourDefense;
    private static final int REQUIRED_STRENGTH = 15;

    public Chainmail(int requiredStrength, int armourDefense, String effect) {
        super("Chainmail", requiredStrength, armourDefense, 0, effect);
        Chainmail.armourDefense = armourDefense;
    }

    public Chainmail(String effect) {
        super("Chainmail", REQUIRED_STRENGTH, 15, 0, effect);
        Chainmail.armourDefense = 15;
    }

    public static Chainmail createChainmail(Charecter character, int requiredStrength, int armourDefense, String effect) throws NumberFormatException {
        try {
            int strength = myChar.getStrength();
            if (strength >= requiredStrength) {
                return new Chainmail(requiredStrength, armourDefense, effect);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Character does not have the required strength to wear Chainmail.");
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

    public StatusType getEffect() {
        return super.getStatusEffect();
    }

    @Override
    public void setEffect(String effect) {
        super.setEffect(effect);
    }
}