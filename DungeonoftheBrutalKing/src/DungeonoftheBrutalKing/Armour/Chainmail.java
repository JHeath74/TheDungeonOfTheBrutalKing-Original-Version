
package DungeonoftheBrutalKing.Armour;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Status.StatusType;

public class Chainmail extends ArmourManager {

    private static final int DEFAULT_ARMOUR_DEFENSE = 15;
    private static final int REQUIRED_STRENGTH = 15;

    public Chainmail(int requiredStrength, int armourDefense, String effect) {
        super("Chainmail", requiredStrength, armourDefense, 0, effect);
    }

    public Chainmail(String effect) {
        super("Chainmail", REQUIRED_STRENGTH, DEFAULT_ARMOUR_DEFENSE, 0, effect);
    }

    public static Chainmail createChainmail(Character character, int requiredStrength, int armourDefense, String effect) {
        if (character.getStrength() >= requiredStrength) {
            return new Chainmail(requiredStrength, armourDefense, effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wear Chainmail.");
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public int getRequiredStrength() {
        return requiredStrength;
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
