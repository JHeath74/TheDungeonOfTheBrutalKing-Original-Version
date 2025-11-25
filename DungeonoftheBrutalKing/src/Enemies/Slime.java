
// src/Enemies/Slime.java
package Enemies;

import SharedData.GameSettings;
import SharedData.Alignment;
import DungeonoftheBrutalKing.MainGameScreen;
import Status.PoisonStatus;
import DungeonoftheBrutalKing.Character;

/**
 * Represents a Slime enemy with basic combat abilities and alignment.
 */
public class Slime extends Enemies {

    // --- Fields ---
    private int level; // Used for rewards and scaling
    private final Alignment alignment = Alignment.EVIL;
    private final int alignmentImpact = 1;

    // --- Constructor ---
    /**
     * Constructs a Slime enemy with predefined stats and image.
     */
    public Slime() {
        super(
            "Slime",                                   // Name
            1,                                         // Level (used in superclass, overridden below)
            15,                                        // Hit points
            3,                                         // Strength
            1,                                         // Charisma
            2,                                         // Agility
            1,                                         // Intelligence
            1,                                         // Wisdom
            GameSettings.MonsterImagePath + "Slime.png", // Image path
            false,                                     // Is magic user
            0                                          // Spell strength
        );
        this.level = 1; // Set actual level for this instance
    }

    // --- Combat Methods ---
    /**
     * Reduces hit points by the given damage amount.
     * If hit points drop below zero, sets them to zero.
     * Prints a message if the Slime dies.
     * @param damage The amount of damage to take.
     */
    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) setHitPoints(0);
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " has died.");
    }

    /**
     * Checks if the Slime is dead (hit points <= 0).
     * @return true if dead, false otherwise.
     */
    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    /**
     * Calculates the Slime's attack damage based on strength and agility.
     * @return The calculated attack damage.
     */
    @Override
    public int attack() {
        return (int) ((getStrength() * 1.2) + (getAgility() * 0.4));
    }

    /**
     * Performs a poison attack on the target, with a chance to apply poison status.
     * @param target The character being attacked.
     * @return The attack damage.
     */
    public int attack(Character target) {
        boolean poisonApplied = Math.random() < 0.2; // 20% chance
        if (poisonApplied) {
            MainGameScreen.appendToMessageTextPane(getName() + " attacks and applies poison!");
            target.addStatus(new PoisonStatus(2)); // Poison for 2 turns
        } else {
            MainGameScreen.appendToMessageTextPane(getName() + " attacks.");
        }
        return attack();
    }

    /**
     * Calculates reduced damage when defending, based on base defense and agility.
     * Caps reduction at 80%. Displays a message with the reduced damage.
     * @param incomingDamage The original damage to be reduced.
     * @return The reduced damage after defense.
     */
    public int defend(int incomingDamage) {
        int baseDefense = 8;
        int agility = getAgility();
        int reductionPercent = (baseDefense + agility) / 2;
        if (reductionPercent > 80) reductionPercent = 80; // Cap at 80%
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " defends and reduces damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    // --- Utility Methods ---
    /**
     * Returns the image path for the Slime.
     * @return The image path.
     */
    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    /**
     * Returns a string representation of the Slime's stats.
     * @return String with all key attributes.
     */
    @Override
    public String toString() {
        return "Slime{" +
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
     * Gets the level of the Slime.
     * @return the level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the experience reward for defeating the Slime.
     * @return experience points.
     */
    @Override
    public int getExperienceReward() {
        int base = level * 10;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    /**
     * Gets the gold reward for defeating the Slime.
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
     * Gets the alignment of the Slime.
     * @return alignment.
     */
    public Alignment getAlignment() {
        return alignment;
    }
}
