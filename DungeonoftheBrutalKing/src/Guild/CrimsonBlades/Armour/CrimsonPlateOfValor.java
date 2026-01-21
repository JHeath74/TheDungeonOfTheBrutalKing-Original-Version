
// File: src/Guild/CrimsonBlades/Armour/CrimonPlateOfValor.java

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

    private boolean isEquipped = false;

    public CrimsonPlateOfValor(String effect) {
        super("Crimon Plate Of Valor", REQUIRED_STRENGTH, ARMOUR_DEFENSE, effect);
    }

    public void equip(Charecter wearer) {
        if (wearer != null && !isEquipped) {
            wearer.setArmour(getName());
            wearer.setStrength(wearer.getStrength() + 4); // Valor stat bonus
            wearer.setCritChance(wearer.getCritChance() + 6); // Critical hit bonus
            wearer.setEffectProtection("fear", true); // Protection against "fear"
            isEquipped = true;
        }
    }

    public void unequip(Charecter wearer) {
        if (wearer != null && isEquipped) {
            wearer.setArmour(null);
            wearer.setStrength(wearer.getStrength() - 4);
            wearer.setCritChance(wearer.getCritChance() - 6);
            wearer.setEffectProtection("fear", false);
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
        return "Crimon Plate Of Valor: A mighty crimson plate that emboldens its wearer, granting strength, resilience, and protection against fear.";
    }
}
