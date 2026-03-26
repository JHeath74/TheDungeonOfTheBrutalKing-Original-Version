
// src/Classes/Cleric.java
package DungeonoftheBrutalKing.Classes;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.SharedData.Stat;

public class Cleric extends Class {

    @SuppressWarnings("unused")
    private static final Charecter myChar = Charecter.getInstance();

    public static final String CHAR_CLASS = "Cleric";

    private static final String DESCRIPTION =
         "A Cleric is a devoted champion of the divine, wielding both martial prowess and sacred magic. " +
         "Clerics heal wounds, cure ailments, and protect allies with blessings, while also calling down holy wrath upon their foes. " +
         "Their faith grants them power over life and death, making them essential in any adventuring party.\n\n" +
         "Primary Stat: Wisdom (WIS)\n" +
         "Secondary Stat: Intelligence (INTI)";

    // Class metadata (for UI/guidance/build rules)
    private static final Stat PRIMARY_STAT = Stat.WIS;
    private static final Stat SECONDARY_STAT = Stat.INTI;

    // --- Base stat bonuses at character creation ---
    private static final int BASE_STA_BONUS = 1;   // Stamina (STA)
    private static final int BASE_CHR_BONUS = 0;   // Charisma (CHR)
    private static final int BASE_STR_BONUS = 0;   // Strength (STR)
    private static final int BASE_INTI_BONUS = 1;  // Intelligence (INTI)
    private static final int BASE_WIS_BONUS = 2;   // Wisdom (WIS)
    private static final int BASE_AGI_BONUS = 0;   // Agility (AGI)
    private static final int BASE_VIT_BONUS = 1;   // Vitality (VIT)

    // --- Stat increases per level ---
    private static final int STA_PER_LEVEL = 1;    // Stamina (STA)
    private static final int CHR_PER_LEVEL = 0;    // Charisma (CHR)
    private static final int STR_PER_LEVEL = 0;    // Strength (STR)
    private static final int INTI_PER_LEVEL = 1;   // Intelligence (INTI)
    private static final int WIS_PER_LEVEL = 2;    // Wisdom (WIS)
    private static final int AGI_PER_LEVEL = 0;    // Agility (AGI)
    private static final int VIT_PER_LEVEL = 1;    // Vitality (VIT)

    public Cleric() {
        this.charClass = CHAR_CLASS;
        this.classDescription = DESCRIPTION;
    }

    // Compatibility with existing call sites
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
