package Enemies;

import java.util.ArrayList;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.GameSettings;

/**
 * Represents a Devourer enemy with basic combat abilities.
 */
public class Devourer extends Enemies {

    private int level;

    /**
     * Constructs a Devourer with predefined stats and image.
     */
    public Devourer() {
        super(
            "Devourer",
            4, // Set level to 4 for consistency
            30,
            8,
            5,
            7,
            6,
            3,
            GameSettings.MonsterImagePath + "Devourer.png",
            false,
            0
        );
        this.level = 7;
    }

    /**
     * Reduces hit points by the given damage amount.
     * If hit points drop below zero, sets them to zero.
     * Prints a message if the Devourer dies.
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
     * Checks if the Devourer is dead (hit points <= 0).
     * @return true if dead, false otherwise.
     */
    
    
    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }
    

    /**
     * Calculates the Devourer's attack damage based on strength and agility.
     * @return The calculated attack damage.
     */
    @Override
    public int attack() {
        return (int) ((getStrength() * 1.5) + (getAgility() * 0.5));
    }

    /**
     * Helper method to get the attack damage.
     * @return The attack damage.
     */
    public int getAttackDamage() {
        return attack();
    }

    /**
     * Returns the image path for the Devourer.
     * Shows an injured image if hit points are low.
     * @return The image path.
     */
    @Override
    public String getImagePath() {
        if (getHitPoints() < 10) {
            return GameSettings.MonsterImagePath + "Devourer_injured.png";
        }
        return super.getImagePath();
    }

    /**
     * Returns a string representation of the Devourer's stats.
     * @return String with all key attributes.
     */
    @Override
    public String toString() {
        return "Devourer{" +
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
     * Attempts to steal an item from the player.
     * @param player The player character.
     */
    public void tryStealItem(Charecter player) {
        double stealChance = 0.2; // 20% chance
        // Convert Set to List for indexed access
        ArrayList<String> inventory = new ArrayList<>(player.getCharInventory());
        if (!inventory.isEmpty() && Math.random() < stealChance) {
            int index = (int) (Math.random() * inventory.size());
            String stolen = inventory.get(index);
            // Remove from both the player's inventory set and the local list
            player.removeFromInventory(stolen);
            MainGameScreen.appendToMessageTextPane(getName() + " has stolen " + stolen + "!");
        }
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

    /**
     * Gets the level of the Devourer.
     * @return the level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the experience reward for defeating the Devourer.
     * @return experience points.
     */
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
