
package DungeonoftheBrutalKing.Guild.CelestialArcaneOrder.Weapon;

import DungeonoftheBrutalKing.Weapon.WeaponManager;
import DungeonoftheBrutalKing.Status.DrainStatus;
import DungeonoftheBrutalKing.Status.StatusManager;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.Status.HasHitPoints;

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
    public boolean equip(Character wearer) {
        if (wearer != null
            && wearer.getCurrentGuild() == GUILDtype
            && wearer.getWisdom() >= REQUIRED_WISDOM) {
            wearer.setEquippedWeapon(getName());
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Character wearer) {
        if (wearer != null && getName().equals(wearer.getEquippedWeapon())) {
            wearer.setEquippedWeapon(null);
        }
		return false;
    }

    @Override
    public void applyCombatEffect(HasHitPoints target) {
        if (target instanceof Character) {
            Character character = (Character) target;
            // Apply DrainStatus for 2 turns
            character.addStatus(new DrainStatus(2, requiredStrength, null));
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
