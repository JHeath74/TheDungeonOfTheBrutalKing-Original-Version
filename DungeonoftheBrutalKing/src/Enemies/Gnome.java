
// src/Enemies/Gnome.java
package Enemies;

import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.GameSettings;
import SharedData.Alignment;

/**
 * Represents a Gnome enemy with basic combat abilities and alignment.
 */
public class Gnome extends Enemies {

    // --- Fields ---
    private int level; // Used for rewards and scaling
    private final Alignment alignment = Alignment.NEUTRAL;
    private final int alignmentImpact = 1;

    // --- Constructor ---
    /**
     * Constructs a Gnome enemy with predefined stats and image.
     */
    public Gnome() {
        super(
            "Gnome",                          // Name
            2,                                // Level (used in superclass, overridden below)
            30,                               // Hit points
            8,                                // Strength
            5,                                // Charisma
            7,                                // Agility
            6,                                // Intelligence
            3,                                // Wisdom
            GameSettings.MonsterImagePath + "Gnome.png", // Image path
            false,                            // Is magic user
            0                                 // Spell strength
        );
        this.level = 2; // Set actual level for this instance
    }

    // --- Combat Methods ---
    /**
     * Reduces hit points by the given damage amount.
     * If hit points drop below zero, sets them to zero.
     * Prints a message if the Gnome dies.
     * @param damage The amount of damage to take.
     */
    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) setHitPoints(0);
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " has died.");
    }

    /**
     * Checks if the Gnome is dead (hit points <= 0).
     * @return true if dead, false otherwise.
     */
    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    /**
     * Calculates the Gnome's attack damage based on strength and agility.
     * @return The calculated attack damage.
     */
    @Override
    public int attack() {
        return (int) ((getStrength() * 1.5) + (getAgility() * 0.5));
    }

    /**
     * Helper method to get attack damage.
     * @return The attack damage.
     */
    public int getAttackDamage() {
        return attack();
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
     * Returns the image path for the Gnome.
     * @return The image path.
     */
    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    /**
     * Returns a string representation of the Gnome's stats.
     * @return String with all key attributes.
     */
    @Override
    public String toString() {
        return "Gnome{" +
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
     * Gets the level of the Gnome.
     * @return the level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the experience reward for defeating the Gnome.
     * @return experience points.
     */
    @Override
    public int getExperienceReward() {
        int base = level * 10;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    /**
     * Gets the gold reward for defeating the Gnome.
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
     * Gets the alignment of the Gnome.
     * @return alignment.
     */
    public Alignment getAlignment() {
        return alignment;
    }
}
