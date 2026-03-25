
package Classes;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Stat;

public class Bard extends Class {

    private static final String CHAR_CLASS = "Bard";
    private static final String IMAGE = "Bard.webp";

    private static final String DESCRIPTION =
        "A Bard is a charismatic performer and spellcaster, weaving music, poetry, and magic to inspire allies and confound enemies. " +
        "Masters of versatility, Bards can heal, support, or manipulate the flow of battle with their enchanting presence and quick wit. " +
        "Their charisma fuels their magical abilities, while their intelligence allows them to adapt to any challenge and master a wide array of skills. " +
        "Bards are invaluable for their creativity, resourcefulness, and ability to turn the tide of any encounter.\n\n" +
        "Primary Stat: Charisma (CHR)\n" +
        "Secondary Stat: Intelligence (INTI)";

    private static final Stat PRIMARY_STAT = Stat.CHR;
    private static final Stat SECONDARY_STAT = Stat.INTI;


 // Base stat bonuses at character creation
 private static final int BASE_STA_BONUS = 0;   // Stamina (STA)
 private static final int BASE_CHR_BONUS = 5;   // Charisma (CHR)
 private static final int BASE_STR_BONUS = 0;   // Strength (STR)
 private static final int BASE_INTI_BONUS = 5;  // Intelligence (INTI)
 private static final int BASE_WIS_BONUS = 0;   // Wisdom (WIS)
 private static final int BASE_AGI_BONUS = 0;   // Agility (AGI)
 private static final int BASE_VIT_BONUS = 0;   // Vitality (VIT)

 // Stat increases per level
 private static final int STA_PER_LEVEL = 1;    // Stamina (STA)
 private static final int CHR_PER_LEVEL = 0;    // Charisma (CHR)
 private static final int STR_PER_LEVEL = 0;    // Strength (STR)
 private static final int INTI_PER_LEVEL = 1;   // Intelligence (INTI)
 private static final int WIS_PER_LEVEL = 1;    // Wisdom (WIS)
 private static final int AGI_PER_LEVEL = 0;    // Agility (AGI)
 private static final int VIT_PER_LEVEL = 1;    // Vitality (VIT)


    public Bard() {
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
