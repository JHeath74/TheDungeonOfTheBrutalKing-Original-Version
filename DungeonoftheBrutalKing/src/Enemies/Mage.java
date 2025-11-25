
// src/Enemies/Mage.java
package Enemies;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.GameSettings;
import SharedData.Alignment;
import Status.Status;

/**
 * Represents a Mage enemy with combat abilities, magic, and alignment.
 */
public class Mage extends Enemies {

    // --- Fields ---
    private int level; // Used for rewards and scaling
    private final Alignment alignment = Alignment.EVIL;
    private final int alignmentImpact = 2;

    // --- Constructor ---
    /**
     * Constructs a Mage enemy with predefined stats and image.
     */
    public Mage() {
        super(
            "Mage",                                   // Name
            5,                                        // Level (used in superclass, overridden below)
            25,                                       // Hit points
            5,                                        // Strength
            6,                                        // Charisma
            8,                                        // Agility
            10,                                       // Intelligence
            7,                                        // Wisdom
            GameSettings.MonsterImagePath + "Mage.png", // Image path
            true,                                     // Is magic user
            12                                        // Spell strength
        );
        this.level = 5; // Set actual level for this instance
    }

    // --- Combat Methods ---
    /**
     * Reduces hit points by the given damage amount.
     * If hit points drop below zero, sets them to zero.
     * Prints a message if the Mage dies.
     * @param damage The amount of damage to take.
     */
    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) setHitPoints(0);
        if (isDead()) System.out.println(getName() + " has died.");
    }

    /**
     * Checks if the Mage is dead (hit points <= 0).
     * @return true if dead, false otherwise.
     */
    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    /**
     * Calculates the Mage's attack damage based on intelligence, agility, and spell strength.
     * @return The calculated attack damage.
     */
    @Override
    public int attack() {
        return (int) ((getIntelligence() * 1.5) + (getAgility() * 0.5) + (isMagicUser() ? getSpellStrength() : 0));
    }

    /**
     * Performs a custom attack with fireball and status effect.
     * @param target The character being attacked.
     * @param fireStatus The status effect to apply.
     */
    public void attack(Character target, Status fireStatus) {
        int spellDamage = attack();
        MainGameScreen.appendToMessageTextPane(getName() + " attacks with a fireball for " + spellDamage + " damage!");
        target.takeDamage(spellDamage);
        fireStatus.applyEffect(target);
        MainGameScreen.appendToMessageTextPane(getName() + " attempts to apply " + fireStatus.getName() + " effect!");
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
        System.out.println(getName() + " defends and reduces damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    // --- Utility Methods ---
    /**
     * Returns the image path for the Mage.
     * @return The image path.
     */
    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    /**
     * Returns a string representation of the Mage's stats.
     * @return String with all key attributes.
     */
    @Override
    public String toString() {
        return "Mage{" +
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
     * Gets the level of the Mage.
     * @return the level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the experience reward for defeating the Mage.
     * @return experience points.
     */
    @Override
    public int getExperienceReward() {
        int base = level * 10;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    /**
     * Gets the gold reward for defeating the Mage.
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
     * Gets the alignment of the Mage.
     * @return alignment.
     */
    public Alignment getAlignment() {
        return alignment;
    }
}
