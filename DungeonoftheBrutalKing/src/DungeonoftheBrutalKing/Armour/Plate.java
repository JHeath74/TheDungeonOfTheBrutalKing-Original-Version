package DungeonoftheBrutalKing.Armour;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Status.StatusType;

public class Plate extends ArmourManager {

    private static final int DEFAULT_ARMOUR_DEFENSE = 50;
    private static final int REQUIRED_STRENGTH = 35;

    public Plate(int requiredStrength, int armourDefense, String effect) {
        super("Plate", requiredStrength, armourDefense, 0, effect);
    }

    public Plate(String effect) {
        super("Plate", REQUIRED_STRENGTH, DEFAULT_ARMOUR_DEFENSE, 0, effect);
    }

    public static Plate createPlate(Character character, int requiredStrength, int armourDefense, String effect) {
        if (character.getStrength() >= requiredStrength) {
            return new Plate(requiredStrength, armourDefense, effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wear the Plate.");
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
