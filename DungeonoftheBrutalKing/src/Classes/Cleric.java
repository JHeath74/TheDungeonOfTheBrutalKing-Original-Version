
// src/Classes/Cleric.java
package Classes;

import DungeonoftheBrutalKing.Charecter;

public class Cleric extends Class {

    @SuppressWarnings("unused")
    private static final Charecter myChar = Charecter.getInstance();

    public static final String CHAR_CLASS = "Cleric";

    private static final String DESCRIPTION =
            CHAR_CLASS + " are versatile figures, both capable in combat and skilled in the use of "
                    + "divine magic. " + CHAR_CLASS + " are powerful healers due to the large \n number of healing and "
                    + " curative magics available to them. \n\nWith divinely-granted abilities over life or death, they are \n"
                    + " also able to repel or control undead creatures."
                    + "    \n\nWisdom (WIS) is your most important stat,\n followed closely by INTELLIGENCE (INTI).";

    public enum Stat {
        STA, CHR, STR, INTI, WIS, AGI, VIT
    }

    // Class metadata (for UI/guidance/build rules)
    private static final Stat PRIMARY_STAT = Stat.WIS;
    private static final Stat SECONDARY_STAT = Stat.INTI;

    // Optional: class bonuses applied to rolled stats at creation
    private static final int BASE_STA_BONUS = 1;
    private static final int BASE_CHR_BONUS = 0;
    private static final int BASE_STR_BONUS = 0;
    private static final int BASE_INTI_BONUS = 1;
    private static final int BASE_WIS_BONUS = 2;
    private static final int BASE_AGI_BONUS = 0;
    private static final int BASE_VIT_BONUS = 0;

    // Optional: per-level growth (apply when leveling up, not on creation rolls)
    private static final int STA_PER_LEVEL = 1;
    private static final int CHR_PER_LEVEL = 0;
    private static final int STR_PER_LEVEL = 0;
    private static final int INTI_PER_LEVEL = 1;
    private static final int WIS_PER_LEVEL = 2;
    private static final int AGI_PER_LEVEL = 0;
    private static final int VIT_PER_LEVEL = 1;

    public Cleric() {
        this.charClass = CHAR_CLASS;
        this.classDescription = DESCRIPTION;
    }

    // Keep compatibility with existing call sites
    public static String ClassDescription() {
        return DESCRIPTION;
    }

    @Override
    public String getCharClass() {
        return CHAR_CLASS;
    }

    @Override
    public String getClassDescription() {
        return DESCRIPTION;
    }

    // Metadata getters
    public Stat getPrimaryStat() { return PRIMARY_STAT; }
    public Stat getSecondaryStat() { return SECONDARY_STAT; }

    // Use these from your character-creation code to modify rolled stats.
    public int getBaseStaBonus() { return BASE_STA_BONUS; }
    public int getBaseChrBonus() { return BASE_CHR_BONUS; }
    public int getBaseStrBonus() { return BASE_STR_BONUS; }
    public int getBaseIntiBonus() { return BASE_INTI_BONUS; }
    public int getBaseWisBonus() { return BASE_WIS_BONUS; }
    public int getBaseAgiBonus() { return BASE_AGI_BONUS; }
    public int getBaseVitBonus() { return BASE_VIT_BONUS; }

    // Use these from your level-up code (do not re-roll stats).
    public int getStaPerLevel() { return STA_PER_LEVEL; }
    public int getChrPerLevel() { return CHR_PER_LEVEL; }
    public int getStrPerLevel() { return STR_PER_LEVEL; }
    public int getIntiPerLevel() { return INTI_PER_LEVEL; }
    public int getWisPerLevel() { return WIS_PER_LEVEL; }
    public int getAgiPerLevel() { return AGI_PER_LEVEL; }
    public int getVitPerLevel() { return VIT_PER_LEVEL; }
}
