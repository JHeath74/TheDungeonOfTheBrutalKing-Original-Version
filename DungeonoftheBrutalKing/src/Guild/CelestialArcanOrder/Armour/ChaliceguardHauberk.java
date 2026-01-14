
// File: src/Guild/CelestialArcanOrder/Armour/ChaliceguardHauberk.java

package Guild.CelestialArcanOrder.Armour;

import Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;

public class ChaliceguardHauberk extends ArmourManager {

    private static final int REQUIRED_WISDOM = 14;
    private static final int ARMOUR_DEFENSE = 6;
    private static final int WEIGHT = 3;
    private static final Guild GUILDname = Guild.CELESTIAL_ARCANE_ORDER;
    private static final GuildType GUILDtype = GuildType.CLERIC;

    private boolean isEquipped = false;

    public ChaliceguardHauberk(String effect) {
        super("Chaliceguard Hauberk", REQUIRED_WISDOM, ARMOUR_DEFENSE, effect);
    }

    public void equip(Charecter wearer) {
        if (wearer != null && !isEquipped) {
            wearer.setArmour(getName());
            wearer.setWisdom(wearer.getWisdom() + 2); // Stat bonus
            wearer.setEffectProtection("radiant", true); // Protection against "radiant"
            isEquipped = true;
        }
    }

    public void unequip(Charecter wearer) {
        if (wearer != null && isEquipped) {
            wearer.setArmour(null);
            wearer.setWisdom(wearer.getWisdom() - 2); // Remove stat bonus
            wearer.setEffectProtection("radiant", false); // Remove protection
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
        return "Chaliceguard Hauberk: Sacred hauberk adorned with the sigil of the chalice, worn by the Celestial Arcane Order's clerics. Offers potent divine protection and amplifies holy rites.";
    }
}
