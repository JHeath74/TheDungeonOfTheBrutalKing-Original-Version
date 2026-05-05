
package DungeonoftheBrutalKing.Armour;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Status.StatusType;

public class StuddedLeather extends ArmourManager {

    private static final int REQUIRED_STRENGTH = 15;
    private static final int DEFAULT_ARMOUR_DEFENSE = 15;

    public StuddedLeather(Character owner, String effect) {
        super("StuddedLeather", REQUIRED_STRENGTH, DEFAULT_ARMOUR_DEFENSE, 0, effect);
        if (owner.getStrength() < REQUIRED_STRENGTH) {
            throw new IllegalArgumentException("Character does not have the required strength to equip Studded Leather.");
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
