package Guild.CelestialArcaneOrder.Armour;

import Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;

public class VestureoftheDawnbound extends ArmourManager {

    private static final int REQUIRED_WISDOM = 12;
    private static final int ARMOUR_DEFENSE = 4;
    private static final int WEIGHT = 1;
    private static final int WISDOM_BONUS = 2;
    private static final Guild GUILDname = Guild.CELESTIAL_ARCANE_ORDER;
    private static final GuildType GUILDtype = GuildType.CLERIC;

    private boolean isEquipped = false;
    private boolean fireProtectionApplied = false;
    private boolean wearerHadFireProtection = false;

    public VestureoftheDawnbound(String effect) {
        super("Vesture of the Dawnbound", REQUIRED_WISDOM, ARMOUR_DEFENSE, effect);
    }

    @Override
    public void equip(Charecter wearer) {
        if (wearer != null && !isEquipped) {
            wearer.setArmour(getName());
            wearer.setWisdom(wearer.getWisdom() + WISDOM_BONUS);
            wearerHadFireProtection = wearer.hasEffectProtection("fire");
            if (!wearerHadFireProtection) {
                wearer.setEffectProtection("fire", true);
                fireProtectionApplied = true;
            }
            isEquipped = true;
        }
    }

    @Override
    public void unequip(Charecter wearer) {
        if (wearer != null && isEquipped) {
            wearer.setArmour(null);
            wearer.setWisdom(wearer.getWisdom() - WISDOM_BONUS);
            if (fireProtectionApplied && !wearerHadFireProtection) {
                wearer.setEffectProtection("fire", false);
            }
            fireProtectionApplied = false;
            isEquipped = false;
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
        return "Vesture of the Dawnbound: Sacred vestment woven with celestial threads, favored by clerics of the Celestial Arcane Order. Enhances divine protection and spellcasting.";
    }
}
