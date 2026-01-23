package Guild.CelestialArcaneOrder.Armour;

import Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;

public class OathbearersVestment extends ArmourManager {

    private static final int REQUIRED_WISDOM = 15;
    private static final int ARMOUR_DEFENSE = 7;
    private static final int WEIGHT = 2;
    private static final int WISDOM_BONUS = 2;
    private static final Guild GUILDname = Guild.CELESTIAL_ARCANE_ORDER;
    private static final GuildType GUILDtype = GuildType.CLERIC;

    private boolean isEquipped = false;
    private boolean stunProtectionApplied = false;
    private boolean wearerHadStunProtection = false;

    public OathbearersVestment(String effect) {
        super("Oathbearer’s Vestment", REQUIRED_WISDOM, ARMOUR_DEFENSE, effect);
    }

    @Override
    public void equip(Charecter wearer) {
        if (wearer != null && !isEquipped) {
            wearer.setArmour(getName());
            wearer.setWisdom(wearer.getWisdom() + WISDOM_BONUS);
            wearerHadStunProtection = wearer.hasEffectProtection("stun");
            if (!wearerHadStunProtection) {
                wearer.setEffectProtection("stun", true);
                stunProtectionApplied = true;
            }
            isEquipped = true;
        }
    }

    @Override
    public void unequip(Charecter wearer) {
        if (wearer != null && isEquipped) {
            wearer.setArmour(null);
            wearer.setWisdom(wearer.getWisdom() - WISDOM_BONUS);
            if (stunProtectionApplied && !wearerHadStunProtection) {
                wearer.setEffectProtection("stun", false);
            }
            stunProtectionApplied = false;
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
        return "Oathbearer’s Vestment: Revered vestment worn by those who uphold the sacred oaths of the Celestial Arcane Order. Empowers clerics with divine resilience and spiritual authority.";
    }
}
