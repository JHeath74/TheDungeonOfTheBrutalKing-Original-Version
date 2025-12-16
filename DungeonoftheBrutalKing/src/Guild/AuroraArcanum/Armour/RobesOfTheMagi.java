
package Guild.AuroraArcanum.Armour;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;
import Armour.ArmourManager;

public class RobesOfTheMagi extends ArmourManager {

    private static final int REQUIRED_INTELLIGENCE = 20;
    private static final int INTELLIGENCE_BONUS = 2;
    private static final int SPELL_RESISTANCE_BONUS = 10; // percent
    private static final int WEIGHT = 2;

    private static final Guild GUILDname = Guild.AURORA_ARCNUM;
    private static final GuildType GUILDtype = GuildType.WIZARD;

    private boolean isEquipped = false;

    public RobesOfTheMagi(int requiredIntelligence, int spellResistance, String effect, int weight) {
        super("Robes of the Magi", requiredIntelligence, spellResistance, effect, weight);
    }

    public static RobesOfTheMagi createRobes(Charecter character, int spellResistance, String effect) {
        int intelligence = character.getIntelligence();
        if (intelligence >= REQUIRED_INTELLIGENCE) {
            return new RobesOfTheMagi(REQUIRED_INTELLIGENCE, spellResistance, effect, WEIGHT);
        }
        throw new IllegalArgumentException("Character does not have the required intelligence to wear the Robes of the Magi.");
    }

    public void equip(Charecter wearer) {
        if (!isEquipped) {
            wearer.setIntelligence(wearer.getIntelligence() + INTELLIGENCE_BONUS);
            wearer.setSpellResistance(wearer.getSpellResistance() + SPELL_RESISTANCE_BONUS);
            isEquipped = true;
        }
    }

    public void unequip(Charecter wearer) {
        if (isEquipped) {
            wearer.setIntelligence(wearer.getIntelligence() - INTELLIGENCE_BONUS);
            wearer.setSpellResistance(wearer.getSpellResistance() - SPELL_RESISTANCE_BONUS);
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
    public double getDefense() {
        return (double) SPELL_RESISTANCE_BONUS;
    }

    @Override
    public void setEffect(String effect) {
        super.setEffect(effect);
    }

    @Override
    public String getDescription() {
        return "Robes of the Magi: Flowing enchanted robes that boost spell resistance and enhance magical focus. Embroidered with runes or sigils that shimmer when spells are cast.";
    }
}
