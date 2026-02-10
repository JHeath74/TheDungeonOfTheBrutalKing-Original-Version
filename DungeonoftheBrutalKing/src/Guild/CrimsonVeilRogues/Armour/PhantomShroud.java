
// src/Guild/CrimsonVeilRogues/Armour/CrimsonHood.java
package Guild.CrimsonVeilRogues.Armour;

import DungeonoftheBrutalKing.Charecter;
import Armour.ArmourManager;

public class PhantomShroud extends ArmourManager {
    private static final int REQUIRED_AGILITY = 12;
    private static final int AGILITY_BONUS = 1;
    private static final int DEFENSE_BONUS = 1;
    private static final String ARMOUR_NAME = "Phantom Shroud";
    private static final String DESCRIPTION = "Phantom Shroud: A blood-red hood that marks a rogue of the Veil, granting subtle protection and intimidation.";

    public PhantomShroud(String effect) {
        super(ARMOUR_NAME, REQUIRED_AGILITY, DEFENSE_BONUS, effect);
    }

    public static PhantomShroud createCrimsonHood(Charecter character, String effect) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        int agility = character.getAgility();
        if (agility >= REQUIRED_AGILITY) {
            return new PhantomShroud(effect);
        }
        throw new IllegalArgumentException("Character does not have the required agility to wear the Crimson Hood.");
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
