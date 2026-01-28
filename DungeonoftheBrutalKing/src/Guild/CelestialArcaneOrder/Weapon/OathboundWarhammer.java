// src/Guild/CelestialArcaneOrder/Weapon/OathboundWarhammer.java

package Guild.CelestialArcaneOrder.Weapon;

import Weapon.WeaponManager;
import Status.HasHitPoints;
import Status.RadiantStatus;
import Status.Status;
import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import SharedData.GuildType;

public class OathboundWarhammer extends WeaponManager {

    private static final int REQUIRED_WISDOM = 14;
    private static final int DAMAGE = 9;
    private static final int WEIGHT = 5;
    private static final Guild GUILDname = Guild.CELESTIAL_ARCANE_ORDER;
    private static final GuildType GUILDtype = GuildType.CLERIC;

    public OathboundWarhammer(String effect) {
        super("Oathbound Warhammer", REQUIRED_WISDOM, DAMAGE, effect, WEIGHT);
    }

    @Override
    public boolean equip(Charecter wearer) {
        if (wearer != null && wearer.getCurrentGuild() == GUILDtype) {
            wearer.setWeapon(getName());
            wearer.setWisdom(wearer.getWisdom() + 2); // Stat bonus
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Charecter wearer) {
        if (wearer != null && wearer.getWeapon() != null && wearer.getWeapon().equals(getName())) {
            wearer.setWeapon(null);
            wearer.setWisdom(wearer.getWisdom() - 2); // Remove stat bonus
            return true;
        }
        return false;
    }

    @Override
    public void applyCombatEffect(HasHitPoints target) {
        if (target != null) {
            int extraRadiantDamage = 4;
            target.setHitPoints(target.getHitPoints() - extraRadiantDamage);

            Status radiant = new RadiantStatus(extraRadiantDamage);
            if (target instanceof Charecter) {
                ((Charecter) target).addStatus(radiant);
            } else if (target instanceof Enemies) {
                ((Enemies) target).addStatus(radiant);
            }
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
        return "Oathbound Warhammer: A sacred warhammer wielded by clerics of the Celestial Arcane Order. It radiates divine power, delivering crushing blows to foes and upholding holy oaths.";
    }
}
