
package Guild.NightShadeHunters.Armour;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;

public class NightshadeStalkerCuirass {

    private static final Guild REQUIRED_GUILD = Guild.NIGHT_SHADE_HUNTERS;

    private static final String ARMOUR_NAME = "NightshadeStalkerCuirass";
    private static final String DESCRIPTION =
            "NightshadeStalkerCuirass: A dark, close-fitted cuirass made for the Night Shade Hunters. " +
            "Grants defense and bolsters vitality and agility for silent approach and rapid repositioning.";

    private static final int WEIGHT = 8;

    private static final int REQUIRED_VITALITY = 11;
    private static final int REQUIRED_AGILITY = 13;

    private static final int DEFENSE_BONUS = 7;
    private static final int VITALITY_BONUS = 3;
    private static final int AGILITY_BONUS = 4;

    // Track last applied bonuses for correct removal
    private int lastDefBonus = 0;
    private int lastVitBonus = 0;
    private int lastAgiBonus = 0;

    public static NightshadeStalkerCuirass createNightshadeStalkerCuirass(Charecter character) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        if (character.getGuild() != REQUIRED_GUILD)
            throw new IllegalArgumentException("Only Night Shade Hunters members can wear the NightshadeStalkerCuirass.");
        if (character.getVitality() < REQUIRED_VITALITY)
            throw new IllegalArgumentException("Character does not have the required vitality to wear the NightshadeStalkerCuirass.");
        if (character.getAgility() < REQUIRED_AGILITY)
            throw new IllegalArgumentException("Character does not have the required agility to wear the NightshadeStalkerCuirass.");

        return new NightshadeStalkerCuirass();
    }

    public boolean equip(Charecter wearer) {
        if (wearer == null) return false;
        if (wearer.getGuild() != REQUIRED_GUILD) return false;
        if (wearer.getVitality() < REQUIRED_VITALITY) return false;
        if (wearer.getAgility() < REQUIRED_AGILITY) return false;

        lastDefBonus = DEFENSE_BONUS;
        lastVitBonus = VITALITY_BONUS;
        lastAgiBonus = AGILITY_BONUS;

        wearer.setDefense(wearer.getDefense() + lastDefBonus);
        wearer.setVitality(wearer.getVitality() + lastVitBonus);
        wearer.setAgility(wearer.getAgility() + lastAgiBonus);

        return true;
    }

    public boolean unequip(Charecter wearer) {
        if (wearer == null) return false;

        if (lastDefBonus != 0) {
            wearer.setDefense(wearer.getDefense() - lastDefBonus);
        }
        if (lastVitBonus != 0) {
            wearer.setVitality(Math.max(0, wearer.getVitality() - lastVitBonus));
        }
        if (lastAgiBonus != 0) {
            wearer.setAgility(Math.max(0, wearer.getAgility() - lastAgiBonus));
        }

        lastDefBonus = 0;
        lastVitBonus = 0;
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

    public int getRequiredVitality() {
        return REQUIRED_VITALITY;
    }

    public int getRequiredAgility() {
        return REQUIRED_AGILITY;
    }

    public int getDefenseBonus() {
        return DEFENSE_BONUS;
    }

    public int getVitalityBonus() {
        return VITALITY_BONUS;
    }

    public int getAgilityBonus() {
        return AGILITY_BONUS;
    }
}
