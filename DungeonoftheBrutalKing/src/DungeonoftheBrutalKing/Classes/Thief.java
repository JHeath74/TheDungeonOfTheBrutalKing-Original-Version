
// src/Classes/Thief.java
package DungeonoftheBrutalKing.Classes;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.SharedData.Stat;

public class Thief extends Class {

    private static final String CHAR_CLASS = "Thief";
    private static final String IMAGE = "Thief.webp";

    private static final String DESCRIPTION =
        "A Thief is a master of stealth, deception, and precision. Skilled in infiltration, lockpicking, and striking from the shadows, " +
        "Thieves excel at bypassing defenses and exploiting enemy weaknesses. Their agility allows them to move swiftly and avoid danger, " +
        "while their intelligence aids in solving complex puzzles and planning daring heists. Thieves are invaluable for scouting, sabotage, " +
        "and turning the tide of battle with a well-placed strike.\n\n" +
        "Primary Stat: Agility (AGI)\n" +
        "Secondary Stat: Intelligence (INTI)";

    private static final Stat PRIMARY_STAT = Stat.AGI;
    private static final Stat SECONDARY_STAT = Stat.INTI;

    // Base stat bonuses at character creation
    private static final int BASE_STA_BONUS = 0;   // Stamina (STA)
    private static final int BASE_CHR_BONUS = 0;   // Charisma (CHR)
    private static final int BASE_STR_BONUS = 0;   // Strength (STR)
    private static final int BASE_INTI_BONUS = 1;  // Intelligence (INTI)
    private static final int BASE_WIS_BONUS = 0;   // Wisdom (WIS)
    private static final int BASE_AGI_BONUS = 2;   // Agility (AGI)
    private static final int BASE_VIT_BONUS = 0;   // Vitality (VIT)

    // Stat increases per level
    private static final int STA_PER_LEVEL = 1;    // Stamina (STA)
    private static final int CHR_PER_LEVEL = 0;    // Charisma (CHR)
    private static final int STR_PER_LEVEL = 1;    // Strength (STR)
    private static final int INTI_PER_LEVEL = 0;   // Intelligence (INTI)
    private static final int WIS_PER_LEVEL = 0;    // Wisdom (WIS)
    private static final int AGI_PER_LEVEL = 2;    // Agility (AGI)
    private static final int VIT_PER_LEVEL = 1;    // Vitality (VIT)

    public Thief() {
        this.charClass = CHAR_CLASS;
        this.classDescription = DESCRIPTION;
    }

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
    
    @Override
    public boolean isMagicUser() {
        return false;
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
