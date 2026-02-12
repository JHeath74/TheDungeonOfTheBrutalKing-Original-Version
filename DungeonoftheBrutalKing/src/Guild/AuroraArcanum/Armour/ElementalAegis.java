package Guild.AuroraArcanum.Armour;

import Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.EquipmentRequirement;
import SharedData.Guild;
import SharedData.GuildType;


public class ElementalAegis extends ArmourManager {

    private static final EquipmentRequirement REQUIREMENT = EquipmentRequirement.ELEMENTAL_MANTLE;
    private static final Guild GUILDname = Guild.AURORA_ARCANUM;
    private static final GuildType GUILDtype = GuildType.WIZARD;

    private static final int INTELLIGENCE_BONUS = 4;
    private static final double DEFENSE_BONUS_PERCENT = 0.12; // 12% defense bonus

    private final String elementType;
    private boolean isEquipped = false;
    private int lastDefenseBonus = 0;

    public ElementalAegis(String elementType, String effect) {
        super("Elemental Mantle", REQUIREMENT.getIntelligence(), REQUIREMENT.getDefense(), effect);
        this.elementType = elementType.toLowerCase();
    }

    @Override
    public boolean equip(Charecter wearer) {
        if (!isEquipped && wearer.getGuild() == GUILDname) {
            wearer.addResistance(elementType);
            wearer.setIntelligence(wearer.getIntelligence() + INTELLIGENCE_BONUS);
            lastDefenseBonus = (int) Math.round(wearer.getDefense() * DEFENSE_BONUS_PERCENT);
            wearer.setDefense(wearer.getDefense() + lastDefenseBonus);
            isEquipped = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Charecter wearer) {
        if (isEquipped) {
            wearer.removeResistance(elementType);
            wearer.setIntelligence(wearer.getIntelligence() - INTELLIGENCE_BONUS);
            wearer.setDefense(wearer.getDefense() - lastDefenseBonus);
            isEquipped = false;
            lastDefenseBonus = 0;
        }
        return !isEquipped;
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
        return (double) REQUIREMENT.getWeight();
    }

    @Override
    public String getDescription() {
        return "Elemental Mantle: Armour infused with " + elementType + " energy. Grants resistance and allows the wizard to channel it offensively.";
    }
}
