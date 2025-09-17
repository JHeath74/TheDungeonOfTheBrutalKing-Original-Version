
// src/Enemies/Waith.java
package Enemies;

import SharedData.GameSettings;

public class Wraith extends Enemies {

    // Constructor with comments for each variable
    public Wraith() {
        super(
            /* name: The type or identifier of the enemy */ "Waith",
            /* level: The enemy's experience or difficulty level */ 1,
            /* hitPoints: The enemy's health value */ 26,
            /* strength: Physical attack power */ 5,
            /* charisma: Social or persuasive ability */ 7,
            /* agility: Speed and evasion capability */ 6,
            /* intelligence: Problem-solving or magical ability */ 8,
            /* wisdom: Decision-making or resistance to effects */ 4,
            /* imagePath: Path to the enemy's image asset */ GameSettings.MonsterImagePath + "Waith.png"
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
        // Waith relies more on intelligence and charisma for attack
        return (int) ((getIntelligence() * 1.0) + (getCharisma() * 1.0));
    }

    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    @Override
    public String toString() {
        return "Waith{" +
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
