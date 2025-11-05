package Enemies;

import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.GameSettings;

/**
 * Represents a Dwarves enemy with basic combat abilities.
 */
public class Dwarves extends Enemies {

    // The level of the Dwarves, used for rewards and scaling
    private int level;

    /**
     * Constructs a Dwarves enemy with predefined stats and image.
     */


public Dwarves() {
    super(
        "Dwarves",                          // Name of the enemy
        2,                                  // Level
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


    /**
     * Reduces hit points by the given damage amount.
     * If hit points drop below zero, sets them to zero.
     * Prints a message if the Dwarves dies.
     * @param damage The amount of damage to take.
     */
    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) {
            setHitPoints(0);
        }
        if (isDead()) {
            MainGameScreen.appendToMessageTextPane(getName() + " has died.");
        }
    }

    /**
     * Checks if the Dwarves is dead (hit points <= 0).
     * @return true if dead, false otherwise.
     */
    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
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

    /**
     * Implementation of the abstract attack() method.
     * @return The attack damage.
     */
    @Override
    public int attack() {
        return getAttackDamage();
    }

    /**
     * Gets the level of the Dwarves.
     * @return the level.
     */
    public int getLevel() {
        return level;
    }

    @Override
    public int getExperienceReward() {
        int base = level * 10;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 5;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }
}
