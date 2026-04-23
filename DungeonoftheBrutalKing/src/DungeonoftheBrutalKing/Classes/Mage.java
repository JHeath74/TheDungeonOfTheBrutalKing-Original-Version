
// src/Classes/Mage.java
package DungeonoftheBrutalKing.Classes;

import DungeonoftheBrutalKing.SharedData.Stat;
import DungeonoftheBrutalKing.Character;

public class Mage extends Class {

    @SuppressWarnings("unused")
    private static final Character myChar = Character.getInstance();

    public static final String CHAR_CLASS = "Mage";
    public static final String IMAGE = "Mage.webp";

    private static final String DESCRIPTION =
         "A Mage is a master of arcane arts, wielding powerful spells and unraveling the mysteries of ancient lore. " +
         "Mages excel at dealing magical damage, controlling the battlefield, and providing utility through their vast knowledge of magic. " +
         "Their intellect and wisdom allow them to manipulate the forces of the world, making them indispensable in any adventuring party.\n\n" +
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
    private static final int STA_PER_LEVEL = 1;    // Stamina (STA)
    private static final int CHR_PER_LEVEL = 0;    // Charisma (CHR)
    private static final int STR_PER_LEVEL = 0;    // Strength (STR)
    private static final int INTI_PER_LEVEL = 2;   // Intelligence (INTI)
    private static final int WIS_PER_LEVEL = 1;    // Wisdom (WIS)
    private static final int AGI_PER_LEVEL = 0;    // Agility (AGI)
    private static final int VIT_PER_LEVEL = 1;    // Vitality (VIT)

    public Mage() {
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

	@Override
	public boolean isMagicUser() {
		// TODO Auto-generated method stub
		return true;
	}
}
