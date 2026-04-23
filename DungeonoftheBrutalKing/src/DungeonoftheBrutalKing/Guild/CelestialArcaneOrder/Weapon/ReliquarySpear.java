package DungeonoftheBrutalKing.Guild.CelestialArcaneOrder.Weapon;

import DungeonoftheBrutalKing.Weapon.WeaponManager;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.Status.HasHitPoints;
import DungeonoftheBrutalKing.Status.ReduceDefenseStatus;

public class ReliquarySpear extends WeaponManager {

    private static final int REQUIRED_WISDOM = 13;
    private static final int DAMAGE = 7;
    private static final int WEIGHT = 5;
    private static final Guild GUILDname = Guild.CELESTIAL_ARCANE_ORDER;
    private static final GuildType GUILDtype = GuildType.CLERIC;

    private boolean curseProtectionApplied = false;

    public ReliquarySpear(String effect) {
        super("Reliquary Spear", REQUIRED_WISDOM, DAMAGE, effect, WEIGHT);
    }

    @Override
    public boolean equip(Character wearer) {
        if (wearer != null && wearer.getCurrentGuild() == GUILDtype && (wearer.getEquippedWeapon() == null || !wearer.getEquippedWeapon().equals(getName()))) {
            wearer.setEquippedWeapon(getName());
            wearer.setWisdom(wearer.getWisdom() + 2);
            if (!wearer.hasEffectProtection("curse")) {
                wearer.setEffectProtection("curse", true);
                curseProtectionApplied = true;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Character wearer) {
        if (wearer != null && wearer.getEquippedWeapon() != null && wearer.getEquippedWeapon().equals(getName())) {
            wearer.setEquippedWeapon(null);
            int newWisdom = wearer.getWisdom() - 2;
            wearer.setWisdom(Math.max(newWisdom, 0));
            if (curseProtectionApplied && wearer.hasEffectProtection("curse")) {
                wearer.setEffectProtection("curse", false);
                curseProtectionApplied = false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void applyCombatEffect(HasHitPoints target) {
        if (target instanceof Character) {
            ((Character) target).addStatus(new ReduceDefenseStatus());
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
        return "Reliquary Spear: A sacred spear carried by clerics of the Celestial Arcane Order. It is adorned with holy relics and channels divine power to pierce the darkness.";
    }
}
