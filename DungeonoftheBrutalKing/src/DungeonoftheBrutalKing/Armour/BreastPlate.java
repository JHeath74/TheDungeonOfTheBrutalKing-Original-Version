package DungeonoftheBrutalKing.Armour;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Status.StatusType;

public class BreastPlate extends ArmourManager {

    public BreastPlate(int requiredStrength, int armourDefense, String effect) {
        // ArmourManager expects (name, requiredStrength, armourDefense, weight, effect)
        super("Breast Plate", requiredStrength, armourDefense, 0, effect);
        // allArmour is now protected in ArmourManager, so this is allowed
        allArmour.add(this);
    }

    public static BreastPlate createBreastPlate(Character character, int requiredStrength, int armourDefense, String effect) {
        int strength = character.getStrength();
        if (strength >= requiredStrength) {
            return new BreastPlate(requiredStrength, armourDefense, effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to equip the Breast Plate.");
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public int getRequiredStrength() {
        return requiredStrength;
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
