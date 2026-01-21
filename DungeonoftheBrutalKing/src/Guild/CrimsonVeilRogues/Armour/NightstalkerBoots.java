
// src/Guild/CrimsonVeilRogues/Armour/NightstalkerBoots.java
package Guild.CrimsonVeilRogues.Armour;

import DungeonoftheBrutalKing.Charecter;
import Armour.ArmourManager;

public class NightstalkerBoots extends ArmourManager {
    private static final int REQUIRED_AGILITY = 15;
    private static final int AGILITY_BONUS = 2;
    private static final int DEFENSE_BONUS = 1; // Changed to int
    private boolean isEquipped = false;

    public NightstalkerBoots(int requiredAgility, String effect) {
        super("Nightstalker Boots", requiredAgility, DEFENSE_BONUS, effect);
    }

    public boolean equip(Charecter wielder) {
        if (!isEquipped && wielder.getAgility() >= REQUIRED_AGILITY) {
            wielder.setAgility(wielder.getAgility() + AGILITY_BONUS);
            wielder.setDefense(wielder.getDefense() + DEFENSE_BONUS);
            isEquipped = true;
            return true;
        }
        return false;
    }

    public void unequip(Charecter wielder) {
        if (isEquipped) {
            wielder.setAgility(wielder.getAgility() - AGILITY_BONUS);
            wielder.setDefense(wielder.getDefense() - DEFENSE_BONUS);
            isEquipped = false;
        }
    }

    @Override
    public String getDescription() {
        return "Nightstalker Boots: Silent boots that let rogues move unseen, boosting agility and evasion.";
    }
}
