
// src/Enemies/Skeleton.java
package Enemies;

import SharedData.GameSettings;
import SharedData.Alignment;
import DungeonoftheBrutalKing.MainGameScreen;

/**
 * Represents a Skeleton enemy with basic combat abilities and alignment.
 */
public class Skeleton extends Enemies {

    // --- Fields ---
    private int level; // Used for rewards and scaling
    private final Alignment alignment = Alignment.EVIL;
    private final int alignmentImpact = 1;

    // --- Constructor ---
    /**
     * Constructs a Skeleton enemy with predefined stats and image.
     */
    public Skeleton() {
        super(
            "Skeleton",                                   // Name
            1,                                            // Level (used in superclass, overridden below)
            30,                                           // Hit points
            8,                                            // Strength
            5,                                            // Charisma
            7,                                            // Agility
            6,                                            // Intelligence
            3,                                            // Wisdom
            GameSettings.MonsterImagePath + "Skeleton.png", // Image path
            false,                                        // Is magic user
            0                                             // Spell strength
        );
        this.level = 1; // Set actual level for this instance
    }

    // --- Combat Methods ---
    /**
     * Reduces hit points by the given damage amount.
     * If hit points drop below zero, sets them to zero.
     * Prints a message if the Skeleton dies.
     * @param damage The amount of damage to take.
     */
    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) setHitPoints(0);
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " has died.");
    }

    /**
     * Checks if the Skeleton is dead (hit points <= 0).
     * @return true if dead, false otherwise.
     */
    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    /**
     * Calculates the Skeleton's attack damage based on strength and agility.
     * @return The calculated attack damage.
     */
    @Override
    public int attack() {
        return (int) ((getStrength() * 1.5) + (getAgility() * 0.5));
    }

    /**
     * Calculates reduced damage when defending, based on base defense and agility.
     * Caps reduction at 80%. Displays a message with the reduced damage.
     * @param incomingDamage The original damage to be reduced.
     * @return The reduced damage after defense.
     */
    public int defend(int incomingDamage) {
        int baseDefense = 10;
        int agility = getAgility();
        int reductionPercent = (baseDefense + agility) / 2;
        if (reductionPercent > 80) reductionPercent = 80; // Cap at 80%
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " defends and reduces damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    // --- Utility Methods ---
    /**
     * Returns the image path for the Skeleton.
     * @return The image path.
     */
    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    /**
     * Returns a string representation of the Skeleton's stats.
     * @return String with all key attributes.
     */
    @Override
    public String toString() {
        return "Skeleton{" +
                "name='" + getName() + '\'' +
                ", level=" + getLevel() +
                ", hitPoints=" + getHitPoints() +
                ", strength=" + getStrength() +
                ", charisma=" + getCharisma() +
                ", agility=" + getAgility() +
                ", intelligence=" + getIntelligence() +
                ", wisdom=" + getWisdom() +
                ", imagePath='" + getImagePath() + '\'' +
                ", isMagicUser=" + isMagicUser() +
                ", spellStrength=" + getSpellStrength() +
                '}';
    }

    // --- Getters and Alignment Methods ---
    /**
     * Gets the level of the Skeleton.
     * @return the level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the experience reward for defeating the Skeleton.
     * @return experience points.
     */
    @Override
    public int getExperienceReward() {
        int base = level * 10;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    /**
     * Gets the gold reward for defeating the Skeleton.
     * @return gold amount.
     */
    @Override
    public int getGoldReward() {
        int base = level * 5;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    /**
     * Gets the alignment impact value.
     * @return alignment impact.
     */
    @Override
    public int getAlignmentImpact() {
        return alignmentImpact;
    }

    /**
     * Gets the alignment of the Skeleton.
     * @return alignment.
     */
    public Alignment getAlignment() {
        return alignment;
    }
}
