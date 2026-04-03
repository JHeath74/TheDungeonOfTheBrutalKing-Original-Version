package DungeonoftheBrutalKing.Guild.CelestialArcaneOrder.Armour;

import DungeonoftheBrutalKing.Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.SharedData.GuildType;

public class ChaliceguardHauberk extends ArmourManager {

    private static final int REQUIRED_WISDOM = 14;
    private static final int ARMOUR_DEFENSE = 22;
    private static final int WEIGHT = 3;
    private static final int WISDOM_BONUS = 11;
    private static final Guild GUILDname = Guild.CELESTIAL_ARCANE_ORDER;
    private static final GuildType GUILDtype = GuildType.CLERIC;

    private boolean isEquipped = false;
    private boolean radiantProtectionApplied = false;
    private boolean wearerHadRadiantProtection = false;

    public ChaliceguardHauberk(String effect) {
        // requiredStrength is unused for clerics here, so pass 0
        super("Chaliceguard Hauberk", 0, ARMOUR_DEFENSE, WEIGHT, effect);
    }

    @Override
    public boolean equip(Charecter wearer) {
        if (wearer != null && !isEquipped
                && wearer.getGuild() == GUILDname
                && wearer.getWisdom() >= REQUIRED_WISDOM) {

            wearer.setEuippedArmour(getName());
            wearer.setWisdom(wearer.getWisdom() + WISDOM_BONUS);

            wearerHadRadiantProtection = wearer.hasEffectProtection("radiant");
            if (!wearerHadRadiantProtection) {
                wearer.setEffectProtection("radiant", true);
                radiantProtectionApplied = true;
            }

            isEquipped = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Charecter wearer) {
        if (wearer != null && isEquipped) {
            wearer.setEuippedArmour(null);
            wearer.setWisdom(wearer.getWisdom() - WISDOM_BONUS);

            if (radiantProtectionApplied && !wearerHadRadiantProtection) {
                wearer.setEffectProtection("radiant", false);
            }

            radiantProtectionApplied = false;
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
        return "Chaliceguard Hauberk: Sacred hauberk adorned with the sigil of the chalice, worn by the Celestial Arcane Order's clerics. Offers potent divine protection and amplifies holy rites.";
    }
}
