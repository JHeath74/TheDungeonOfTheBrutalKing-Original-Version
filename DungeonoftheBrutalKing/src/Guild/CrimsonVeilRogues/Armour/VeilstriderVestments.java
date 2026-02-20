
java
package Guild.CrimsonVeilRogues.Armour;

import DungeonoftheBrutalKing.Charecter;
import Armour.ArmourManager;

public class VeilstriderVestments extends ArmourManager {
    private static final int REQUIRED_AGILITY = 13;
    private static final int AGILITY_BONUS = 2;
    private static final int DEFENSE_BONUS = 1;
    private static final int WEIGHT = 2;
    private boolean isEquipped = false;

    public VeilstriderVestments(String effect) {
        super("Veilstrider Vestments", REQUIRED_AGILITY, DEFENSE_BONUS, WEIGHT, effect);
    }

    @Override
    public boolean equip(Charecter wielder) {
        if (!isEquipped && wielder.getAgility() >= REQUIRED_AGILITY) {
            wielder.setAgility(wielder.getAgility() + AGILITY_BONUS);
            wielder.setDefense(wielder.getDefense() + DEFENSE_BONUS);
            isEquipped = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Charecter wielder) {
        if (isEquipped) {
            wielder.setAgility(wielder.getAgility() - AGILITY_BONUS);
            wielder.setDefense(wielder.getDefense() - DEFENSE_BONUS);
            isEquipped = false;
            return true;
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "Veilstrider Vestments: Supple armor favored by rogues, offering protection without sacrificing mobility.";
    }
}
