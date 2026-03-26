
package Guild.CelestialArcaneOrder.Weapon;

import Weapon.WeaponManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;
import Status.HasHitPoints;
import Status.Status;
import Status.StatusType;

public class VigilantCenserFlail extends WeaponManager {

    private static final int REQUIRED_WISDOM = 13;
    private static final int DAMAGE = 7;
    private static final int WEIGHT = 3;
    private static final int BONUS_WISDOM = 2;
    private static final Guild GUILDname = Guild.CELESTIAL_ARCANE_ORDER;
    private static final GuildType GUILDtype = GuildType.CLERIC;

    public VigilantCenserFlail(String effect) {
        super("Vigilant Censer‐Flail", REQUIRED_WISDOM, DAMAGE, effect, WEIGHT);
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
            return true;
        }
        return false;
    }

    @Override
    public void applyEffect(HasHitPoints target) {
        if (target != null && getStatusEffect() != null) {
            StatusEffect effect = getStatusEffect();
            StatusType type = mapStatusEffectToType(effect);
            Status effectStatus = new Status(effect.name(), 1, true, type);
            target.addStatus(effectStatus);
        }
    }

    // Only include cases that exist in WeaponManager.StatusEffect
    private StatusType mapStatusEffectToType(StatusEffect effect) {
        switch (effect) {
            case POISON: return StatusType.POISON_STATUS;
            case STUN: return StatusType.STUN_STATUS;
            case BLEED: return StatusType.BLEED_STATUS;
            case FIRE: return StatusType.FIRE_STATUS;
            case COLD: return StatusType.ICE_STATUS;
            // Add more cases here if they exist in StatusEffect
            default: return StatusType.ACCURACY_STATUS;
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
        return "Vigilant Censer‐Flail: A sacred flail with a burning censer, wielded by vigilant clerics of the Celestial Arcane Order. Swings with holy fervor, purifying foes and sanctifying the battlefield.";
    }
}
