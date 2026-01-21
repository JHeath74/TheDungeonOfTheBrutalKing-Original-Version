
// src/Guild/CrimsonVeilRogues/Armour/PoisonthreadGloves.java
package Guild.CrimsonVeilRogues.Armour;

import DungeonoftheBrutalKing.Charecter;
import Armour.ArmourManager;

public class PoisonthreadGloves extends ArmourManager {
    private static final int REQUIRED_AGILITY = 13;
    private static final int AGILITY_BONUS = 1;
    private static final int DEFENSE_BONUS = 1; // Changed to int
    private boolean isEquipped = false;

    public PoisonthreadGloves(int requiredAgility, String effect) {
        super("Poisonthread Gloves", requiredAgility, effect, DEFENSE_BONUS);
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
    public void unequip(Charecter wielder) {
        if (isEquipped) {
            wielder.setAgility(wielder.getAgility() - AGILITY_BONUS);
            wielder.setDefense(wielder.getDefense() - DEFENSE_BONUS);
            isEquipped = false;
        }
    }

    @Override
    public String getDescription() {
        return "Poisonthread Gloves: Gloves laced with toxins, perfect for a rogue's deadly touch and nimble fingers.";
    }
}
