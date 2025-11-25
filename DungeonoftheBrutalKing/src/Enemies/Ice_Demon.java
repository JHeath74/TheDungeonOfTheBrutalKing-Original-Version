
// src/Enemies/Ice_Demon.java
package Enemies;

import SharedData.GameSettings;
import SharedData.Alignment;
import Status.IceStatus;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import java.util.Random;

/**
 * Represents an Ice Demon enemy with basic combat abilities, frozen status effect, and alignment.
 */
public class Ice_Demon extends Enemies {

    // --- Fields ---
    private int level; // Used for rewards and scaling
    private final Alignment alignment = Alignment.EVIL;
    private final int alignmentImpact = 2;

    // --- Constructor ---
    /**
     * Constructs an Ice Demon enemy with predefined stats and image.
     */
    public Ice_Demon() {
        super(
            "Ice Demon",                          // Name
            4,                                    // Level (used in superclass, overridden below)
            30,                                   // Hit points
            8,                                    // Strength
            5,                                    // Charisma
            7,                                    // Agility
            6,                                    // Intelligence
            3,                                    // Wisdom
            GameSettings.MonsterImagePath + "Ice_Demon.png", // Image path
            false,                                // Is magic user
            0                                     // Spell strength
        );
        this.level = 4; // Set actual level for this instance
    }

    // --- Combat Methods ---
    /**
     * Reduces hit points by the given damage amount.
     * If hit points drop below zero, sets them to zero.
     * Prints a message if the Ice Demon dies.
     * @param damage The amount of damage to take.
     */
    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) setHitPoints(0);
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " has died.");
    }

    /**
     * Checks if the Ice Demon is dead (hit points <= 0).
     * @return true if dead, false otherwise.
     */
    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    /**
     * Calculates the Ice Demon's attack damage based on strength and agility.
     * @return The calculated attack damage.
     */
    @Override
    public int attack() {
        return (int) ((getStrength() * 1.5) + (getAgility() * 0.5));
    }

    /**
     * Performs an attack and may inflict frozen status on the target.
     * @param target The character being attacked.
     * @return The attack damage.
     */
    public int attack(Character target) {
        int baseAttack = attack();
        if (new Random().nextInt(100) < 25) {
            MainGameScreen.appendToMessageTextPane(getName() + " uses icy power! The target is frozen!");
            target.addStatus(new IceStatus());
        }
        return baseAttack;
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
     * Returns the image path for the Ice Demon.
     * @return The image path.
     */
    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    /**
     * Returns a string representation of the Ice Demon's stats.
     * @return String with all key attributes.
     */
    @Override
    public String toString() {
        return "Ice_Demon{" +
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
     * Gets the level of the Ice Demon.
     * @return the level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the experience reward for defeating the Ice Demon.
     * @return experience points.
     */
    @Override
    public int getExperienceReward() {
        int base = level * 10;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    /**
     * Gets the gold reward for defeating the Ice Demon.
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
     * Gets the alignment of the Ice Demon.
     * @return alignment.
     */
    public Alignment getAlignment() {
        return alignment;
    }
}
