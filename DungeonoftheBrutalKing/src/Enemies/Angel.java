
package Enemies;

import SharedData.GameSettings;
import SharedData.Alignment;
import DungeonoftheBrutalKing.MainGameScreen;

/**
 * Represents an Angel enemy with specific stats and abilities.
 * Inherits from the Enemies base class.
 */
public class Angel extends Enemies {
    // Angel's level
    private int level;
    // Angel's core attributes
    private final int strength;
    private final int charisma;
    private final int agility;
    private final int intelligence;
    private final int wisdom;
    // Angel's vitality stat, used for hit points calculation
    private final int vitality;
    // Angel's current hit points
    private int hitPoints;
    // Angel's alignment is always GOOD
    private final Alignment alignment = Alignment.GOOD;

    /**
     * Default constructor. Initializes Angel with random level and preset stats.
     */
    public Angel() {
        this(randomLevel(), 6, 9, 7, 9, 10, 8); // Default vitality = 8
    }

    /**
     * Constructs an Angel with specified stats.
     * Hit points are determined by level and vitality only.
     */
    public Angel(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality) {
        super(
            "Angel",
            level,
            (level * 5) + (vitality * 7), // HP uses only level and vitality
            strength,
            charisma,
            agility,
            intelligence,
            wisdom,
            GameSettings.MonsterImagePath + "Angel.png",
            true
        );
        this.level = level;
        this.strength = strength;
        this.charisma = charisma;
        this.agility = agility;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.vitality = vitality;
        this.hitPoints = (level * 5) + (vitality * 7);
    }

    // Getters for Angel's attributes
    public int getLevel() { return level; }
    public int getStrength() { return strength; }
    public int getCharisma() { return charisma; }
    public int getAgility() { return agility; }
    public int getIntelligence() { return intelligence; }
    public int getWisdom() { return wisdom; }
    public int getVitality() { return vitality; }
    public int getHitPoints() { return hitPoints; }

    /**
     * Sets Angel's hit points, ensuring they do not go below zero.
     */
    public void setHitPoints(int hitPoints) { this.hitPoints = Math.max(hitPoints, 0); }

    /**
     * Reduces hit points by damage taken and displays a message if Angel dies.
     */
    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " fades, but grace remains.");
    }

    /**
     * Calculates Angel's spell strength based on level, wisdom, and intelligence.
     */
    @Override
    public int getSpellStrength() {
        return (getLevel() * 2) + (getWisdom() * 2) + (getIntelligence());
    }

    /**
     * Sets Angel's level.
     * Optionally, hit points can be recalculated here if needed.
     */
    @Override
    public void setLevel(int level) {
        this.level = level;
        // Optionally, recalculate hitPoints if level changes:
        // this.hitPoints = (level * 5) + (vitality * 7);
    }

    /**
     * Checks if Angel is dead (hit points <= 0).
     */
    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    /**
     * Calculates Angel's attack value using strength, wisdom, and spell strength.
     */
    @Override
    public int attack() {
        return (int) ((getStrength() * 1.2) + (getWisdom() * 1.5) + getSpellStrength());
    }

    /**
     * Reduces incoming damage based on Angel's wisdom and a base defense value.
     * Displays a message about the damage reduction.
     */
    public int defend(int incomingDamage) {
        int baseDefense = 8;
        int reductionPercent = (baseDefense + getWisdom()) / 2;
        if (reductionPercent > 80) reductionPercent = 80;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " spreads radiant wings, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    /**
     * Returns the image path for the Angel.
     */
    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    /**
     * Returns a string representation of the Angel.
     */
    @Override
    public String toString() {
        return "Angel{" +
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

    /**
     * Calculates the experience reward for defeating the Angel.
     */
    @Override
    public int getExperienceReward() {
        int base = level * 16;
        int offset = (int) ((Math.random() * (2 * level * 8 + 1)) - (level * 8));
        return Math.max(base + offset, 0);
    }

    /**
     * Calculates the gold reward for defeating the Angel.
     */
    @Override
    public int getGoldReward() {
        int base = level * 9;
        int offset = (int) ((Math.random() * (2 * level * 8 + 1)) - (level * 8));
        return Math.max(base + offset, 0);
    }

    /**
     * Generates a random level for the Angel between 1 and 5.
     */
    private static int randomLevel() {
        return 1 + (int) (Math.random() * 5);
    }

    /**
     * Returns the alignment impact (negative for GOOD alignment).
     */
    @Override
    public int getAlignmentImpact() {
        int offset = (int) (Math.random() * ((level / 5) * 2 + 1)) - (level / 5);
        return -(level + offset);
    }

    /**
     * Returns the Angel's alignment (always GOOD).
     */
    @Override
    public Alignment getAlignment() {
        return alignment;
    }
}
