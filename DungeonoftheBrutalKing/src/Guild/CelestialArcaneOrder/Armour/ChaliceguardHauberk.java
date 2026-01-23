package Guild.CelestialArcaneOrder.Armour;

import Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;

public class ChaliceguardHauberk extends ArmourManager {

    private static final int REQUIRED_WISDOM = 14;
    private static final int ARMOUR_DEFENSE = 6;
    private static final int WEIGHT = 3;
    private static final int WISDOM_BONUS = 2;
    private static final Guild GUILDname = Guild.CELESTIAL_ARCANE_ORDER;
    private static final GuildType GUILDtype = GuildType.CLERIC;

    private boolean isEquipped = false;
    private boolean radiantProtectionApplied = false;
    private boolean wearerHadRadiantProtection = false;

    public ChaliceguardHauberk(String effect) {
        super("Chaliceguard Hauberk", REQUIRED_WISDOM, ARMOUR_DEFENSE, effect);
    }

    @Override
    public void equip(Charecter wearer) {
        if (wearer != null && !isEquipped) {
            wearer.setArmour(getName());
            wearer.setWisdom(wearer.getWisdom() + WISDOM_BONUS);
            wearerHadRadiantProtection = wearer.hasEffectProtection("radiant");
            if (!wearerHadRadiantProtection) {
                wearer.setEffectProtection("radiant", true);
                radiantProtectionApplied = true;
            }
            isEquipped = true;
        }
    }

    @Override
    public void unequip(Charecter wearer) {
        if (wearer != null && isEquipped) {
            wearer.setArmour(null);
            wearer.setWisdom(wearer.getWisdom() - WISDOM_BONUS);
            if (radiantProtectionApplied && !wearerHadRadiantProtection) {
                wearer.setEffectProtection("radiant", false);
            }
            radiantProtectionApplied = false;
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
        return "Chaliceguard Hauberk: Sacred hauberk adorned with the sigil of the chalice, worn by the Celestial Arcane Order's clerics. Offers potent divine protection and amplifies holy rites.";
    }
}
