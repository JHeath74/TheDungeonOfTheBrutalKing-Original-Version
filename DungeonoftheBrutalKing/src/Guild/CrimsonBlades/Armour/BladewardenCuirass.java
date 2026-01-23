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
    private static final Guild GUILDname = Guild.CRIMSON_BLADES;
    private static final GuildType GUILDtype = GuildType.WARRIOR;

    public BladewardenCuirass(String effect) {
        super("Bladewarden Cuirass", REQUIRED_STRENGTH, ARMOUR_DEFENSE, effect);
    }

    @Override
    public void equip(Charecter wearer) {
        if (wearer != null && (wearer.getArmour() == null || !wearer.getArmour().equals(getName()))) {
            wearer.setArmour(getName());
            wearer.setStrength(wearer.getStrength() + 3);
            wearer.setCritChance(wearer.getCritChance() + 5);
            wearer.setDefense(wearer.getDefense() + BONUS_DEFENSE);
            wearer.setEffectProtection("bleed", true);
            wearer.setEffectProtection("stun", true);
        }
    }

    @Override
    public void unequip(Charecter wearer) {
        if (wearer != null && wearer.getArmour() != null && wearer.getArmour().equals(getName())) {
            wearer.setArmour(null);
            wearer.setStrength(wearer.getStrength() - 3);
            wearer.setCritChance(wearer.getCritChance() - 5);
            wearer.setDefense(wearer.getDefense() - BONUS_DEFENSE);
            wearer.setEffectProtection("bleed", false);
            wearer.setEffectProtection("stun", false);
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
        return "Bladewarden Cuirass: Heavy crimson plate forged for the Crimson Blades. Greatly increases strength, resilience, and defense, and protects against bleeding and stun effects.";
    }
}
