
package DungeonoftheBrutalKing.Armour;

import DungeonoftheBrutalKing.Status.StatusType;

public class Skin extends ArmourManager {

    private static final int DEFAULT_DEFENSE = 12;
    private static final int REQUIRED_STRENGTH = 12;

    // Default constructor for starting armor
    public Skin() {
        super("Skin", REQUIRED_STRENGTH, DEFAULT_DEFENSE, 0, null);
    }

    // Constructor for custom skin armor
    public Skin(int requiredStrength, int armourDefense, String effect) {
        super("Skin", requiredStrength, armourDefense, 0, effect);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public int getRequiredStrength() {
        return REQUIRED_STRENGTH;
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
