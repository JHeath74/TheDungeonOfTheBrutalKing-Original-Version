
package Guild.ObsidianHexCoven.Armour;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;

public class VoidScribeCowl {

    private static final Guild REQUIRED_GUILD = Guild.OBSIDIAN_HEX_COVEN;

    private static final String ARMOUR_NAME = "VoidScribeCowl";
    private static final String DESCRIPTION =
            "VoidScribeCowl: A deep-hooded cowl stitched with ink-black runes for the Obsidian Hex Coven. " +
            "Grants modest protection and strengthens spellcasting by bolstering Intelligence and Wisdom.";

    private static final int WEIGHT = 2;

    private static final int REQUIRED_INTELLIGENCE = 13;
    private static final int REQUIRED_WISDOM = 12;

    private static final int DEFENSE_BONUS = 3;
    private static final int INTELLIGENCE_BONUS = 6;
    private static final int WISDOM_BONUS = 4;

    // Track last applied bonuses for correct removal
    private int lastDefBonus = 0;
    private int lastIntBonus = 0;
    private int lastWisBonus = 0;

    public static VoidScribeCowl createVoidScribeCowl(Charecter character) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        if (character.getGuild() != REQUIRED_GUILD)
            throw new IllegalArgumentException("Only Obsidian Hex Coven members can wear the VoidScribeCowl.");
        if (character.getIntelligence() < REQUIRED_INTELLIGENCE)
            throw new IllegalArgumentException("Character does not have the required intelligence to wear the VoidScribeCowl.");
        if (character.getWisdom() < REQUIRED_WISDOM)
            throw new IllegalArgumentException("Character does not have the required wisdom to wear the VoidScribeCowl.");

        return new VoidScribeCowl();
    }

    public boolean equip(Charecter wearer) {
        if (wearer == null) return false;
        if (wearer.getGuild() != REQUIRED_GUILD) return false;
        if (wearer.getIntelligence() < REQUIRED_INTELLIGENCE) return false;
        if (wearer.getWisdom() < REQUIRED_WISDOM) return false;

        lastDefBonus = DEFENSE_BONUS;
        lastIntBonus = INTELLIGENCE_BONUS;
        lastWisBonus = WISDOM_BONUS;

        wearer.setDefense(wearer.getDefense() + lastDefBonus);
        wearer.setIntelligence(wearer.getIntelligence() + lastIntBonus);
        wearer.setWisdom(wearer.getWisdom() + lastWisBonus);

        return true;
    }

    public boolean unequip(Charecter wearer) {
        if (wearer == null) return false;

        if (lastDefBonus != 0) wearer.setDefense(wearer.getDefense() - lastDefBonus);
        if (lastIntBonus != 0) wearer.setIntelligence(Math.max(0, wearer.getIntelligence() - lastIntBonus));
        if (lastWisBonus != 0) wearer.setWisdom(Math.max(0, wearer.getWisdom() - lastWisBonus));

        lastDefBonus = 0;
        lastIntBonus = 0;
        lastWisBonus = 0;

        return true;
    }

    public String getName() {
        return ARMOUR_NAME;
    }

    public String getDescription() {
        return DESCRIPTION;
    }

    public int getWeight() {
        return WEIGHT;
    }

    public int getRequiredIntelligence() {
        return REQUIRED_INTELLIGENCE;
    }

    public int getRequiredWisdom() {
        return REQUIRED_WISDOM;
    }

    public int getDefenseBonus() {
        return DEFENSE_BONUS;
    }

    public int getIntelligenceBonus() {
        return INTELLIGENCE_BONUS;
    }

    public int getWisdomBonus() {
        return WISDOM_BONUS;
    }
}
