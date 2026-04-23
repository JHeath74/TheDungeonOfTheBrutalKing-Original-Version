package DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Armour;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.SharedData.Guild;

public class GuildsVaultmailEnsemble {

    private static final Guild REQUIRED_GUILD = Guild.OBSIDIAN_SHADOW_SYNDICATE;

    private static final String ARMOUR_NAME = "GuildsVaultmailEnsemble";
    private static final String DESCRIPTION =
            "GuildsVaultmailEnsemble: Fine mail underlayer beneath a tailored coat, designed for burglary in hostile places. " +
            "Heavier than leathers, but markedly safer in close combat.";

    private static final int WEIGHT = 8;

    private static final int REQUIRED_AGILITY = 15;
    private static final int REQUIRED_CHARISMA = 10;

    private static final int DEFENSE_BONUS = 9;
    private static final int AGILITY_BONUS = 1;
    private static final int CHARISMA_BONUS = 1;

    private int lastDefBonus = 0;
    private int lastAgiBonus = 0;
    private int lastChrBonus = 0;

    public static GuildsVaultmailEnsemble createGuildsVaultmailEnsemble(Character character) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        if (character.getGuild() != REQUIRED_GUILD)
            throw new IllegalArgumentException("Only Obsidian Shadow Syndicate members can wear the GuildsVaultmailEnsemble.");
        if (character.getAgility() < REQUIRED_AGILITY)
            throw new IllegalArgumentException("Character does not have the required agility to wear the GuildsVaultmailEnsemble.");
        if (character.getCharisma() < REQUIRED_CHARISMA)
            throw new IllegalArgumentException("Character does not have the required charisma to wear the GuildsVaultmailEnsemble.");

        return new GuildsVaultmailEnsemble();
    }

    public boolean equip(Character wearer) {
        if (wearer == null) return false;
        if (wearer.getGuild() != REQUIRED_GUILD) return false;
        if (wearer.getAgility() < REQUIRED_AGILITY) return false;
        if (wearer.getCharisma() < REQUIRED_CHARISMA) return false;

        lastDefBonus = DEFENSE_BONUS;
        lastAgiBonus = AGILITY_BONUS;
        lastChrBonus = CHARISMA_BONUS;

        wearer.setDefense(wearer.getDefense() + lastDefBonus);
        wearer.setAgility(wearer.getAgility() + lastAgiBonus);
        wearer.setCharisma(wearer.getCharisma() + lastChrBonus);

        return true;
    }

    public boolean unequip(Character wearer) {
        if (wearer == null) return false;

        if (lastDefBonus != 0) wearer.setDefense(wearer.getDefense() - lastDefBonus);
        if (lastAgiBonus != 0) wearer.setAgility(Math.max(0, wearer.getAgility() - lastAgiBonus));
        if (lastChrBonus != 0) wearer.setCharisma(Math.max(0, wearer.getCharisma() - lastChrBonus));

        lastDefBonus = 0;
        lastAgiBonus = 0;
        lastChrBonus = 0;

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

    public int getRequiredCharisma() {
        return REQUIRED_CHARISMA;
    }

    public int getDefenseBonus() {
        return DEFENSE_BONUS;
    }

    public int getAgilityBonus() {
        return AGILITY_BONUS;
    }

    public int getCharismaBonus() {
        return CHARISMA_BONUS;
    }
}