
// src/Enemies/Beacon.java
package Enemies;

import SharedData.GameSettings;
import SharedData.Alignment;
import DungeonoftheBrutalKing.MainGameScreen;

/**
 * Represents a Beacon enemy with good alignment and no magic abilities.
 * Uses vitality for hit points calculation.
 */
public class Beacon extends Enemies {

    private int level;
    private final int vitality; // Vitality stat used for hit points
    private final Alignment alignment = Alignment.GOOD;
    private final int alignmentImpact = -3;

    /**
     * Default constructor. Initializes Beacon with preset stats.
     */
    public Beacon() {
        this(8, 8, 9, 7, 7, 9, 7); // Default vitality = 7
    }

    /**
     * Constructs a Beacon with specified stats.
     * Hit points are determined by level and vitality only.
     */
    public Beacon(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality) {
        super(
            "Beacon",
            level,
            (level * 5) + (vitality * 7), // HP uses only level and vitality
            strength,
            charisma,
            agility,
            intelligence,
            wisdom,
            GameSettings.MonsterImagePath + "Beacon.png",
            false
        );
        this.level = level;
        this.vitality = vitality;
    }

    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) setHitPoints(0);
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " falls, light unwavering.");
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    @Override
    public int attack() {
        return (int) ((getStrength() * 1.3) + (getWisdom() * 1.3));
    }

    public int defend(int incomingDamage) {
        int baseDefense = 13;
        int wisdom = getWisdom();
        int reductionPercent = (baseDefense + wisdom) / 2;
        if (reductionPercent > 75) reductionPercent = 75;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " radiates hope, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    @Override
    public String toString() {
        return "Beacon{" +
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
                ", isMagicUser=" + isMagicUser() +
                ", spellStrength=" + getSpellStrength() +
                '}';
    }

    public int getLevel() {
        return level;
    }

    public int getVitality() {
        return vitality;
    }

    @Override
    public int getExperienceReward() {
        int base = level * 15;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 9;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getAlignmentImpact() {
        return alignmentImpact;
    }

    @Override
    public Alignment getAlignment() {
        return alignment;
    }
}
