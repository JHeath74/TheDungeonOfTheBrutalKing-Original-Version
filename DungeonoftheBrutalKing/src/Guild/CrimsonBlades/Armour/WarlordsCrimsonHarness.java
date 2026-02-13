package Guild.CrimsonBlades.Armour;

import Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;
import java.util.Collections;

public class WarlordsCrimsonHarness extends ArmourManager {

    private static final int REQUIRED_STRENGTH = 20;
    private static final int ARMOUR_DEFENSE = 16;
    private static final int WEIGHT = 9;
    private static final int BONUS_STRENGTH = 7;
    private static final int BONUS_CRIT_CHANCE = 12;
    private static final Guild GUILD_NAME = Guild.CRIMSON_BLADES;
    private static final GuildType GUILD_TYPE = GuildType.WARRIOR;
    private static final String ARMOUR_NAME = "Warlords Crimson Harness";
    private static final String DESCRIPTION = "Warlords Crimson Harness: The ultimate harness of the Crimson Blades, forged for warlords. Grants immense strength, critical mastery, and protection against bleeding and stunning effects.";
    private static final String PROTECTION_BLEED = "bleed";
    private static final String PROTECTION_STUN = "stun";

    public WarlordsCrimsonHarness(String effect) {
        super(ARMOUR_NAME, REQUIRED_STRENGTH, ARMOUR_DEFENSE, effect);
    }

    @Override
    public boolean equip(Charecter wearer) {
        if (wearer == null) return false;
        if (wearer.getArmour() == null || !wearer.getArmour().equals(getName())) {
            wearer.setArmour(getName());
            wearer.setStrength(wearer.getStrength() + BONUS_STRENGTH);
            wearer.setCritChance(wearer.getCritChance() + BONUS_CRIT_CHANCE);
            if (wearer.getEffectProtection() != null) {
                Collections.addAll(wearer.getEffectProtection(), PROTECTION_BLEED, PROTECTION_STUN);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Charecter wearer) {
        if (wearer == null) return false;
        if (wearer.getArmour() != null && wearer.getArmour().equals(getName())) {
            wearer.setArmour(null);
            wearer.setStrength(wearer.getStrength() - BONUS_STRENGTH);
            wearer.setCritChance(wearer.getCritChance() - BONUS_CRIT_CHANCE);
            if (wearer.getEffectProtection() != null) {
                wearer.getEffectProtection().remove(PROTECTION_BLEED);
                wearer.getEffectProtection().remove(PROTECTION_STUN);
            }
            return true;
        }
        return false;
    }

    public Guild getGuild() {
        return GUILD_NAME;
    }

    public GuildType getGuildType() {
        return GUILD_TYPE;
    }

    @Override
    public String getName() {
        return ARMOUR_NAME;
    }

    @Override
    public double getWeight() {
        return WEIGHT;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
