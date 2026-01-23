package Guild.AuroraArcanum.Armour;

import Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.EquipmentRequirement;
import SharedData.Guild;
import SharedData.GuildType;

public class ElementalMantle extends ArmourManager {

    private static final EquipmentRequirement REQUIREMENT = EquipmentRequirement.ELEMENTAL_MANTLE;
    private static final Guild GUILDname = Guild.AURORA_ARCANUM;
    private static final GuildType GUILDtype = GuildType.WIZARD;

    private final String elementType; // e.g., "fire", "frost", "lightning"
    private boolean isEquipped = false;

    public ElementalMantle(String elementType, String effect) {
        super("Elemental Mantle", REQUIREMENT.getIntelligence(), REQUIREMENT.getDefense(), effect);
        this.elementType = elementType.toLowerCase();
    }

    @Override
    public void equip(Charecter wearer) {
        if (!isEquipped) {
            wearer.addResistance(elementType);
            isEquipped = true;
        }
    }

    @Override
    public void unequip(Charecter wearer) {
        if (isEquipped) {
            wearer.removeResistance(elementType);
            isEquipped = false;
        }
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
