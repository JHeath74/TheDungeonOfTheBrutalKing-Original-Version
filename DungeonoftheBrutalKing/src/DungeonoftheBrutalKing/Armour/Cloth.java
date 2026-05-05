
package DungeonoftheBrutalKing.Armour;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Status.StatusType;

public class Cloth extends ArmourManager {

    private static final int REQUIRED_STRENGTH = 1;
    private static final int DEFAULT_ARMOUR_DEFENSE = 1;

    public Cloth(Character owner, String effect) {
        super("Cloth", REQUIRED_STRENGTH, DEFAULT_ARMOUR_DEFENSE, 0, effect);
        if (owner.getStrength() < REQUIRED_STRENGTH) {
            throw new IllegalArgumentException("Character does not have the required strength to equip Cloth.");
        }
        allArmour.add(this);
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
