package Guild.CelestialArcaneOrder.Armour;

import Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;

public class SanctumAegisPlate extends ArmourManager {

    private static final int REQUIRED_WISDOM = 10;
    private static final int ARMOUR_DEFENSE = 23;
    private static final int WEIGHT = 2;
    private static final int WISDOM_BONUS = 23;
    private static final Guild GUILDname = Guild.CELESTIAL_ARCANE_ORDER;
    private static final GuildType GUILDtype = GuildType.CLERIC;

    private boolean isEquipped = false;
    private boolean curseProtectionApplied = false;
    private boolean wearerHadCurseProtection = false;

    public SanctumAegisPlate(String effect) {
        super("Sanctum Aegis Plate", REQUIRED_WISDOM, ARMOUR_DEFENSE, effect);
    }

    @Override
    public boolean equip(Charecter wearer) {
        if (wearer != null && !isEquipped) {
            wearer.setArmour(getName());
            wearer.setWisdom(wearer.getWisdom() + WISDOM_BONUS);
            wearerHadCurseProtection = wearer.hasEffectProtection("curse");
            if (!wearerHadCurseProtection) {
                wearer.setEffectProtection("curse", true);
                curseProtectionApplied = true;
            }
            isEquipped = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Charecter wearer) {
        if (wearer != null && isEquipped) {
            wearer.setArmour(null);
            wearer.setWisdom(wearer.getWisdom() - WISDOM_BONUS);
            if (curseProtectionApplied && !wearerHadCurseProtection) {
                wearer.setEffectProtection("curse", false);
            }
            curseProtectionApplied = false;
            isEquipped = false;
            return true;
        }
        return false;
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
        return "Sanctum Aegis Plate: Blessed plate armour for clerics, offering protection from curses and enhancing divine wisdom.";
    }
}
