package Guild.CelestialArcaneOrder.Armour;

import Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;

public class ReliquaryWardmail extends ArmourManager {

    private static final int REQUIRED_WISDOM = 13;
    private static final int ARMOUR_DEFENSE = 5;
    private static final int WEIGHT = 2;
    private static final int WISDOM_BONUS = 2;
    private static final Guild GUILDname = Guild.CELESTIAL_ARCANE_ORDER;
    private static final GuildType GUILDtype = GuildType.CLERIC;

    private boolean isEquipped = false;
    private boolean echoProtectionApplied = false;
    private boolean wearerHadEchoProtection = false;

    public ReliquaryWardmail(String effect) {
        super("Reliquary Wardmail", REQUIRED_WISDOM, ARMOUR_DEFENSE, effect);
    }

    @Override
    public void equip(Charecter wearer) {
        if (wearer != null && !isEquipped) {
            wearer.setArmour(getName());
            wearer.setWisdom(wearer.getWisdom() + WISDOM_BONUS);
            wearerHadEchoProtection = wearer.hasEffectProtection("echo");
            if (!wearerHadEchoProtection) {
                wearer.setEffectProtection("echo", true);
                echoProtectionApplied = true;
            }
            isEquipped = true;
        }
    }

    @Override
    public void unequip(Charecter wearer) {
        if (wearer != null && isEquipped) {
            wearer.setArmour(null);
            wearer.setWisdom(wearer.getWisdom() - WISDOM_BONUS);
            if (echoProtectionApplied && !wearerHadEchoProtection) {
                wearer.setEffectProtection("echo", false);
            }
            echoProtectionApplied = false;
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
        return "Reliquary Wardmail: Blessed mail imbued with relics of the Celestial Arcane Order. Grants clerics enhanced protection and channels divine energy.";
    }
}
