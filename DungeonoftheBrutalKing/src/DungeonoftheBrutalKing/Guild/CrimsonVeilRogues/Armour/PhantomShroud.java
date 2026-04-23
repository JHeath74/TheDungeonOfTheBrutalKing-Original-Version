
package DungeonoftheBrutalKing.Guild.CrimsonVeilRogues.Armour;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Armour.ArmourManager;

public class PhantomShroud extends ArmourManager {
    private static final int REQUIRED_AGILITY = 12;
    private static final int AGILITY_BONUS = 1;
    private static final int DEFENSE_BONUS = 1;
    private static final int WEIGHT = 2;
    private static final String ARMOUR_NAME = "Phantom Shroud";
    private static final String DESCRIPTION = "Phantom Shroud: A blood-red hood that marks a rogue of the Veil, granting subtle protection and intimidation.";

    public PhantomShroud(String effect) {
        super(ARMOUR_NAME, REQUIRED_AGILITY, DEFENSE_BONUS, WEIGHT, effect);
    }

    public static PhantomShroud createCrimsonHood(Character character, String effect) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        int agility = character.getAgility();
        if (agility >= REQUIRED_AGILITY) {
            return new PhantomShroud(effect);
        }
        throw new IllegalArgumentException("Character does not have the required agility to wear the Crimson Hood.");
    }

    public boolean equip(Character wielder) {
        if (wielder == null) return false;
        if (wielder.getEquippedArmour() == null || !wielder.getEquippedArmour().equals(getName())) {
            wielder.setEquippedArmour(getName());
            wielder.setAgility(wielder.getAgility() + AGILITY_BONUS);
            wielder.setDefense(wielder.getDefense() + DEFENSE_BONUS);
            return true;
        }
        return false;
    }

    public boolean unequip(Character wielder) {
        if (wielder == null) return false;
        if (wielder.getEquippedArmour() != null && wielder.getEquippedArmour().equals(getName())) {
            wielder.setEquippedArmour(null);
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
