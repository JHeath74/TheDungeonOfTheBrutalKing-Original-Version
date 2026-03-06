
// `src/Classes/Hunter.java`
package Classes;

import DungeonoftheBrutalKing.Charecter;

public class Hunter extends Class {

    @SuppressWarnings("unused")
    private static final Charecter myChar = Charecter.getInstance();

    public static final String CHAR_CLASS = "Hunter";

    private static final String DESCRIPTION =
            CHAR_CLASS + " finds its place as a bulwark between civilization and the terrors of the wilderness.\n"
                    + "Despite its namesake, tracking mere game is only a minor task in the hunter's repertoire of expertise.\n"
                    + "Its specialized battle techniques can fell rampaging ogres to hordes of orcs."
                    + "\n\nCharisma (CHR) is an important skill for a " + CHAR_CLASS;

    public enum Stat {
        STA, CHR, STR, INTI, WIS, AGI, VIT
    }

    // Class metadata (for UI/guidance/build rules)
    private static final Stat PRIMARY_STAT = Stat.AGI;
    private static final Stat SECONDARY_STAT = Stat.STA;

    // Optional: apply these to rolled stats at character creation
    private static final int BASE_STA_BONUS = 1;
    private static final int BASE_CHR_BONUS = 2;
    private static final int BASE_STR_BONUS = 1;
    private static final int BASE_INTI_BONUS = 0;
    private static final int BASE_WIS_BONUS = 0;
    private static final int BASE_AGI_BONUS = 2;
    private static final int BASE_VIT_BONUS = 0;

    // Optional: apply these when leveling up (do not re-roll)
    private static final int STA_PER_LEVEL = 1;
    private static final int CHR_PER_LEVEL = 1;
    private static final int STR_PER_LEVEL = 0;
    private static final int INTI_PER_LEVEL = 0;
    private static final int WIS_PER_LEVEL = 0;
    private static final int AGI_PER_LEVEL = 1;
    private static final int VIT_PER_LEVEL = 1;

    public Hunter() {
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

    @Override
    public String getClassDescription() {
        return DESCRIPTION;
    }

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
