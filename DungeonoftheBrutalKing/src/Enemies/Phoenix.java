
// src/Enemies/Phoenix.java
package Enemies;

import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.GameSettings;
import SharedData.Alignment;
import Status.FireStatus;
import DungeonoftheBrutalKing.Character;

/**
 * Represents a Phoenix enemy with fire attack and alignment.
 */
public class Phoenix extends Enemies {

    // --- Fields ---
    private int level; // Used for rewards and scaling
    private final Alignment alignment = Alignment.GOOD;
    private final int alignmentImpact = -3;

    // --- Constructor ---
    /**
     * Constructs a Phoenix enemy with predefined stats and image.
     */
    public Phoenix() {
        super(
            "Phoenix",                                   // Name
            9,                                           // Level (used in superclass, overridden below)
            30,                                          // Hit points
            8,                                           // Strength
            5,                                           // Charisma
            7,                                           // Agility
            6,                                           // Intelligence
            3,                                           // Wisdom
            GameSettings.MonsterImagePath + "Phoenix.png", // Image path
            true,                                        // Is magic user
            10                                           // Spell strength
        );
        this.level = 9; // Set actual level for this instance
    }

    // --- Combat Methods ---
    /**
     * Reduces hit points by the given damage amount.
     * If hit points drop below zero, sets them to zero.
     * Prints a message if the Phoenix dies.
     * @param damage The amount of damage to take.
     */
    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) setHitPoints(0);
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " has died.");
    }

    /**
     * Checks if the Phoenix is dead (hit points <= 0).
     * @return true if dead, false otherwise.
     */
    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    /**
     * Calculates the Phoenix's attack damage based on strength, agility, and spell strength.
     * @return The calculated attack damage.
     */
    @Override
    public int getAttackDamage() {
        return (int) ((getStrength() * 1.5) + (getAgility() * 0.5) + getSpellStrength());
    }

    /**
     * Performs a fire attack on the target, with a chance to apply fire status.
     * @param target The character being attacked.
     * @return The fire attack damage.
     */
    public int attack(Character target) {
        int phoenixFlameAttack = 12 + getStrength() + getSpellStrength();
        boolean fireStatusApplied = Math.random() < 0.3; // 30% chance
        if (fireStatusApplied) {
            MainGameScreen.appendToMessageTextPane(getName() + " attacks with Phoenix Flame and applies fire status!");
            target.addStatus(new FireStatus());
        } else {
            MainGameScreen.appendToMessageTextPane(getName() + " attacks with Phoenix Flame.");
        }
        return phoenixFlameAttack;
    }

    /**
     * Calculates reduced damage when defending, based on base defense and agility.
     * Caps reduction at 80%. Displays a message with the reduced damage.
     * @param incomingDamage The original damage to be reduced.
     * @return The reduced damage after defense.
     */
    @Override
    public int defend(int incomingDamage) {
        int baseDefense = 12;
        int agility = getAgility();
        int reductionPercent = (baseDefense + agility) / 2;
        if (reductionPercent > 80) reductionPercent = 80; // Cap at 80%
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " defends and reduces damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    /**
     * Default attack method (not used for Phoenix).
     * @return 0.
     */
    @Override
    public int attack() {
        return 0;
    }

    // --- Utility Methods ---
    /**
     * Returns the image path for the Phoenix.
     * @return The image path.
     */
    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    /**
     * Returns a string representation of the Phoenix's stats.
     * @return String with all key attributes.
     */
    @Override
    public String toString() {
        return "Phoenix{" +
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
     * Gets the level of the Phoenix.
     * @return the level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the experience reward for defeating the Phoenix.
     * @return experience points.
     */
    @Override
    public int getExperienceReward() {
        int base = level * 10;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    /**
     * Gets the gold reward for defeating the Phoenix.
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
     * Gets the alignment of the Phoenix.
     * @return alignment.
     */
    public Alignment getAlignment() {
        return alignment;
    }
}
