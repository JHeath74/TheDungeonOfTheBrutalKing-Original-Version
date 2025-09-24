
// src/Enemies/Dwarves.java
package Enemies;

import SharedData.GameSettings;

public class Dwarves extends Enemies {

	public Dwarves() {
	    // Calls the superclass constructor with the following parameters:
	    super(
	        "Dwarves",                          // Name of the enemy
	        1,                                  // Level
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
	}

    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) {
            setHitPoints(0);
        }
        if (isDead()) {
            System.out.println(getName() + " has died.");
        }
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    @Override
    public int getAttackDamage() {
        return (int) ((getStrength() * 1.5) + (getAgility() * 0.5));
    }

    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

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

    // Implementation of the abstract attack() method
    @Override
    public int attack() {
        return getAttackDamage();
    }
}
