package Guild.AuroraArcanum.Armour;

import Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;

public class ElementalMantle extends ArmourManager {

    private static final int REQUIRED_INTELLIGENCE = 14;
    private static final int ARMOUR_DEFENSE = 2;
    private static final int WEIGHT = 1;
    private static final Guild GUILDname = Guild.AURORA_ARCNUM;
    private static final GuildType GUILDtype = GuildType.WIZARD;

    private final String elementType; // e.g., "fire", "frost", "lightning"
    private boolean isEquipped = false;

    public ElementalMantle(String elementType, String effect) {
        super("Elemental Mantle", REQUIRED_INTELLIGENCE, ARMOUR_DEFENSE, effect);
        this.elementType = elementType.toLowerCase();
    }

    public void equip(Charecter wearer) {
        if (!isEquipped) {
            wearer.addResistance(elementType); // Assumes Charecter has addResistance
            isEquipped = true;
        }
    }

    public void unequip(Charecter wearer) {
        if (isEquipped) {
            wearer.removeResistance(elementType); // Assumes Charecter has removeResistance
            isEquipped = false;
        }
    }

    // Channel the element offensively (stub, implement as needed)
    public void channelElement(Charecter caster, Charecter target) {
        // Example: deal elemental damage to target
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
        return (double) WEIGHT;
    }

    @Override
    public String getDescription() {
        return "Elemental Mantle: Armour infused with " + elementType + " energy. Grants resistance and allows the wizard to channel it offensively.";
    }
}
