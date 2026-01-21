
// File: src/Guild/CrimsonBlades/Armour/WarlordsCrimsonHarness.java

package Guild.CrimsonBlades.Armour;

import Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;

public class WarlordsCrimsonHarness extends ArmourManager {

    private static final int REQUIRED_STRENGTH = 20;
    private static final int ARMOUR_DEFENSE = 16;
    private static final int WEIGHT = 9;
    private static final Guild GUILDname = Guild.CRIMSON_BLADES;
    private static final GuildType GUILDtype = GuildType.WARRIOR;

    private boolean isEquipped = false;

    public WarlordsCrimsonHarness(String effect) {
        super("Warlords Crimson Harness", REQUIRED_STRENGTH, ARMOUR_DEFENSE, effect);
    }

    public void equip(Charecter wearer) {
        if (wearer != null && !isEquipped) {
            wearer.setArmour(getName());
            wearer.setStrength(wearer.getStrength() + 7); // Warlord strength bonus
            wearer.setCritChance(wearer.getCritChance() + 12); // High crit bonus
            wearer.setEffectProtection("bleed", true);
            wearer.setEffectProtection("stun", true); // Protection against stun
            isEquipped = true;
        }
    }

    public void unequip(Charecter wearer) {
        if (wearer != null && isEquipped) {
            wearer.setArmour(null);
            wearer.setStrength(wearer.getStrength() - 7);
            wearer.setCritChance(wearer.getCritChance() - 12);
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
        return "Warlords Crimson Harness: The ultimate harness of the Crimson Blades, forged for warlords. Grants immense strength, critical mastery, and protection against bleeding and stunning effects.";
    }
}
