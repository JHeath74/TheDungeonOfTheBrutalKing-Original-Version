package DungeonoftheBrutalKing.Guild.CelestialArcaneOrder.Weapon;

import DungeonoftheBrutalKing.Weapon.WeaponManager;
import DungeonoftheBrutalKing.Status.HasHitPoints;
import DungeonoftheBrutalKing.Status.RadiantStatus;
import DungeonoftheBrutalKing.Status.Status;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.SharedData.GuildType;

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
    public boolean equip(Character wearer) {
        if (wearer != null && wearer.getGuild() == GUILDname) {
            wearer.setEquippedWeapon(getName());
            wearer.setWisdom(wearer.getWisdom() + 2);
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Character wearer) {
        if (wearer != null
                && wearer.getEquippedWeapon() != null
                && wearer.getEquippedWeapon().equals(getName())) {

            wearer.setEquippedWeapon(null);
            int newWisdom = wearer.getWisdom() - 2;
            wearer.setWisdom(Math.max(newWisdom, 0));
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
            if (target instanceof Character) {
                ((Character) target).addStatus(radiant);
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
