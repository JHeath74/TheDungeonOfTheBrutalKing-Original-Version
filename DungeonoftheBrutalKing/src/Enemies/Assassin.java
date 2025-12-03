
// src/Enemies/Assassin.java
package Enemies;

import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.Alignment;
import SharedData.GameSettings;

/**
 * Represents an Assassin enemy with vitality stat for hit points.
 */
public class Assassin extends Enemies {

    private int level;
    private final int vitality; // Vitality stat used for hit points
    private final Alignment alignment = Alignment.EVIL;
    private final int alignmentImpact = 2;

    /**
     * Default constructor. Initializes Assassin with preset stats.
     */
    public Assassin() {
        this(5, 8, 5, 7, 6, 3, 6); // Default vitality = 6
    }

    /**
     * Constructs an Assassin with specified stats.
     * Hit points are determined by level and vitality only.
     */
    public Assassin(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality) {
    	super(
    		    "Assassin",
    		    level,
    		    (level * 5) + (vitality * 7), // HP uses only level and vitality
    		    strength,
    		    charisma,
    		    agility,
    		    intelligence,
    		    wisdom,
    		    GameSettings.MonsterImagePath + "Assassin.png",
    		    false
    		);
        this.level = level;
        this.vitality = vitality;
    }

    @Override
    public Alignment getAlignment() {
        return alignment;
    }

    @Override
    public int getAlignmentImpact() {
        int offset = (int) (Math.random() * ((level / 5) * 2 + 1)) - (level / 5);
        return -(level + offset);
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

    public int getVitality() {
        return vitality;
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
                ", vitality=" + getVitality() +
                ", imagePath='" + getImagePath() + '\'' +
                '}';
    }
}
