package DungeonoftheBrutalKing.Guild.CelestialArcaneOrder.Weapon;

import DungeonoftheBrutalKing.Weapon.WeaponManager;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Status.HasHitPoints;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.Status.Status;
import DungeonoftheBrutalKing.Status.RadiantStatus;

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
    public boolean equip(Character wearer) {
        if (wearer != null && wearer.getGuild() == GUILDname) {
            wearer.setEquippedWeapon(getName());
            wearer.setWisdom(wearer.getWisdom() + BONUS_WISDOM);
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
            wearer.setWisdom(wearer.getWisdom() - BONUS_WISDOM);
            return true;
        }
        return false;
    }

    @Override
    public void applyCombatEffect(HasHitPoints target) {
        if (target != null) {
            int extraRadiantDamage = 2; // smaller than Warhammer, adjust as desired
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
        return "Sunwarden Mace: A radiant mace wielded by clerics of the Celestial Arcane Order. Channels sunlight into divine strikes, banishing darkness and empowering holy magic.";
    }
}
