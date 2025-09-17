
// src/Enemies/Mage.java
package Enemies;

import SharedData.GameSettings;

public class Mage extends Enemies {

    // Constructor with comments for each variable
    public Mage() {
        super(
            /* name: The type or identifier of the enemy */ "Mage",
            /* level: The enemy's experience or difficulty level */ 1,
            /* hitPoints: The enemy's health value */ 25,
            /* strength: Physical attack power */ 5,
            /* charisma: Social or persuasive ability */ 6,
            /* agility: Speed and evasion capability */ 8,
            /* intelligence: Problem-solving or magical ability */ 10,
            /* wisdom: Decision-making or resistance to effects */ 7,
            /* imagePath: Path to the enemy's image asset */ GameSettings.MonsterImagePath + "Mage.png"
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
        return (int) ((getIntelligence() * 1.5) + (getAgility() * 0.5));
    }

    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

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
                '}';
    }
}
