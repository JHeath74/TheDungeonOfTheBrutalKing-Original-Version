
// src/Enemies/Rat.java
package Enemies;

import SharedData.GameSettings;

public class Rat extends Enemies {

    // Constructor with comments for each variable
    public Rat() {
        super(
            /* name: The type or identifier of the enemy */ "Rat",
            /* level: The enemy's experience or difficulty level */ 1,
            /* hitPoints: The enemy's health value */ 10,
            /* strength: Physical attack power */ 2,
            /* charisma: Social or persuasive ability */ 1,
            /* agility: Speed and evasion capability */ 5,
            /* intelligence: Problem-solving or magical ability */ 1,
            /* wisdom: Decision-making or resistance to effects */ 1,
            /* imagePath: Path to the enemy's image asset */ GameSettings.MonsterImagePath + "Rat.png"
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
    public int attack() {
        return (int) ((getStrength() * 1.1) + (getAgility() * 0.6));
    }

    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    @Override
    public String toString() {
        return "Rat{" +
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
}
