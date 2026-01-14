package Guild.CelestialArcanOrder.Weapon;

import Weapon.WeaponManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;

public class VigilantCenserFlail extends WeaponManager {

    private static final int REQUIRED_WISDOM = 13;
    private static final int DAMAGE = 7;
    private static final int WEIGHT = 3;
    private static final Guild GUILDname = Guild.CELESTIAL_ARCANE_ORDER;
    private static final GuildType GUILDtype = GuildType.CLERIC;

    public VigilantCenserFlail(String effect) {
        super("Vigilant Censer‐Flail", REQUIRED_WISDOM, DAMAGE, effect, DAMAGE);
    }

    public boolean equip(Charecter wearer) {
        if (wearer != null && wearer.getCurrentGuild() == GUILDtype) {
            wearer.setWeapon(getName());
            return true;
        }
        return false;
    }

    public void unequip(Charecter wearer) {
        // Unequip logic here
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
        return "Vigilant Censer‐Flail: A sacred flail with a burning censer, wielded by vigilant clerics of the Celestial Arcane Order. Swings with holy fervor, purifying foes and sanctifying the battlefield.";
    }
}
