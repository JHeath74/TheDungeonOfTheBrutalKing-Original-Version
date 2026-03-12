
package Guild.NightShadeHunters.Armour;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;

public class DuskwoodTrackerMail {

    private static final Guild REQUIRED_GUILD = Guild.NIGHT_SHADE_HUNTERS;

    private static final String ARMOUR_NAME = "DuskwoodTrackerMail";
    private static final String DESCRIPTION =
            "DuskwoodTrackerMail: Quiet, bark-toned mail reinforced with leather layers for the Night Shade Hunters. " +
            "Grants defense and bolsters vitality and stamina for long pursuits and steady aim.";

    private static final int WEIGHT = 7;

    private static final int REQUIRED_VITALITY = 10;
    private static final int REQUIRED_STAMINA = 12;

    private static final int DEFENSE_BONUS = 6;
    private static final int VITALITY_BONUS = 3;
    private static final int STAMINA_BONUS = 4;

    // Track last applied bonuses for correct removal
    private int lastDefBonus = 0;
    private int lastVitBonus = 0;
    private int lastStaBonus = 0;

    public static DuskwoodTrackerMail createDuskwoodTrackerMail(Charecter character) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        if (character.getGuild() != REQUIRED_GUILD)
            throw new IllegalArgumentException("Only Night Shade Hunters members can wear the DuskwoodTrackerMail.");
        if (character.getVitality() < REQUIRED_VITALITY)
            throw new IllegalArgumentException("Character does not have the required vitality to wear the DuskwoodTrackerMail.");
        if (character.getStamina() < REQUIRED_STAMINA)
            throw new IllegalArgumentException("Character does not have the required stamina to wear the DuskwoodTrackerMail.");

        return new DuskwoodTrackerMail();
    }

    public boolean equip(Charecter wearer) {
        if (wearer == null) return false;
        if (wearer.getGuild() != REQUIRED_GUILD) return false;
        if (wearer.getVitality() < REQUIRED_VITALITY) return false;
        if (wearer.getStamina() < REQUIRED_STAMINA) return false;

        lastDefBonus = DEFENSE_BONUS;
        lastVitBonus = VITALITY_BONUS;
        lastStaBonus = STAMINA_BONUS;

        wearer.setDefense(wearer.getDefense() + lastDefBonus);
        wearer.setVitality(wearer.getVitality() + lastVitBonus);
        wearer.setStamina(wearer.getStamina() + lastStaBonus);

        return true;
    }

    public boolean unequip(Charecter wearer) {
        if (wearer == null) return false;

        if (lastDefBonus != 0) wearer.setDefense(wearer.getDefense() - lastDefBonus);
        if (lastVitBonus != 0) wearer.setVitality(Math.max(0, wearer.getVitality() - lastVitBonus));
        if (lastStaBonus != 0) wearer.setStamina(Math.max(0, wearer.getStamina() - lastStaBonus));

        lastDefBonus = 0;
        lastVitBonus = 0;
        lastStaBonus = 0;

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

    public int getRequiredStamina() {
        return REQUIRED_STAMINA;
    }

    public int getDefenseBonus() {
        return DEFENSE_BONUS;
    }

    public int getVitalityBonus() {
        return VITALITY_BONUS;
    }

    public int getStaminaBonus() {
        return STAMINA_BONUS;
    }
}
