package DungeonoftheBrutalKing.Armour;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Status.StatusType;

public class StuddedLeather extends ArmourManager {

    private static final int DEFAULT_ARMOUR_DEFENSE = 15;
    private static final int REQUIRED_STRENGTH = 15;

    public StuddedLeather(int requiredStrength, int armourDefense, String effect) {
        super("StuddedLeather", requiredStrength, armourDefense, 0, effect);
    }

    public StuddedLeather(String effect) {
        super("StuddedLeather", REQUIRED_STRENGTH, DEFAULT_ARMOUR_DEFENSE, 0, effect);
    }

    public static StuddedLeather createStuddedLeather(Character character, int requiredStrength, int armourDefense, String effect) {
        if (character.getStrength() >= requiredStrength) {
            return new StuddedLeather(requiredStrength, armourDefense, effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wear Studded Leather.");
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
