package DungeonoftheBrutalKing.Classes;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.SharedData.Stat;

/**
 * Represents the Wizard class in the game.
 * <p>
 * Wizards are masters of arcane magic, wielding spells through intellect, study, and deep understanding of the mystical arts.
 * They command a vast array of powerful spells, excelling in versatility, control, and problem-solving.
 * Their intelligence allows them to unravel magical mysteries and adapt to any challenge, while their wisdom guides their decisions and enhances their spellcasting.
 * Though physically frail, Wizards are indispensable for their magical prowess and strategic insight.
 */
public class Wizard extends Class {

    @SuppressWarnings("unused")
    private static final Character myChar = Character.getInstance();

    public static final String CHAR_CLASS = "Wizard";
    private static final String IMAGE = "/images/Wizard.png";

    private static final String DESCRIPTION =
        "A Wizard is a master of arcane magic, wielding spells through intellect, study, and deep understanding of the mystical arts. " +
        "Wizards command a vast array of powerful spells, excelling in versatility, control, and problem-solving. " +
        "Their intelligence allows them to unravel magical mysteries and adapt to any challenge, while their wisdom guides their decisions and enhances their spellcasting. " +
        "Though physically frail, Wizards are indispensable for their magical prowess and strategic insight.\n\n" +
        "Primary Stat: Intelligence (INTI)\n" +
        "Secondary Stat: Wisdom (WIS)";

    // Class metadata (for UI/guidance/build rules)
    private static final Stat PRIMARY_STAT = Stat.INTI;
    private static final Stat SECONDARY_STAT = Stat.WIS;

    // --- Base stat bonuses at character creation ---
    private static final int BASE_STA_BONUS = 0;   // Stamina (STA)
    private static final int BASE_CHR_BONUS = 0;   // Charisma (CHR)
    private static final int BASE_STR_BONUS = 0;   // Strength (STR)
    private static final int BASE_INTI_BONUS = 2;  // Intelligence (INTI)
    private static final int BASE_WIS_BONUS = 1;   // Wisdom (WIS)
    private static final int BASE_AGI_BONUS = 0;   // Agility (AGI)
    private static final int BASE_VIT_BONUS = 0;   // Vitality (VIT)

    // --- Stat increases per level ---
    private static final int STA_PER_LEVEL = 0;    // Stamina (STA)
    private static final int CHR_PER_LEVEL = 0;    // Charisma (CHR)
    private static final int STR_PER_LEVEL = 0;    // Strength (STR)
    private static final int INTI_PER_LEVEL = 2;   // Intelligence (INTI)
    private static final int WIS_PER_LEVEL = 1;    // Wisdom (WIS)
    private static final int AGI_PER_LEVEL = 0;    // Agility (AGI)
    private static final int VIT_PER_LEVEL = 1;    // Vitality (VIT)

    public Wizard() {
        this.charClass = CHAR_CLASS;
        this.classDescription = DESCRIPTION;
    }

    /**
     * Returns the class description.
     */
    public static String ClassDescription() {
        return DESCRIPTION;
    }

    /** @return the name of the character class */
    @Override
    public String getCharClass() {
        return CHAR_CLASS;
    }

    /**
     * Returns the full resource path for the Wizard image.
     */
    @Override
    public String getImage() {
        return IMAGE;
    }

    /** @return the class description */
    @Override
    public String getClassDescription() {
        return DESCRIPTION;
    }

    /**
     * Returns whether the Wizard is a magic user.
     * @return true, as Wizards use arcane magic.
     */
    @Override
    public boolean isMagicUser() {
        return true;
    }

    /** @return the primary stat for Wizard */
    @Override
    public Stat getPrimaryStat() { return PRIMARY_STAT; }

    /** @return the secondary stat for Wizard */
    @Override
    public Stat getSecondaryStat() { return SECONDARY_STAT; }

    // Base stat bonus getters
    public int getBaseStaBonus() { return BASE_STA_BONUS; }
    public int getBaseChrBonus() { return BASE_CHR_BONUS; }
    public int getBaseStrBonus() { return BASE_STR_BONUS; }
    public int getBaseIntiBonus() { return BASE_INTI_BONUS; }
    public int getBaseWisBonus() { return BASE_WIS_BONUS; }
    public int getBaseAgiBonus() { return BASE_AGI_BONUS; }
    public int getBaseVitBonus() { return BASE_VIT_BONUS; }

    // Per-level stat increase getters
    public int getStaPerLevel() { return STA_PER_LEVEL; }
    public int getChrPerLevel() { return CHR_PER_LEVEL; }
    public int getStrPerLevel() { return STR_PER_LEVEL; }
    public int getIntiPerLevel() { return INTI_PER_LEVEL; }
    public int getWisPerLevel() { return WIS_PER_LEVEL; }
    public int getAgiPerLevel() { return AGI_PER_LEVEL; }
    public int getVitPerLevel() { return VIT_PER_LEVEL; }
}
