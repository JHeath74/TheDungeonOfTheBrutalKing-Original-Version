
java
package Guild.CrimsonBlades.Armour;

import Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;

public class BloodforgedBattleMail extends ArmourManager {

    private static final int REQUIRED_STRENGTH = 18;
    private static final int ARMOUR_DEFENSE = 14;
    private static final int WEIGHT = 7;
    private static final Guild GUILDname = Guild.CRIMSON_BLADES;
    private static final GuildType GUILDtype = GuildType.WARRIOR;

    public BloodforgedBattleMail(String effect) {
        super("Bloodforged BattleMail", REQUIRED_STRENGTH, ARMOUR_DEFENSE, effect);
    }

    @Override
    public void equip(Charecter wearer) {
        if (wearer != null && (wearer.getArmour() == null || !wearer.getArmour().equals(getName()))) {
            wearer.setArmour(getName());
            wearer.setStrength(wearer.getStrength() + 5);
            wearer.setCritChance(wearer.getCritChance() + 8);
            wearer.setEffectProtection("bleed", true);
            wearer.setEffectProtection("curse", true);
        }
    }

    @Override
    public void unequip(Charecter wearer) {
        if (wearer != null && wearer.getArmour() != null && wearer.getArmour().equals(getName())) {
            wearer.setArmour(null);
            wearer.setStrength(wearer.getStrength() - 5);
            wearer.setCritChance(wearer.getCritChance() - 8);
            wearer.setEffectProtection("bleed", false);
            wearer.setEffectProtection("curse", false);
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
        return "Bloodforged BattleMail: Legendary mail infused with the blood of ancient warriors. Grants immense strength, high resilience, and protects against bleeding and curses.";
    }
}
