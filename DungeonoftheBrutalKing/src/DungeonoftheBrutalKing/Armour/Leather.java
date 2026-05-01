
package DungeonoftheBrutalKing.Armour;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Status.StatusType;

public class Leather extends ArmourManager {

    private static final int DEFAULT_ARMOUR_DEFENSE = 8;
    private static final int REQUIRED_STRENGTH = 8;

    public Leather(int requiredStrength, int armourDefense, String effect) {
        super("Leather", requiredStrength, armourDefense, 0, effect);
    }

    public Leather(String effect) {
        super("Leather", REQUIRED_STRENGTH, DEFAULT_ARMOUR_DEFENSE, 0, effect);
    }

    public static Leather createLeather(Character character, int requiredStrength, int armourDefense, String effect) {
        if (character.getStrength() >= requiredStrength) {
            return new Leather(requiredStrength, armourDefense, effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wear the Leather.");
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
