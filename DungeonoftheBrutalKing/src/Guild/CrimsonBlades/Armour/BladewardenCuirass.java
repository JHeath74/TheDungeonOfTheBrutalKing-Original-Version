package Guild.CrimsonBlades.Armour;

import Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;

public class BladewardenCuirass extends ArmourManager {

    private static final int REQUIRED_STRENGTH = 16;
    private static final int ARMOUR_DEFENSE = 10;
    private static final int WEIGHT = 5;
    private static final int BONUS_DEFENSE = 2; // New stat bonus
    private static final Guild GUILDname = Guild.CRIMSON_BLADES;
    private static final GuildType GUILDtype = GuildType.WARRIOR;

    private boolean isEquipped = false;

    public BladewardenCuirass(String effect) {
        super("Bladewarden Cuirass", REQUIRED_STRENGTH, ARMOUR_DEFENSE, effect);
    }

    @Override
    public void equip(Charecter wearer) {
        if (wearer != null && !isEquipped) {
            wearer.setArmour(getName());
            wearer.setStrength(wearer.getStrength() + 3); // Warrior stat bonus
            wearer.setCritChance(wearer.getCritChance() + 5); // Critical hit bonus
            wearer.setDefense(wearer.getDefense() + BONUS_DEFENSE); // Stat bonus
            wearer.setEffectProtection("bleed", true); // Protection against "bleed"
            wearer.setEffectProtection("stun", true); // Status defense example
            isEquipped = true;
        }
    }

    @Override
    public void unequip(Charecter wearer) {
        if (wearer != null && isEquipped) {
            wearer.setArmour(null);
            wearer.setStrength(wearer.getStrength() - 3);
            wearer.setCritChance(wearer.getCritChance() - 5);
            wearer.setDefense(wearer.getDefense() - BONUS_DEFENSE);
            wearer.setEffectProtection("bleed", false);
            wearer.setEffectProtection("stun", false);
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
        return "Bladewarden Cuirass: Heavy crimson plate forged for the Crimson Blades. Greatly increases strength, resilience, and defense, and protects against bleeding and stun effects.";
    }
}
