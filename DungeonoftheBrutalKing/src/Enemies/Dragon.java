package Enemies;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.GameSettings;
import Status.FireStatus;
import Status.StatusManager;

/**
 * Represents a Dragon enemy with advanced combat abilities.
 */
public class Dragon extends Enemies {

    // The level of the Dragon, used for rewards and scaling
    private int level;

    /**
     * Constructs a Dragon with predefined stats and image.
     */
    public Dragon() {
        super(
            "Dragon", // Enemy name
            6,        // Level (used in superclass, but overridden below)
            30,       // Hit points
            8,        // Strength
            5,        // Charisma
            7,        // Agility
            6,        // Intelligence
            3,        // Wisdom
            GameSettings.MonsterImagePath + "Dragon.png", // Image path
            false,    // Is magic user
            0         // Spell strength
        );
        this.level = 9; // Set actual level for this instance
    }

    /**
     * Reduces hit points by the given damage amount.
     * If hit points drop below zero, sets them to zero.
     * Prints a message if the Dragon dies.
     * @param damage The amount of damage to take.
     */
    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) setHitPoints(0);
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " has died.\n");
    }

    /**
     * Attacks a target character, with a chance to inflict burn.
     * @param target The character being attacked.
     * @return The damage dealt.
     */
    public int attack(Character target) {
        int damage = getAttackDamage();
        double baseBurnChance = 0.3;
        double defenseFactor = Math.max(0, 1.0 - (target.getDefense() / 100.0));
        double finalBurnChance = baseBurnChance * defenseFactor;
        MainGameScreen.appendToMessageTextPane(getName() + " attacks for " + damage + " damage.\n");
        target.takeDamage(damage);
        if (Math.random() < finalBurnChance) {
            MainGameScreen.appendToMessageTextPane(getName() + " breathes fire! The target is burned.\n");
            FireStatus fireStatus = new FireStatus();
            target.getStatusManager().addStatus(fireStatus);
        }
        return damage;
    }

    /**
     * Throws an exception; use attack(Charecter target) instead.
     * @throws UnsupportedOperationException Always thrown to enforce correct usage.
     */
    @Override
    public int attack() {
        throw new UnsupportedOperationException("Use attack(Charecter target) instead.");
    }

    /**
     * Checks if the Dragon is dead (hit points <= 0).
     * @return true if dead, false otherwise.
     */
    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    /**
     * Calculates the Dragon's attack damage based on strength and agility.
     * @return The calculated attack damage.
     */
    @Override
    public int getAttackDamage() {
        return (int) ((getStrength() * 1.5) + (getAgility() * 0.5));
    }

    /**
     * Returns the image path for the Dragon.
     * Shows an injured image if hit points are low.
     * @return The image path.
     */
    @Override
    public String getImagePath() {
        if (getHitPoints() < 10) {
            return GameSettings.MonsterImagePath + "Dragon_injured.png";
        }
        return super.getImagePath();
    }

    /**
     * Returns a string representation of the Dragon's stats.
     * @return String with all key attributes.
     */
    @Override
    public String toString() {
        return "Dragon{" +
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
        MainGameScreen.appendToMessageTextPane(getName() + " defends and reduces damage to " + reducedDamage + ".\n");
        return reducedDamage;
    }

    /**
     * Gets the level of the Dragon.
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
