
// src/Enemies/Mold.java
package Enemies;

import SharedData.GameSettings;
import SharedData.Alignment;
import Status.PoisonStatus;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;

/**
 * Represents a Mold enemy with poison attack and alignment.
 */
public class Mold extends Enemies {

    // --- Fields ---
    private int level; // Used for rewards and scaling
    private final Alignment alignment = Alignment.EVIL;
    private final int alignmentImpact = 1;

    // --- Constructor ---
    /**
     * Constructs a Mold enemy with predefined stats and image.
     */
    public Mold() {
        super(
            "Mold",                                   // Name
            1,                                        // Level (used in superclass, overridden below)
            20,                                       // Hit points
            4,                                        // Strength
            2,                                        // Charisma
            3,                                        // Agility
            1,                                        // Intelligence
            1,                                        // Wisdom
            GameSettings.MonsterImagePath + "Mold.png", // Image path
            false,                                    // Is magic user
            0                                         // Spell strength
        );
        this.level = 1; // Set actual level for this instance
    }

    // --- Combat Methods ---
    /**
     * Reduces hit points by the given damage amount.
     * If hit points drop below zero, sets them to zero.
     * Prints a message if the Mold dies.
     * @param damage The amount of damage to take.
     */
    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) setHitPoints(0);
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " has died.");
    }

    /**
     * Checks if the Mold is dead (hit points <= 0).
     * @return true if dead, false otherwise.
     */
    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    /**
     * Performs a poison attack on the target, with a chance to apply poison status.
     * @param target The character being attacked.
     * @return The poison attack damage.
     */
    @Override
    public int attack(Character target) {
        int poisonAttack = 12 + getStrength() + getSpellStrength();
        boolean poisonStatusApplied = Math.random() < 0.3; // 30% chance
        if (poisonStatusApplied) {
            MainGameScreen.appendToMessageTextPane(getName() + " attacks and applies poison status!");
            target.addStatus(new PoisonStatus(poisonAttack));
        } else {
            MainGameScreen.appendToMessageTextPane(getName() + " attacks.");
        }
        return poisonAttack;
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
     * Returns the image path for the Mold.
     * @return The image path.
     */
    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    /**
     * Returns a string representation of the Mold's stats.
     * @return String with all key attributes.
     */
    @Override
    public String toString() {
        return "Mold{" +
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
     * Gets the level of the Mold.
     * @return the level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the experience reward for defeating the Mold.
     * @return experience points.
     */
    @Override
    public int getExperienceReward() {
        int base = level * 10;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    /**
     * Gets the gold reward for defeating the Mold.
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
     * Gets the alignment of the Mold.
     * @return alignment.
     */
    public Alignment getAlignment() {
        return alignment;
    }
}
