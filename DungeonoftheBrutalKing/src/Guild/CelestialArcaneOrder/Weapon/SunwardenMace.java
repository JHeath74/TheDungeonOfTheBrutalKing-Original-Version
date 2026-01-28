
package Guild.CelestialArcaneOrder.Weapon;

import Weapon.WeaponManager;
import SharedData.Guild;
import SharedData.GuildType;
import DungeonoftheBrutalKing.Charecter;
import Status.HasHitPoints;
import Status.Status;

public class SunwardenMace extends WeaponManager {

    private static final int REQUIRED_WISDOM = 12;
    private static final int DAMAGE = 8;
    private static final int WEIGHT = 3;
    private static final int BONUS_WISDOM = 2;
    private static final Guild GUILDname = Guild.CELESTIAL_ARCANE_ORDER;
    private static final GuildType GUILDtype = GuildType.CLERIC;

    public SunwardenMace(String effect) {
        super("Sunwarden Mace", REQUIRED_WISDOM, DAMAGE, effect, WEIGHT);
    }

    @Override
    public boolean equip(Charecter wearer) {
        if (wearer != null && wearer.getCurrentGuild() == GUILDtype) {
            wearer.setWeapon(getName());
            wearer.setWisdom(wearer.getWisdom() + BONUS_WISDOM);
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Charecter wearer) {
        if (wearer != null && wearer.getWeapon() != null && wearer.getWeapon().equals(getName())) {
            wearer.setWeapon(null);
            wearer.setWisdom(wearer.getWisdom() - BONUS_WISDOM);
        }
		return false;
    }

    public void applyEffect(HasHitPoints target) {
        // Example: apply the weapon's effect as a Status
        if (target != null && getStatusEffect() != null) {
            Status effectStatus = Status.createFromEffect(getStatusEffect());
            target.addStatus(effectStatus);
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
        return "Sunwarden Mace: A radiant mace wielded by clerics of the Celestial Arcane Order. Channels sunlight into divine strikes, banishing darkness and empowering holy magic.";
    }
}
