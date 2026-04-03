
package DungeonoftheBrutalKing.Guild.CelestialArcaneOrder.Armour;

import DungeonoftheBrutalKing.Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.SharedData.GuildType;

public class ReliquaryWardmail extends ArmourManager {

    private static final int REQUIRED_WISDOM = 13;
    private static final int ARMOUR_DEFENSE = 25;
    private static final int WEIGHT = 2;
    private static final int WISDOM_BONUS = 12;
    private static final Guild GUILDname = Guild.CELESTIAL_ARCANE_ORDER;
    private static final GuildType GUILDtype = GuildType.CLERIC;

    private boolean isEquipped = false;
    private boolean echoProtectionApplied = false;
    private boolean wearerHadEchoProtection = false;

    public ReliquaryWardmail(String effect) {
        // requiredStrength unused (wisdom-gated), so pass 0
        super("Reliquary Wardmail", 0, ARMOUR_DEFENSE, WEIGHT, effect);
    }

    @Override
    public boolean equip(Charecter wearer) {
        if (wearer != null && !isEquipped
                && wearer.getGuild() == GUILDname
                && wearer.getWisdom() >= REQUIRED_WISDOM) {

            wearer.setEuippedArmour(getName());
            wearer.setWisdom(wearer.getWisdom() + WISDOM_BONUS);

            wearerHadEchoProtection = wearer.hasEffectProtection("echo");
            if (!wearerHadEchoProtection) {
                wearer.setEffectProtection("echo", true);
                echoProtectionApplied = true;
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

            if (echoProtectionApplied && !wearerHadEchoProtection) {
                wearer.setEffectProtection("echo", false);
            }

            echoProtectionApplied = false;
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
        return "Reliquary Wardmail: Blessed mail imbued with relics of the Celestial Arcane Order. Grants clerics enhanced protection and channels divine energy.";
    }
}
