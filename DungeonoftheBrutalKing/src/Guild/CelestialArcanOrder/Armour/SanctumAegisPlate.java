
package Guild.CelestialArcanOrder.Armour;

import Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;

public class SanctumAegisPlate extends ArmourManager {

    private static final int REQUIRED_WISDOM = 10;
    private static final int ARMOUR_DEFENSE = 3;
    private static final int WEIGHT = 2;
    private static final Guild GUILDname = Guild.CELESTIAL_ARCANE_ORDER;
    private static final GuildType GUILDtype = GuildType.CLERIC;

    private boolean isEquipped = false;

    public SanctumAegisPlate(String effect) {
        super("Sanctum Aegis Plate", REQUIRED_WISDOM, ARMOUR_DEFENSE, effect);
    }

    @Override
    public void equip(Charecter wearer) {
        if (wearer != null && !isEquipped) {
            wearer.setArmour(getName());
            wearer.setWisdom(wearer.getWisdom() + 2); // Stat bonus
            wearer.setEffectProtection("curse", true); // Protection
            isEquipped = true;
        }
    }

    @Override
    public void unequip(Charecter wearer) {
        if (wearer != null && isEquipped) {
            wearer.setArmour(null);
            wearer.setWisdom(wearer.getWisdom() - 2); // Remove stat bonus
            wearer.setEffectProtection("curse", false); // Remove protection
            isEquipped = false;
        }
    }

    public Guild getGuild() {
        return GUILDname;
    }

    public GuildType getGuildType() {
        return GUILDtype;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public double getWeight() {
        return (double) WEIGHT;
    }

    @Override
    public String getDescription() {
        return "Celestial Chain: Blessed chainmail infused with radiant energy, designed for clerics. Grants protection while allowing divine magic to flow unhindered.";
    }
}
