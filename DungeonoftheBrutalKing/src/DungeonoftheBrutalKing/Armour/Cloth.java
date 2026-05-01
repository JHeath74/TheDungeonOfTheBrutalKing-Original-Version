package DungeonoftheBrutalKing.Armour;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Status.StatusType;

public class Cloth extends ArmourManager {

    private static final int DEFAULT_ARMOUR_DEFENSE = 1;
    private static final int REQUIRED_STRENGTH = 1;

    public Cloth(int requiredStrength, int armourDefense, String effect) {
        super("Cloth", requiredStrength, armourDefense, 0, effect);
    }

    public Cloth(String effect) {
        super("Cloth", REQUIRED_STRENGTH, DEFAULT_ARMOUR_DEFENSE, 0, effect);
    }

    public static Cloth createCloth(Character character, int requiredStrength, int armourDefense, String effect) {
        if (character.getStrength() >= requiredStrength) {
            return new Cloth(requiredStrength, armourDefense, effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wear the Cloth.");
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
