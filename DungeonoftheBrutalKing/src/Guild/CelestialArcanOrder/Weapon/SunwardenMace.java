
// File: src/Guild/CelestialArcanOrder/Weapons/SunwardenMace.java

package Guild.CelestialArcanOrder.Weapon;

import Weapon.WeaponManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;

public class SunwardenMace extends WeaponManager {

    private static final int REQUIRED_WISDOM = 12;
    private static final int DAMAGE = 8;
    private static final int WEIGHT = 3;
    private static final Guild GUILDname = Guild.CELESTIAL_ARCANE_ORDER;
    private static final GuildType GUILDtype = GuildType.CLERIC;

    public SunwardenMace(String effect) {
        super("Sunwarden Mace", REQUIRED_WISDOM, DAMAGE, effect, DAMAGE);
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
