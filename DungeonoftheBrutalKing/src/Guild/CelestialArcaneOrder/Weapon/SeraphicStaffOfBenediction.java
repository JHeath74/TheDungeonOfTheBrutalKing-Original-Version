
package Guild.CelestialArcaneOrder.Weapon;

import Weapon.WeaponManager;
import Status.DrainStatus;
import Status.StatusManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;
import Status.HasHitPoints;

public class SeraphicStaffOfBenediction extends WeaponManager {

    private static final int REQUIRED_WISDOM = 15;
    private static final int DAMAGE = 8;
    private static final int WEIGHT = 4;
    private static final Guild GUILDname = Guild.CELESTIAL_ARCANE_ORDER;
    private static final GuildType GUILDtype = GuildType.CLERIC;

    public SeraphicStaffOfBenediction(String effect) {
        super("Seraphic Staff of Benediction", REQUIRED_WISDOM, DAMAGE, effect, DAMAGE);
    }

    @Override
    public boolean equip(Charecter wearer) {
        if (wearer != null
            && wearer.getCurrentGuild() == GUILDtype
            && wearer.getWisdom() >= REQUIRED_WISDOM) {
            wearer.setWeapon(getName());
            return true;
        }
        return false;
    }

    @Override
    public void unequip(Charecter wearer) {
        if (wearer != null && getName().equals(wearer.getWeapon())) {
            wearer.setWeapon(null);
        }
    }

    @Override
    public void applyCombatEffect(HasHitPoints target) {
        if (target instanceof Charecter) {
            Charecter character = (Charecter) target;
            // Apply DrainStatus for 2 turns
            character.addStatus(new DrainStatus(2));
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
        return "Seraphic Staff of Benediction: A radiant staff blessed by celestial powers, wielded by clerics of the Celestial Arcane Order. It channels divine energy to heal allies and smite the wicked.";
    }
}
