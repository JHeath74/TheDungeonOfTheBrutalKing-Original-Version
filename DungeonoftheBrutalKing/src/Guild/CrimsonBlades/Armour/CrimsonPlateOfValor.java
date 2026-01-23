
// File: src/Guild/CrimsonBlades/Armour/CrimsonPlateOfValor.java

package Guild.CrimsonBlades.Armour;

import Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;

public class CrimsonPlateOfValor extends ArmourManager {

    private static final int REQUIRED_STRENGTH = 17;
    private static final int ARMOUR_DEFENSE = 12;
    private static final int WEIGHT = 6;
    private static final Guild GUILDname = Guild.CRIMSON_BLADES;
    private static final GuildType GUILDtype = GuildType.WARRIOR;

    public CrimsonPlateOfValor(String effect) {
        super("Crimon Plate Of Valor", REQUIRED_STRENGTH, ARMOUR_DEFENSE, effect);
    }

    @Override
    public void equip(Charecter wearer) {
        if (wearer != null && (wearer.getArmour() == null || !wearer.getArmour().equals(getName()))) {
            wearer.setArmour(getName());
            wearer.setStrength(wearer.getStrength() + 4);
            wearer.setCritChance(wearer.getCritChance() + 6);
            wearer.setEffectProtection("fear", true);
        }
    }

    @Override
    public void unequip(Charecter wearer) {
        if (wearer != null && wearer.getArmour() != null && wearer.getArmour().equals(getName())) {
            wearer.setArmour(null);
            wearer.setStrength(wearer.getStrength() - 4);
            wearer.setCritChance(wearer.getCritChance() - 6);
            wearer.setEffectProtection("fear", false);
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
        return "Crimon Plate Of Valor: A mighty crimson plate that emboldens its wearer, granting strength, resilience, and protection against fear.";
    }
}
