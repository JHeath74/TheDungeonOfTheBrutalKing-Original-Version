
// src/Guild/AuroraArcanum/Armour/ElementalAegis.java
package Guild.AuroraArcanum.Armour;

import Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;

public class ElementalAegis extends ArmourManager {

    private static final int REQUIRED_INTELLIGENCE = 16; // Example value
    private static final double DEFENSE_BONUS_PERCENT = 0.10; // 10%
    private static final int WEIGHT = 4;
    private static final Guild GUILDname = Guild.AURORA_ARCANUM;
    private static final GuildType GUILDtype = GuildType.WIZARD;

    private final String elementType;
    private boolean isEquipped = false;
    private int defenseBonus = 0;

    public ElementalAegis(String elementType, String effect) {
        super("Elemental Mantle", REQUIRED_INTELLIGENCE, WEIGHT, effect);
        this.elementType = elementType.toLowerCase();
    }

    @Override
    public boolean equip(Charecter wearer) {
        if (!isEquipped && wearer.getGuild() == GUILDname && wearer.getIntelligence() >= REQUIRED_INTELLIGENCE) {
            int baseDefense = wearer.getDefense();
            defenseBonus = (int) Math.round(baseDefense * DEFENSE_BONUS_PERCENT);
            wearer.setDefense(wearer.getDefense() + defenseBonus);
            wearer.addResistance(elementType);
            isEquipped = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Charecter wearer) {
        if (isEquipped) {
            wearer.setDefense(wearer.getDefense() - defenseBonus);
            wearer.removeResistance(elementType);
            isEquipped = false;
            defenseBonus = 0;
            return true;
        }
        return false;
    }

    public void channelElement(Charecter caster, Charecter target) {
        target.takeElementalDamage(elementType, caster.getSpellPower());
    }

    public String getElementType() {
        return elementType;
    }

    public Guild getGuild() {
        return GUILDname;
    }

    public GuildType getGuildType() {
        return GUILDtype;
    }

    @Override
    public String getName() {
        return super.getName() + " of " + elementType.substring(0, 1).toUpperCase() + elementType.substring(1);
    }

    @Override
    public double getWeight() {
        return WEIGHT;
    }

    @Override
    public String getDescription() {
        return "Elemental Mantle: Armour infused with " + elementType + " energy. Grants resistance and allows the wizard to channel it offensively.";
    }
}
