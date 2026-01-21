
// src/Guild/CrimsonVeilRogues/Armour/ShadowweaveCloak.java
package Guild.CrimsonVeilRogues.Armour;

import DungeonoftheBrutalKing.Charecter;
import Armour.ArmourManager;

public class ShadowweaveCloak extends ArmourManager {
    private static final int REQUIRED_AGILITY = 14;
    private static final int AGILITY_BONUS = 3;
    private static final int DEFENSE_BONUS = 1; // Use int for defense bonus
    private boolean isEquipped = false;

    public ShadowweaveCloak(int requiredAgility, String effect) {
        super("Shadowweave Cloak", requiredAgility, DEFENSE_BONUS, effect); // Add defense bonus if needed
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
        return "Shadowweave Cloak: A cloak woven from darkness, enhancing stealth and agility for the perfect ambush.";
    }
}
