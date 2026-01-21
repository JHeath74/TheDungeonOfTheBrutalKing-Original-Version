
// src/Guild/CelestialArcanOrder/Armour/VestureoftheDawnbound.java

package Guild.CelestialArcanOrder.Armour;

import Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;

public class VestureoftheDawnbound extends ArmourManager {

    private static final int REQUIRED_WISDOM = 12;
    private static final int ARMOUR_DEFENSE = 4;
    private static final int WEIGHT = 1;
    private static final Guild GUILDname = Guild.CELESTIAL_ARCANE_ORDER;
    private static final GuildType GUILDtype = GuildType.CLERIC;

    private boolean isEquipped = false;

    public VestureoftheDawnbound(String effect) {
        super("Vesture of the Dawnbound", REQUIRED_WISDOM, ARMOUR_DEFENSE, effect);
    }

    @Override
    public void equip(Charecter wearer) {
        if (wearer != null && !isEquipped) {
            wearer.setArmour(getName());
            wearer.setWisdom(wearer.getWisdom() + 2); // Stat bonus
            wearer.setEffectProtection("fire", true); // Protection against "fire"
            isEquipped = true;
        }
    }

    @Override
    public void unequip(Charecter wearer) {
        if (wearer != null && isEquipped) {
            wearer.setArmour(null);
            wearer.setWisdom(wearer.getWisdom() - 2); // Remove stat bonus
            wearer.setEffectProtection("fire", false); // Remove protection
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
        return "Vesture of the Dawnbound: Sacred vestment woven with celestial threads, favored by clerics of the Celestial Arcane Order. Enhances divine protection and spellcasting.";
    }
}
