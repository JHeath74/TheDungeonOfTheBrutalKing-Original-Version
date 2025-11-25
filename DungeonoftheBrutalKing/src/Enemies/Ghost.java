
// src/Enemies/Ghost.java
package Enemies;

import SharedData.GameSettings;
import SharedData.Alignment;
import DungeonoftheBrutalKing.MainGameScreen;

/**
 * Represents a Ghost enemy with chilling attacks and alignment.
 */
public class Ghost extends Enemies {

    // --- Fields ---
    private int level; // Used for rewards and scaling
    private final Alignment alignment = Alignment.EVIL;
    private final int alignmentImpact = 3;

    // --- Constructor ---
    /**
     * Constructs a Ghost enemy with predefined stats and image.
     */
    public Ghost() {
        super(
            "Ghost",                          // Name
            3,                                // Level (used in superclass, overridden below)
            20,                               // Hit points
            5,                                // Strength
            4,                                // Charisma
            10,                               // Agility
            8,                                // Intelligence
            7,                                // Wisdom
            GameSettings.MonsterImagePath + "Ghost.png", // Image path
            false,                            // Is magic user
            0                                 // Spell strength
        );
        this.level = 3; // Set actual level for this instance
    }

    // --- Combat Methods ---
    /**
     * Reduces hit points by the given damage amount.
     * If hit points drop below zero, sets them to zero.
     * Prints a message if the Ghost dies.
     * @param damage The amount of damage to take.
     */
    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) setHitPoints(0);
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " has died.");
    }

    /**
     * Checks if the Ghost is dead (hit points <= 0).
     * @return true if dead, false otherwise.
     */
    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    /**
     * Calculates the Ghost's attack damage based on strength and agility.
     * @return The calculated attack damage.
     */
    @Override
    public int getAttackDamage() {
        return (int) ((getStrength() * 1.2) + (getAgility() * 0.8));
    }

    /**
     * Implementation of the abstract attack() method.
     * @return The attack damage.
     */
    @Override
    public int attack() {
        int damage = getAttackDamage();
        MainGameScreen.appendToMessageTextPane(getName() + " attacks with a chilling touch for " + damage + " damage!");
        return damage;
    }

    // --- Utility Methods ---
    /**
     * Returns the image path for the Ghost.
     * @return The image path.
     */
    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    /**
     * Returns a string representation of the Ghost's stats.
     * @return String with all key attributes.
     */
    @Override
    public String toString() {
        return "Ghost{" +
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

    // --- Getters and Alignment Methods ---
    /**
     * Gets the level of the Ghost.
     * @return the level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the experience reward for defeating the Ghost.
     * @return experience points.
     */
    @Override
    public int getExperienceReward() {
        int base = level * 10;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    /**
     * Gets the gold reward for defeating the Ghost.
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
     * Gets the alignment of the Ghost.
     * @return alignment.
     */
    public Alignment getAlignment() {
        return alignment;
    }
}
