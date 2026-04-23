
package DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Armour;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.SharedData.Guild;

public class PaddedShadowWeave {

    private static final Guild REQUIRED_GUILD = Guild.OBSIDIAN_SHADOW_SYNDICATE;

    private static final String ARMOUR_NAME = "PaddedShadowWeave";
    private static final String DESCRIPTION =
            "PaddedShadowWeave: Quiet padded cloth with hidden quilting favored by the Obsidian Shadow Syndicate. " +
            "Grants light protection without hindering movement.";

    private static final int WEIGHT = 3;

    private static final int REQUIRED_AGILITY = 10;

    private static final int DEFENSE_BONUS = 3;
    private static final int AGILITY_BONUS = 1;

    private int lastDefBonus = 0;
    private int lastAgiBonus = 0;

    public static PaddedShadowWeave createPaddedShadowweaveSuit(Character character) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        if (character.getGuild() != REQUIRED_GUILD)
            throw new IllegalArgumentException("Only Obsidian Shadow Syndicate members can wear the PaddedShadowweaveSuit.");
        if (character.getAgility() < REQUIRED_AGILITY)
            throw new IllegalArgumentException("Character does not have the required agility to wear the PaddedShadowweaveSuit.");

        return new PaddedShadowWeave();
    }

    public boolean equip(Character wearer) {
        if (wearer == null) return false;
        if (wearer.getGuild() != REQUIRED_GUILD) return false;
        if (wearer.getAgility() < REQUIRED_AGILITY) return false;

        lastDefBonus = DEFENSE_BONUS;
        lastAgiBonus = AGILITY_BONUS;

        wearer.setDefense(wearer.getDefense() + lastDefBonus);
        wearer.setAgility(wearer.getAgility() + lastAgiBonus);

        return true;
    }

    public boolean unequip(Character wearer) {
        if (wearer == null) return false;

        if (lastDefBonus != 0) wearer.setDefense(wearer.getDefense() - lastDefBonus);
        if (lastAgiBonus != 0) wearer.setAgility(Math.max(0, wearer.getAgility() - lastAgiBonus));

        lastDefBonus = 0;
        lastAgiBonus = 0;

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

    public int getRequiredAgility() {
        return REQUIRED_AGILITY;
    }

    public int getDefenseBonus() {
        return DEFENSE_BONUS;
    }

    public int getAgilityBonus() {
        return AGILITY_BONUS;
    }
}
