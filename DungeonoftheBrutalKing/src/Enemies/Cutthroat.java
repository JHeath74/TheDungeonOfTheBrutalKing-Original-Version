package Enemies;

import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.Alignment;
import SharedData.GameSettings;

/**
 * Represents a Cutthroat enemy with basic combat abilities.
 */
public class Cutthroat extends Enemies {

	 private int level;
	 private final Alignment alignment = Alignment.EVIL;
	 private final int alignmentImpact = 2;
	 
	//    private static final int BASE_HP = 20;
//	    private static final int HP_PER_LEVEL = 5;
	//    private static final int HP_PER_STRENGTH = 2;
	 
//	 int HP = BASE_HP + (level * HP_PER_LEVEL) + (strength * HP_PER_STRENGTH);


    /**
     * Constructs a Cutthroat enemy with predefined stats and image.
     * Sets the enemy's name, level, attributes, image path, and other flags.
     */
	  public Cutthroat() {
	        super(
	            "Cutthroat",                // Enemy name
	            4,                          // Level
	            30,                         // Hit points
	            8,                          // Strength
	            5,                          // Charisma
	            7,                          // Agility
	            6,                          // Intelligence
	            3,                          // Wisdom
	            GameSettings.MonsterImagePath + "Cutthroat.png", // Image path
	            false,                      // Is boss
	            0                           // Special ability code
	        );
	        this.level = 4; // Set level for reference
	    }

    /**
     * Reduces hit points by the given damage amount.
     * If hit points drop below zero, sets them to zero.
     * Prints a message if the Cutthroat dies.
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
     * Checks if the Cutthroat is dead (hit points <= 0).
     * @return true if dead, false otherwise.
     */
    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    /**
     * Calculates the Cutthroat's attack damage based on strength and agility.
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
     * Returns the image path for the Cutthroat.
     * Shows an injured image if hit points are low.
     * @return The image path.
     */
    @Override
    public String getImagePath() {
        if (getHitPoints() < 10) {
            return GameSettings.MonsterImagePath + "Cutthroat_injured.png";
        }
        return super.getImagePath();
    }

    /**
     * Returns a string representation of the Cutthroat's stats.
     * @return String with all key attributes.
     */
    @Override
    public String toString() {
        return "Cutthroat{" +
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
        MainGameScreen.appendToMessageTextPane(getName() + " defends and reduces damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    /**
     * Gets the level of the Cutthroat.
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
    
    @Override
    public Alignment getAlignment() {
        return alignment;
    }
    
	@Override
	public int getAlignmentImpact() {
	    return alignmentImpact;
	}
}
