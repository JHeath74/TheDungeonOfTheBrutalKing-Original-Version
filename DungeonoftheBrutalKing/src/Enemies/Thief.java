
// src/Enemies/Thief.java
package Enemies;

import SharedData.GameSettings;

public class Thief extends Enemies {

    // Constructor with comments for each variable
    public Thief() {
        super(
            /* name: The type or identifier of the enemy */ "Thief",
            /* level: The enemy's experience or difficulty level */ 1,
            /* hitPoints: The enemy's health value */ 22,
            /* strength: Physical attack power */ 6,
            /* charisma: Social or persuasive ability */ 4,
            /* agility: Speed and evasion capability */ 10,
            /* intelligence: Problem-solving or magical ability */ 5,
            /* wisdom: Decision-making or resistance to effects */ 2,
            /* imagePath: Path to the enemy's image asset */ GameSettings.MonsterImagePath + "Thief.png"
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
        // Thief relies more on agility for attack
        return (int) ((getStrength() * 1.0) + (getAgility() * 1.0));
    }

    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    @Override
    public String toString() {
        return "Thief{" +
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
