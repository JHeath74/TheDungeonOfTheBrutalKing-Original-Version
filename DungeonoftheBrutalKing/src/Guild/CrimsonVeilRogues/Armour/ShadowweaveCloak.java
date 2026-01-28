
// src/Guild/CrimsonVeilRogues/Armour/ShadowweaveCloak.java
package Guild.CrimsonVeilRogues.Armour;

import DungeonoftheBrutalKing.Charecter;
import Armour.ArmourManager;

public class ShadowweaveCloak extends ArmourManager {
    private static final int REQUIRED_AGILITY = 14;
    private static final int AGILITY_BONUS = 3;
    private static final int DEFENSE_BONUS = 1;
    private static final String ARMOUR_NAME = "Shadowweave Cloak";
    private static final String DESCRIPTION = "Shadowweave Cloak: A cloak woven from darkness, enhancing stealth and agility for the perfect ambush.";

    public ShadowweaveCloak(String effect) {
        super(ARMOUR_NAME, REQUIRED_AGILITY, DEFENSE_BONUS, effect);
    }

    public static ShadowweaveCloak createShadowweaveCloak(Charecter character, String effect) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        int agility = character.getAgility();
        if (agility >= REQUIRED_AGILITY) {
            return new ShadowweaveCloak(effect);
        }
        throw new IllegalArgumentException("Character does not have the required agility to wear the Shadowweave Cloak.");
    }

    public boolean equip(Charecter wielder) {
        if (wielder == null) return false;
        if (wielder.getArmour() == null || !wielder.getArmour().equals(getName())) {
            wielder.setArmour(getName());
            wielder.setAgility(wielder.getAgility() + AGILITY_BONUS);
            wielder.setDefense(wielder.getDefense() + DEFENSE_BONUS);
            return true;
        }
        return false;
    }

    public boolean unequip(Charecter wielder) {
        if (wielder == null) return false;
        if (wielder.getArmour() != null && wielder.getArmour().equals(getName())) {
            wielder.setArmour(null);
            wielder.setAgility(wielder.getAgility() - AGILITY_BONUS);
            wielder.setDefense(wielder.getDefense() - DEFENSE_BONUS);
            return true;
        }
        return false;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
