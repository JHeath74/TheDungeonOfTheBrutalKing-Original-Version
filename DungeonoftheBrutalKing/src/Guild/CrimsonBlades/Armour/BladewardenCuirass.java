
package Guild.CrimsonBlades.Armour;

import Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;

public class BladewardenCuirass extends ArmourManager {

    private static final int REQUIRED_STRENGTH = 16;
    private static final int ARMOUR_DEFENSE = 10;
    private static final int WEIGHT = 5;
    private static final int BONUS_DEFENSE = 2;
    private static final int BONUS_STRENGTH = 3;
    private static final int BONUS_CRIT_CHANCE = 5;
    private static final Guild GUILD_NAME = Guild.CRIMSON_BLADES;
    private static final GuildType GUILD_TYPE = GuildType.WARRIOR;
    private static final String ARMOUR_NAME = "Bladewarden Cuirass";
    private static final String DESCRIPTION = "Bladewarden Cuirass: Heavy crimson plate forged for the Crimson Blades. Greatly increases strength, resilience, and defense, and protects against bleeding and stun effects.";

    public BladewardenCuirass(String effect) {
        super(ARMOUR_NAME, REQUIRED_STRENGTH, ARMOUR_DEFENSE, effect);
    }

    @Override
    public boolean equip(Charecter wearer) {
        if (wearer == null) return false;
        if (wearer.getArmour() == null || !wearer.getArmour().equals(getName())) {
            wearer.setArmour(getName());
            wearer.setStrength(wearer.getStrength() + BONUS_STRENGTH);
            wearer.setCritChance(wearer.getCritChance() + BONUS_CRIT_CHANCE);
            wearer.setDefense(wearer.getDefense() + BONUS_DEFENSE);
            if (wearer.getEffectProtection() != null) {
                wearer.getEffectProtection().add("bleed");
                wearer.getEffectProtection().add("stun");
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
            wearer.setDefense(wearer.getDefense() - BONUS_DEFENSE);
            if (wearer.getEffectProtection() != null) {
                wearer.getEffectProtection().remove("bleed");
                wearer.getEffectProtection().remove("stun");
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
