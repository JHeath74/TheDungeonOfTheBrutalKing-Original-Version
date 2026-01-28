
// src/Guild/CrimsonVeilRogues/Armour/NightstalkerBoots.java
package Guild.CrimsonVeilRogues.Armour;

import DungeonoftheBrutalKing.Charecter;
import Armour.ArmourManager;

public class NightstalkerBoots extends ArmourManager {
    private static final int REQUIRED_AGILITY = 15;
    private static final int AGILITY_BONUS = 2;
    private static final int DEFENSE_BONUS = 1;
    private static final String ARMOUR_NAME = "Nightstalker Boots";
    private static final String DESCRIPTION = "Nightstalker Boots: Silent boots that let rogues move unseen, boosting agility and evasion.";

    public NightstalkerBoots(String effect) {
        super(ARMOUR_NAME, REQUIRED_AGILITY, DEFENSE_BONUS, effect);
    }

    public static NightstalkerBoots createNightstalkerBoots(Charecter character, String effect) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        int agility = character.getAgility();
        if (agility >= REQUIRED_AGILITY) {
            return new NightstalkerBoots(effect);
        }
        throw new IllegalArgumentException("Character does not have the required agility to wear the Nightstalker Boots.");
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
