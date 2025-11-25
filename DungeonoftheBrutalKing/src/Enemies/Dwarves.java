
// src/Enemies/Dwarves.java
package Enemies;

import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.Alignment;
import SharedData.GameSettings;

/**
 * Represents a Dwarves enemy with basic combat abilities and alignment.
 */
public class Dwarves extends Enemies {

    // --- Fields ---
    private int level; // Used for rewards and scaling
    private final Alignment alignment = Alignment.GOOD;
    private final int alignmentImpact = 2;

    // --- Constructor ---
    /**
     * Constructs a Dwarves enemy with predefined stats and image.
     */
    public Dwarves() {
        super(
            "Dwarves",                          // Name of the enemy
            2,                                  // Level (used in superclass, but overridden below)
            30,                                 // Hit points
            8,                                  // Strength
            5,                                  // Charisma
            7,                                  // Agility
            6,                                  // Intelligence
            3,                                  // Wisdom
            GameSettings.MonsterImagePath + "Dwarves.png", // Image path
            false,                              // Is boss
            0                                   // Experience points
        );
        this.level = 2; // Set actual level for this instance
    }

    // --- Combat Methods ---
    /**
     * Reduces hit points by the given damage amount.
     * If hit points drop below zero, sets them to zero.
     * Prints a message if the Dwarves dies.
     * @param damage The amount of damage to take.
     */
    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) setHitPoints(0);
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " has died.");
    }

    /**
     * Calculates the Dwarves's attack damage based on strength and agility.
     * @return The calculated attack damage.
     */
    @Override
    public int getAttackDamage() {
        return (int) ((getStrength() * 1.5) + (getAgility() * 0.5));
    }

    /**
     * Implementation of the abstract attack() method.
     * @return The attack damage.
     */
    @Override
    public int attack() {
        return getAttackDamage();
    }

    // --- Utility Methods ---
    /**
     * Checks if the Dwarves is dead (hit points <= 0).
     * @return true if dead, false otherwise.
     */
    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    /**
     * Returns the image path for the Dwarves.
     * Shows an injured image if hit points are low.
     * @return The image path.
     */
    @Override
    public String getImagePath() {
        if (getHitPoints() < 10) {
            return GameSettings.MonsterImagePath + "Dwarves_injured.png";
        }
        return super.getImagePath();
    }

    /**
     * Returns a string representation of the Dwarves's stats.
     * @return String with all key attributes.
     */
    @Override
    public String toString() {
        return "Dwarves{" +
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
     * Gets the level of the Dwarves.
     * @return the level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the experience reward for defeating the Dwarves.
     * @return experience points.
     */
    @Override
    public int getExperienceReward() {
        int base = level * 10;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    /**
     * Gets the gold reward for defeating the Dwarves.
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
     * Gets the alignment of the Dwarves.
     * @return alignment.
     */
    public Alignment getAlignment() {
        return alignment;
    }
}
