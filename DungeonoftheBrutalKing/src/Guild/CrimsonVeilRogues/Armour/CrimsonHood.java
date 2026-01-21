
// src/Guild/CrimsonVeilRogues/Armour/CrimsonHood.java
package Guild.CrimsonVeilRogues.Armour;

import DungeonoftheBrutalKing.Charecter;
import Armour.ArmourManager;

public class CrimsonHood extends ArmourManager {
    private static final int REQUIRED_AGILITY = 12;
    private static final int AGILITY_BONUS = 1;
    private static final int DEFENSE_BONUS = 1;
    private boolean isEquipped = false;

    public CrimsonHood(int requiredAgility, String effect) {
        super("Crimson Hood", requiredAgility, DEFENSE_BONUS, effect);
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
        return "Crimson Hood: A blood-red hood that marks a rogue of the Veil, granting subtle protection and intimidation.";
    }
}
