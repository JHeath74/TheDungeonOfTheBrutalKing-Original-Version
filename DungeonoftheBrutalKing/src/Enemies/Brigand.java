
// Brigand.java

package Enemies;

import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.GameSettings;

/**
 * Represents a Brigand enemy with basic combat abilities.
 */
public class Brigand extends Enemies {

    // This field is incorrect and should be removed; see previous guidance.
   

    /**
     * Constructs a Brigand with predefined stats and image.
     */
    public Brigand() {
        super(
            /* name: The type or identifier of the enemy */ "Brigand",
            /* level: The enemy's experience or difficulty level */ 1,
            /* hitPoints: The enemy's health value */ 30,
            /* strength: Physical attack power */ 8,
            /* charisma: Social or persuasive ability */ 5,
            /* agility: Speed and evasion capability */ 7,
            /* intelligence: Problem-solving or magical ability */ 6,
            /* wisdom: Decision-making or resistance to effects */ 3,
            /* imagePath: Path to the enemy's image asset */ GameSettings.MonsterImagePath + "Brigand.png",
            /* isMagicUser: Brigand is not a magic user */ false,
            /* spellStrength: Brigand has no spell strength */ 0
        );
        
        this.level = 3;
    }

    /**
     * Reduces hit points by the given damage amount.
     * If hit points drop below zero, sets them to zero.
     * Prints a message if the Brigand dies.
     * @param damage The amount of damage to take.
     */
    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) {
            setHitPoints(0);
        }
        // This line is incorrect; should use MainGameScreen.appendToMessageTextPane(...)
        MainGameScreen.appendToMessageTextPane(getName() + " has died.");
    }

    /**
     * Checks if the Brigand is dead (hit points <= 0).
     * @return true if dead, false otherwise.
     */
    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
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

    /**
     * Calculates the Brigand's attack damage based on strength and agility.
     * @return The calculated attack damage.
     */
    @Override
    public int attack() {
        // Simple attack logic: base damage from strength and agility
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
     * Returns the image path for the Brigand.
     * @return The image path.
     */
    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    /**
     * Returns a string representation of the Brigand's stats.
     * @return String with all key attributes.
     */
    @Override
    public String toString() {
        return "Brigand{" +
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
        // Appends a message to the message text pane about the defense action
        MainGameScreen.appendToMessageTextPane(getName() + " defends and reduces damage to " + reducedDamage + ".");
        return reducedDamage;
    }
}
