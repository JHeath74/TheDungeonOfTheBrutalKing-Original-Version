
// src/Guild/CrimsonVeilRogues/Armour/PoisonthreadGloves.java
package Guild.CrimsonVeilRogues.Armour;

import DungeonoftheBrutalKing.Charecter;
import Armour.ArmourManager;

public class PoisonthreadGloves extends ArmourManager {
    private static final int REQUIRED_AGILITY = 13;
    private static final int AGILITY_BONUS = 1;
    private static final int DEFENSE_BONUS = 1;
    private static final String ARMOUR_NAME = "Poisonthread Gloves";
    private static final String DESCRIPTION = "Poisonthread Gloves: Gloves laced with toxins, perfect for a rogue's deadly touch and nimble fingers.";

    public PoisonthreadGloves(String effect) {
        super(ARMOUR_NAME, REQUIRED_AGILITY, DEFENSE_BONUS, effect);
    }

    public static PoisonthreadGloves createPoisonthreadGloves(Charecter character, String effect) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        int agility = character.getAgility();
        if (agility >= REQUIRED_AGILITY) {
            return new PoisonthreadGloves(effect);
        }
        throw new IllegalArgumentException("Character does not have the required agility to wear the Poisonthread Gloves.");
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
