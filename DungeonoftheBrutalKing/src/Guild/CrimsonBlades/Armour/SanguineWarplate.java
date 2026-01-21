
// File: src/Guild/CrimsonBlades/Armour/SanguineWarplate.java

package Guild.CrimsonBlades.Armour;

import Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;

public class SanguineWarplate extends ArmourManager {

    private static final int REQUIRED_STRENGTH = 19;
    private static final int ARMOUR_DEFENSE = 15;
    private static final int WEIGHT = 8;
    private static final Guild GUILDname = Guild.CRIMSON_BLADES;
    private static final GuildType GUILDtype = GuildType.WARRIOR;

    private boolean isEquipped = false;

    public SanguineWarplate(String effect) {
        super("Sanguine Warplate", REQUIRED_STRENGTH, ARMOUR_DEFENSE, effect);
    }

    public void equip(Charecter wearer) {
        if (wearer != null && !isEquipped) {
            wearer.setArmour(getName());
            wearer.setStrength(wearer.getStrength() + 6); // Warplate strength bonus
            wearer.setCritChance(wearer.getCritChance() + 10); // High crit bonus
            wearer.setEffectProtection("bleed", true);
            wearer.setEffectProtection("burn", true); // Protection against burn
            isEquipped = true;
        }
    }

    public void unequip(Charecter wearer) {
        if (wearer != null && isEquipped) {
            wearer.setArmour(null);
            wearer.setStrength(wearer.getStrength() - 6);
            wearer.setCritChance(wearer.getCritChance() - 10);
            wearer.setEffectProtection("bleed", false);
            wearer.setEffectProtection("burn", false);
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
        return "Sanguine Warplate: A fearsome crimson warplate pulsing with power. Greatly increases strength, critical prowess, and protects against bleeding and burning.";
    }
}
