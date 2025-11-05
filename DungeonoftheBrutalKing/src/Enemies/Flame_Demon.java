
package Enemies;

import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.GameSettings;
import Status.FireStatus;
import DungeonoftheBrutalKing.Charecter;

/**
 * Represents a Flame Demon enemy with fire-based attacks.
 */
public class Flame_Demon extends Enemies {

    // The level of the Flame Demon, used for rewards and scaling
    private int level;

    /**
     * Constructs a Flame Demon enemy with predefined stats and image.
     */
    public Flame_Demon() {
        super(
            /* name: The type or identifier of the enemy */ "Flame Demon",
            /* level: The enemy's experience or difficulty level */ 6,
            /* hitPoints: The enemy's health value */ 30,
            /* strength: Physical attack power */ 8,
            /* charisma: Social or persuasive ability */ 5,
            /* agility: Speed and evasion capability */ 7,
            /* intelligence: Problem-solving or magical ability */ 6,
            /* wisdom: Decision-making or resistance to effects */ 3,
            /* imagePath: Path to the enemy's image asset */ GameSettings.MonsterImagePath + "Flame_Demon.png",
            /* isMagicUser: Flame Demon is not a magic user */ false,
            /* spellStrength: Flame Demon has no spell strength */ 0
        );
        this.level = 6; // Set actual level for this instance
    }

    /**
     * Reduces hit points by the given damage amount.
     * If hit points drop below zero, sets them to zero.
     * Prints a message if the Flame Demon dies.
     * @param damage The amount of damage to take.
     */
    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) setHitPoints(0);
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " has died.");
    }

    /**
     * Checks if the Flame Demon is dead (hit points <= 0).
     * @return true if dead, false otherwise.
     */
    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    /**
     * Calculates the Flame Demon's attack damage based on strength and agility.
     * @return The calculated attack damage.
     */
    @Override
    public int getAttackDamage() {
        return (int) ((getStrength() * 1.5) + (getAgility() * 0.5));
    }

    /**
     * Default attack (no target).
     * @return The attack damage.
     */
    @Override
    public int attack() {
        int fireSwordAttack = 10 + getStrength();
        MainGameScreen.appendToMessageTextPane(getName() + " attacks with Fire Sword.");
        return fireSwordAttack;
    }

    /**
     * Attack with fire status effect.
     * @param target The character being attacked.
     * @return The attack damage.
     */
    public int attack(Charecter target) {
        int fireSwordAttack = 10 + getStrength();
        boolean fireStatusApplied = Math.random() < 0.3; // 30% chance
        if (fireStatusApplied) {
            MainGameScreen.appendToMessageTextPane(getName() + " attacks with Fire Sword and applies fire status!");
            target.addStatus(new FireStatus());
        } else {
            MainGameScreen.appendToMessageTextPane(getName() + " attacks with Fire Sword.");
        }
        return fireSwordAttack;
    }

    /**
     * Returns the image path for the Flame Demon.
     * Shows an injured image if hit points are low.
     * @return The image path.
     */
    @Override
    public String getImagePath() {
        if (getHitPoints() < 10) {
            return GameSettings.MonsterImagePath + "Flame_Demon_injured.png";
        }
        return super.getImagePath();
    }

    /**
     * Returns a string representation of the Flame Demon's stats.
     * @return String with all key attributes.
     */
    @Override
    public String toString() {
        return "Flame_Demon{" +
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
    @Override
    public int defend(int incomingDamage) {
        int baseDefense = 10;
        int agility = getAgility();
        int reductionPercent = (baseDefense + agility) / 2;
        if (reductionPercent > 80) reductionPercent = 80;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " defends and reduces damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    /**
     * Gets the level of the Flame Demon.
     * @return the level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the experience reward for defeating the Flame Demon.
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
    
@Override
//Add this if Flame_Demon should apply an effect as part of its attack
public void attemptApplyEffect(Charecter target) {
 int damage = attack(target); // This already applies fire status with a chance
 target.takeDamage(damage);
}
}


