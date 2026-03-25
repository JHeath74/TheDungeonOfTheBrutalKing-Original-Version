
package Classes;

import SharedData.Stat;
import DungeonoftheBrutalKing.Charecter;

public class Thief extends Classes.Class {

    @SuppressWarnings("unused")
    private static final Charecter myChar = Charecter.getInstance();

    public static final String CHAR_CLASS = "Thief";
    public static final String IMAGE = "Thief.webp";

    private static final String DESCRIPTION =
    	    "A Thief is a cunning opportunist, skilled in stealth, lockpicking, and striking from the shadows. " +
    	    "Masters of infiltration and evasion, Thieves excel at bypassing traps, disarming locks, and exploiting enemy weaknesses. " +
    	    "Their agility allows them to move swiftly and avoid danger, while their intelligence aids in planning heists and solving complex puzzles. " +
    	    "Thieves are invaluable for scouting, sabotage, and turning the tide of battle with precise, unexpected strikes.\n\n" +
    	    "Primary Stat: Agility (AGI)\n" +
    	    "Secondary Stat: Intelligence (INTI)";


    // Class metadata (for UI/guidance/build rules)
    private static final Stat PRIMARY_STAT = Stat.AGI;
    private static final Stat SECONDARY_STAT = Stat.INTI;

    // Optional: apply these to rolled stats at character creation
    private static final int BASE_STA_BONUS = 0;
    private static final int BASE_CHR_BONUS = 0;
    private static final int BASE_STR_BONUS = 0;
    private static final int BASE_INTI_BONUS = 1;
    private static final int BASE_WIS_BONUS = 0;
    private static final int BASE_AGI_BONUS = 2;
    private static final int BASE_VIT_BONUS = 0;

    // Optional: apply these when leveling up (do not re-roll)
    private static final int STA_PER_LEVEL = 1;
    private static final int CHR_PER_LEVEL = 0;
    private static final int STR_PER_LEVEL = 0;
    private static final int INTI_PER_LEVEL = 1;
    private static final int WIS_PER_LEVEL = 0;
    private static final int AGI_PER_LEVEL = 2;
    private static final int VIT_PER_LEVEL = 1;

    public Thief() {
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
    public String getImage() {
        return IMAGE;
    }

    @Override
    public String getClassDescription() {
        return DESCRIPTION;
    }

    // Metadata getters
    public Stat getPrimaryStat() {
        return PRIMARY_STAT;
    }

    public Stat getSecondaryStat() {
        return SECONDARY_STAT;
    }

    public int getBaseStaBonus() {
        return BASE_STA_BONUS;
    }

    public int getBaseChrBonus() {
        return BASE_CHR_BONUS;
    }

    public int getBaseStrBonus() {
        return BASE_STR_BONUS;
    }

    public int getBaseIntiBonus() {
        return BASE_INTI_BONUS;
    }

    public int getBaseWisBonus() {
        return BASE_WIS_BONUS;
    }

    public int getBaseAgiBonus() {
        return BASE_AGI_BONUS;
    }

    public int getBaseVitBonus() {
        return BASE_VIT_BONUS;
    }

    public int getStaPerLevel() {
        return STA_PER_LEVEL;
    }

    public int getChrPerLevel() {
        return CHR_PER_LEVEL;
    }

    public int getStrPerLevel() {
        return STR_PER_LEVEL;
    }

    public int getIntiPerLevel() {
        return INTI_PER_LEVEL;
    }

    public int getWisPerLevel() {
        return WIS_PER_LEVEL;
    }

    public int getAgiPerLevel() {
        return AGI_PER_LEVEL;
    }

    public int getVitPerLevel() {
        return VIT_PER_LEVEL;
    }
}
