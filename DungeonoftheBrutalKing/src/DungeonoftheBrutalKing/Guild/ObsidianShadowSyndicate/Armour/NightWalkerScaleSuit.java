package DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Armour;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.SharedData.Guild;

public class NightWalkerScaleSuit {

    private static final Guild REQUIRED_GUILD = Guild.OBSIDIAN_SHADOW_SYNDICATE;

    private static final String ARMOUR_NAME = "NightwalkerScaleSuit";
    private static final String DESCRIPTION =
            "NightwalkerScaleSuit: Overlapping dark scales disperse glancing blows and knife strikes while maintaining flexibility. " +
            "Built for night raids and tight corridors.";

    private static final int WEIGHT = 6;

    private static final int REQUIRED_AGILITY = 14;

    private static final int DEFENSE_BONUS = 7;
    private static final int AGILITY_BONUS = 2;

    private int lastDefBonus = 0;
    private int lastAgiBonus = 0;

    public static NightWalkerScaleSuit createNightwalkerScaleSuit(Character character) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        if (character.getGuild() != REQUIRED_GUILD)
            throw new IllegalArgumentException("Only Obsidian Shadow Syndicate members can wear the NightwalkerScaleSuit.");
        if (character.getAgility() < REQUIRED_AGILITY)
            throw new IllegalArgumentException("Character does not have the required agility to wear the NightwalkerScaleSuit.");

        return new NightWalkerScaleSuit();
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