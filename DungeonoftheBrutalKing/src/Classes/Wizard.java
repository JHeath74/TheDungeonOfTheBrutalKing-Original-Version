
// src/Classes/Wizard.java
package Classes;

import DungeonoftheBrutalKing.Charecter;

public class Wizard extends Class {

    @SuppressWarnings("unused")
    private static final Charecter myChar = Charecter.getInstance();

    public static final String CHAR_CLASS = "Wizard";
    public static final String IMAGE = "Wizard.webp";

    private static final String DESCRIPTION =
            "A " + CHAR_CLASS + " is a master of arcane magic, shaping the fabric of reality through sheer intellect and rigorous study. "
                    + "With their spellbook as a constant companion, they wield a vast array of powerful spells, excelling in versatility and utility. "
                    + CHAR_CLASS + " thrive in roles requiring control, damage, or problem-solving, prowess and boosting their effectiveness in arcane knowledge and skill checks. "
                    + "While physically frail, their mental acuity and deep magical expertise make them an indispensable asset to any adventuring party."
                    + "\n\n"
                    + "Intelligence (INTI) followed by Wisdom (WIS) are the most important stats for a " + CHAR_CLASS + ".";

    public enum Stat {
        STA, CHR, STR, INTI, WIS, AGI, VIT
    }

    // Class metadata
    private static final Stat PRIMARY_STAT = Stat.INTI;
    private static final Stat SECONDARY_STAT = Stat.WIS;

    // Optional: apply these to rolled stats at character creation
    private static final int BASE_STA_BONUS = 0;
    private static final int BASE_CHR_BONUS = 0;
    private static final int BASE_STR_BONUS = 0;
    private static final int BASE_INTI_BONUS = 2;
    private static final int BASE_WIS_BONUS = 1;
    private static final int BASE_AGI_BONUS = 0;
    private static final int BASE_VIT_BONUS = 0;

    // Optional: apply these when leveling up (do not re-roll)
    private static final int STA_PER_LEVEL = 0;
    private static final int CHR_PER_LEVEL = 0;
    private static final int STR_PER_LEVEL = 0;
    private static final int INTI_PER_LEVEL = 2;
    private static final int WIS_PER_LEVEL = 1;
    private static final int AGI_PER_LEVEL = 0;
    private static final int VIT_PER_LEVEL = 1;

    public Wizard() {
        this.charClass = CHAR_CLASS;
        this.classDescription = DESCRIPTION;
    }

    // Kept for compatibility with existing call sites
    public static String ClassDescription() {
        return DESCRIPTION;
    }

    @Override
    public String getCharClass() {
        return CHAR_CLASS;
    }

    public String getImage() {
        return IMAGE;
    }

    @Override
    public String getClassDescription() {
        return DESCRIPTION;
    }

    // Metadata getters
    public Stat getPrimaryStat() { return PRIMARY_STAT; }
    public Stat getSecondaryStat() { return SECONDARY_STAT; }

    public int getBaseStaBonus() { return BASE_STA_BONUS; }
    public int getBaseChrBonus() { return BASE_CHR_BONUS; }
    public int getBaseStrBonus() { return BASE_STR_BONUS; }
    public int getBaseIntiBonus() { return BASE_INTI_BONUS; }
    public int getBaseWisBonus() { return BASE_WIS_BONUS; }
    public int getBaseAgiBonus() { return BASE_AGI_BONUS; }
    public int getBaseVitBonus() { return BASE_VIT_BONUS; }

    public int getStaPerLevel() { return STA_PER_LEVEL; }
    public int getChrPerLevel() { return CHR_PER_LEVEL; }
    public int getStrPerLevel() { return STR_PER_LEVEL; }
    public int getIntiPerLevel() { return INTI_PER_LEVEL; }
    public int getWisPerLevel() { return WIS_PER_LEVEL; }
    public int getAgiPerLevel() { return AGI_PER_LEVEL; }
    public int getVitPerLevel() { return VIT_PER_LEVEL; }
}
