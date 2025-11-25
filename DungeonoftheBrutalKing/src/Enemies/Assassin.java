
package Enemies;

import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.Alignment;
import SharedData.GameSettings;

public class Assassin extends Enemies {

    private int level;
    private final Alignment alignment = Alignment.EVIL;
    private final int alignmentImpact = 2;
    
 //   private static final int BASE_HP = 20;
 //   private static final int HP_PER_LEVEL = 5;
 //   private static final int HP_PER_STRENGTH = 2;
 
  //  int HP = BASE_HP + (level * HP_PER_LEVEL) + (strength * HP_PER_STRENGTH);


    public Assassin() {
        // Calls the superclass constructor to set up the Assassin's attributes:
        // name, level, hitPoints, strength, charisma, agility, intelligence, wisdom, imagePath, isMagicUser, spellStrength
        super(
            "Assassin",                // Enemy name
            5,                         // Level
            30,                        // Hit points
            8,                         // Strength
            5,                         // Charisma
            7,                         // Agility
            6,                         // Intelligence
            3,                         // Wisdom
            GameSettings.MonsterImagePath + "Assassin.png", // Image path
            false,                     // Not a magic user
            0                          // Spell strength
        );
        this.level = 5; // Sets the Assassin's level field
    }

    @Override
    public Alignment getAlignment() {
        return alignment;
    }

    @Override
    public int getAlignmentImpact() {
        return alignmentImpact;
    }

    @Override
    public void takeDamage(int damage) {
        int dodgeChance = 20;
        if (Math.random() * 100 < dodgeChance) {
            MainGameScreen.appendToMessageTextPane(getName() + " dodged the attack!");
            return;
        }
        setHitPoints(getHitPoints() - defend(damage));
        if (getHitPoints() < 0) setHitPoints(0);
        if (isDead()) {
            MainGameScreen.appendToMessageTextPane(getImagePath());
        }
    }

    @Override
    public int defend(int incomingDamage) {
        int baseDefense = 10;
        int agility = getAgility();
        int reductionPercent = (baseDefense + agility) / 2;
        if (reductionPercent > 80) reductionPercent = 80;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " defends and reduces damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    @Override
    public int attack() {
        boolean critical = Math.random() < 0.2;
        int base = (int) ((getStrength() * 1.5) + (getAgility() * 0.5));
        return critical ? base * 2 : base;
    }

    public int getAttackDamage() {
        return attack();
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String getImagePath() {
        if (getHitPoints() < 10) {
            return GameSettings.MonsterImagePath + "Assassin_injured.png";
        }
        return super.getImagePath();
    }

    @Override
    public int getExperienceReward() {
        int base = level * 10;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 5;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    @Override
    public String toString() {
        return "Assassin{" +
                "name='" + getName() + '\'' +
                ", level=" + getLevel() +
                ", hitPoints=" + getHitPoints() +
                ", strength=" + getStrength() +
                ", charisma=" + getCharisma() +
                ", agility=" + getAgility() +
                ", intelligence=" + getIntelligence() +
                ", wisdom=" + getWisdom() +
                ", imagePath='" + getImagePath() + '\'' +
                '}';
    }
}
