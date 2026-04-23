package DungeonoftheBrutalKing.Guild.CrimsonVeilRogues.Armour;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Armour.ArmourManager;

public class VeilstriderVestments extends ArmourManager {

    private static final int REQUIRED_STRENGTH = 10;
    private static final int DEFENSE = 8;
    private static final int WEIGHT = 4;
    private static final String NAME = "Veilstrider Vestments";
    private static final String EFFECT = "none";

    public VeilstriderVestments() {
        super(NAME, REQUIRED_STRENGTH, DEFENSE, WEIGHT, EFFECT);
    }

    @Override
    public boolean equip(Character wearer) {
        if (wearer == null) return false;
        if (wearer.getEquippedArmour() == null || !wearer.getEquippedArmour().equals(getName())) {
            wearer.setEquippedArmour(getName());
            wearer.setAgility(wearer.getAgility() + 2);
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Character wearer) {
        if (wearer == null) return false;
        if (wearer.getEquippedArmour() != null && wearer.getEquippedArmour().equals(getName())) {
            wearer.setEquippedArmour(null);
            wearer.setAgility(Math.max(0, wearer.getAgility() - 2));
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public double getWeight() {
        return WEIGHT;
    }

    @Override
    public String getDescription() {
        return "Veilstrider Vestments: Lightweight armour favored by the Crimson Veil Rogues providing stealth and agility.";
    }
}
