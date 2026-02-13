package Guild.CrimsonBlades.Armour;

import Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;

public class CrimsonPlateOfValor extends ArmourManager {

    private static final int REQUIRED_STRENGTH = 17;
    private static final int ARMOUR_DEFENSE = 12;
    private static final int WEIGHT = 6;
    private static final int BONUS_STRENGTH = 4;
    private static final int BONUS_CRIT_CHANCE = 6;
    private static final Guild GUILD_NAME = Guild.CRIMSON_BLADES;
    private static final GuildType GUILD_TYPE = GuildType.WARRIOR;
    private static final String ARMOUR_NAME = "Crimson Plate Of Valor";
    private static final String DESCRIPTION = "Crimson Plate Of Valor: A mighty crimson plate that emboldens its wearer, granting strength, resilience, and protection against fear.";
    private static final String PROTECTION_FEAR = "fear";

    public CrimsonPlateOfValor(String effect) {
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
                wearer.getEffectProtection().add(PROTECTION_FEAR);
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
                wearer.getEffectProtection().remove(PROTECTION_FEAR);
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
