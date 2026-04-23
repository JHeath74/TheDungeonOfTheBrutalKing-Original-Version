package DungeonoftheBrutalKing.Guild.AuroraArcanum.Armour;

import DungeonoftheBrutalKing.Armour.ArmourManager;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.SharedData.GuildType;

public class ElementalAegis extends ArmourManager {

    private static final int REQUIRED_INTELLIGENCE = 16; // example value
    private static final double DEFENSE_BONUS_PERCENT = 0.10; // 10%
    private static final int ARMOUR_DEFENSE = 4;              // base defence for the item
    private static final int WEIGHT = 4;
    private static final Guild GUILDname = Guild.AURORA_ARCANUM;
    private static final GuildType GUILDtype = GuildType.WIZARD;

    private final String elementType;
    private boolean isEquipped = false;
    private int defenseBonus = 0;

    public ElementalAegis(String elementType, String effect) {
        // requiredStrength is not used for wizards, so pass 0
        super("Elemental Mantle", 0, ARMOUR_DEFENSE, WEIGHT, effect);
        this.elementType = elementType.toLowerCase();
    }

    @Override
    public boolean equip(Character wearer) {
        if (!isEquipped
                && wearer.getGuild() == GUILDname
                && wearer.getIntelligence() >= REQUIRED_INTELLIGENCE) {

            int baseDefense = wearer.getDefense();
            defenseBonus = (int) Math.round(baseDefense * DEFENSE_BONUS_PERCENT);
            wearer.setDefense(baseDefense + defenseBonus);

            // TODO: implement elemental resistance on Charecter, then call:
            // wearer.addResistance(elementType);

            isEquipped = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Character wearer) {
        if (isEquipped) {
            wearer.setDefense(wearer.getDefense() - defenseBonus);

            // TODO: implement elemental resistance on Charecter, then call:
            // wearer.removeResistance(elementType);

            isEquipped = false;
            defenseBonus = 0;
            return true;
        }
        return false;
    }

    // Optional offensive channel, currently a no-op until you add APIs to Charecter
    public void channelElement(Character caster, Character target) {
        // TODO: add getSpellPower() and takeElementalDamage(...) to Charecter, then:
        // target.takeElementalDamage(elementType, caster.getSpellPower());
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
        return super.getName() + " of "
                + elementType.substring(0, 1).toUpperCase()
                + elementType.substring(1);
    }

    @Override
    public double getWeight() {
        return WEIGHT;
    }

    @Override
    public String getDescription() {
        return "Elemental Mantle: Armour infused with "
                + elementType
                + " energy. Grants resistance and allows the wizard to channel it offensively.";
    }
}
