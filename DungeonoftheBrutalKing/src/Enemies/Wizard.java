
// src/Enemies/Wizard.java
package Enemies;

import SharedData.GameSettings;

public class Wizard extends Enemies {

    // Constructor with comments for each variable
    public Wizard() {
        super(
            /* name: The type or identifier of the enemy */ "Wizard",
            /* level: The enemy's experience or difficulty level */ 1,
            /* hitPoints: The enemy's health value */ 24,
            /* strength: Physical attack power */ 4,
            /* charisma: Social or persuasive ability */ 6,
            /* agility: Speed and evasion capability */ 5,
            /* intelligence: Problem-solving or magical ability */ 10,
            /* wisdom: Decision-making or resistance to effects */ 8,
            /* imagePath: Path to the enemy's image asset */ GameSettings.MonsterImagePath + "Wizard.png"
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
        // Wizard relies more on intelligence and wisdom for attack
        return (int) ((getIntelligence() * 1.2) + (getWisdom() * 0.8));
    }

    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    @Override
    public String toString() {
        return "Wizard{" +
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
