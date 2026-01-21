
// src/Guild/CrimsonVeilRogues/Armor/VeilboundLeather.java
package Guild.CrimsonVeilRogues.Armor;

import DungeonoftheBrutalKing.Charecter;
import Armor.ArmorManager;

public class VeilboundLeather extends ArmorManager {
    private static final int REQUIRED_AGILITY = 13;
    private static final int AGILITY_BONUS = 2;
    private static final double DEFENSE_BONUS = 0.07;
    private boolean isEquipped = false;

    public VeilboundLeather(int requiredAgility, String effect) {
        super("Veilbound Leather", requiredAgility, effect);
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
        return "Veilbound Leather: Supple armor favored by rogues, offering protection without sacrificing mobility.";
    }
}
