
// src/Classes/Warrior.java
package DungeonoftheBrutalKing.Classes;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.SharedData.Stat;

public class Warrior extends Class {

    private static final String CHAR_CLASS = "Warrior";
    private static final String IMAGE = "Warrior.webp";

    private static final String DESCRIPTION =
        "A Warrior is a battle-hardened champion, excelling in the art of combat and mastery of weapons and armor. " +
        "Warriors lead the charge, withstand devastating blows, and inspire allies through sheer strength and resilience. " +
        "Their strength empowers their attacks, while their stamina allows them to endure the harshest battles, making them formidable opponents and stalwart defenders.\n\n" +
        "Primary Stat: Strength (STR)\n" +
        "Secondary Stat: Stamina (STA)";

    private static final Stat PRIMARY_STAT = Stat.STR;
    private static final Stat SECONDARY_STAT = Stat.STA;

    // Base stat bonuses at character creation
    private static final int BASE_STA_BONUS = 2;   // Stamina (STA)
    private static final int BASE_CHR_BONUS = 0;   // Charisma (CHR)
    private static final int BASE_STR_BONUS = 2;   // Strength (STR)
    private static final int BASE_INTI_BONUS = 0;  // Intelligence (INTI)
    private static final int BASE_WIS_BONUS = 0;   // Wisdom (WIS)
    private static final int BASE_AGI_BONUS = 0;   // Agility (AGI)
    private static final int BASE_VIT_BONUS = 1;   // Vitality (VIT)

    // Stat increases per level
    private static final int STA_PER_LEVEL = 2;    // Stamina (STA)
    private static final int CHR_PER_LEVEL = 0;    // Charisma (CHR)
    private static final int STR_PER_LEVEL = 2;    // Strength (STR)
    private static final int INTI_PER_LEVEL = 0;   // Intelligence (INTI)
    private static final int WIS_PER_LEVEL = 0;    // Wisdom (WIS)
    private static final int AGI_PER_LEVEL = 0;    // Agility (AGI)
    private static final int VIT_PER_LEVEL = 1;    // Vitality (VIT)

    public Warrior() {
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
