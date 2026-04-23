package DungeonoftheBrutalKing.Guild.CelestialArcaneOrder.Weapon;

import DungeonoftheBrutalKing.Weapon.WeaponManager;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.Status.HasHitPoints;
import DungeonoftheBrutalKing.Status.Status;
import DungeonoftheBrutalKing.Status.StatusType;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.Status.StatusPolarity;

public class VigilantCenserFlail extends WeaponManager {

    private static final int REQUIRED_WISDOM = 13;
    private static final int DAMAGE = 7;
    private static final int WEIGHT = 3;
    private static final int BONUS_WISDOM = 2;
    private static final Guild GUILDname = Guild.CELESTIAL_ARCANE_ORDER;
    private static final GuildType GUILDtype = GuildType.CLERIC;

    public VigilantCenserFlail(String effect) {
        super("Vigilant Censer-Flail", REQUIRED_WISDOM, DAMAGE, effect, WEIGHT);
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
        StatusType effectType = getStatusEffect();
        if (target == null || effectType == null || effectType == StatusType.NONE) {
            return;
        }

        Status effectStatus = new Status(
                effectType.name(),
                1,                          // 1 minute duration (60 seconds)
                StatusPolarity.NEGATIVE,    // or POSITIVE, depending on your design
                effectType
        );
        
        // Basic 1-turn status, non-stacking; adjust duration/stacking as needed

        if (target instanceof Character) {
            ((Character) target).addStatus(effectStatus);
        } else if (target instanceof Enemies) {
            ((Enemies) target).addStatus(effectStatus);
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
        return "Vigilant Censer-Flail: A sacred flail with a burning censer, wielded by vigilant clerics of the Celestial Arcane Order. Swings with holy fervor, purifying foes and sanctifying the battlefield.";
    }
}
