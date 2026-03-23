package Guild.CrimsonVeilRogues.Armour;

import DungeonoftheBrutalKing.Charecter;
import Armour.ArmourManager;

public class VeilstriderVestments extends ArmourManager {

    private static final int DEFENSE = 8;
    private static final int WEIGHT = 4;
    private static final String NAME = "Veilstrider Vestments";

    public VeilstriderVestments() {
        super(NAME, DEFENSE, WEIGHT);
    }

    @Override
    public boolean equip(Charecter wearer) {
        if (wearer == null) return false;
        wearer.setArmour(getName());
        wearer.setDexterity(wearer.getDexterity() + 2);
        return true;
    }

    @Override
    public boolean unequip(Charecter wearer) {
        if (wearer == null || wearer.getArmour() == null || !wearer.getArmour().equals(getName())) return false;
        wearer.setArmour(null);
        wearer.setDexterity(Math.max(0, wearer.getDexterity() - 2));
        return true;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getDescription() {
        return "Veilstrider Vestments: Lightweight armour favored by the Crimson Veil Rogues providing stealth and agility.";
    }
}