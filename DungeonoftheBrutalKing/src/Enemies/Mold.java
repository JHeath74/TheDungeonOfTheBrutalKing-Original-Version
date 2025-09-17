
// src/Enemies/Mold.java
package Enemies;

import SharedData.GameSettings;

public class Mold extends Enemies {

    // Constructor with comments for each variable
    public Mold() {
        super(
            /* name: The type or identifier of the enemy */ "Mold",
            /* level: The enemy's experience or difficulty level */ 1,
            /* hitPoints: The enemy's health value */ 20,
            /* strength: Physical attack power */ 4,
            /* charisma: Social or persuasive ability */ 2,
            /* agility: Speed and evasion capability */ 3,
            /* intelligence: Problem-solving or magical ability */ 1,
            /* wisdom: Decision-making or resistance to effects */ 1,
            /* imagePath: Path to the enemy's image asset */ GameSettings.MonsterImagePath + "Mold.png"
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
        return (int) ((getStrength() * 1.2) + (getAgility() * 0.3));
    }

    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    @Override
    public String toString() {
        return "Mold{" +
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
