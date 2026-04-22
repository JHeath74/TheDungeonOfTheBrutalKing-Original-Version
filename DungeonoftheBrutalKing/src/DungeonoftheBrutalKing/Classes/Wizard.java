
// src/Classes/Wizard.java
package DungeonoftheBrutalKing.Classes;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.SharedData.Stat;

public class Wizard extends Class {

    private static final String CHAR_CLASS = "Wizard";
    private static final String IMAGE = "Wizard.webp";

    private static final String DESCRIPTION =
        "A Wizard is a master of arcane magic, wielding spells through intellect, study, and deep understanding of the mystical arts. " +
        "Wizards command a vast array of powerful spells, excelling in versatility, control, and problem-solving. " +
        "Their intelligence allows them to unravel magical mysteries and adapt to any challenge, while their wisdom guides their decisions and enhances their spellcasting. " +
        "Though physically frail, Wizards are indispensable for their magical prowess and strategic insight.\n\n" +
        "Primary Stat: Intelligence (INTI)\n" +
        "Secondary Stat: Wisdom (WIS)";

    private static final Stat PRIMARY_STAT = Stat.INTI;
    private static final Stat SECONDARY_STAT = Stat.WIS;

    // Base stat bonuses at character creation
    private static final int BASE_STA_BONUS = 0;   // Stamina (STA)
    private static final int BASE_CHR_BONUS = 0;   // Charisma (CHR)
    private static final int BASE_STR_BONUS = 0;   // Strength (STR)
    private static final int BASE_INTI_BONUS = 2;  // Intelligence (INTI)
    private static final int BASE_WIS_BONUS = 1;   // Wisdom (WIS)
    private static final int BASE_AGI_BONUS = 0;   // Agility (AGI)
    private static final int BASE_VIT_BONUS = 0;   // Vitality (VIT)

    // Stat increases per level
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
        return true;
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
