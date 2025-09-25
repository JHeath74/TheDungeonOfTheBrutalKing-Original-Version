
// src/Enemies/Wraith.java
package Enemies;

import SharedData.GameSettings;

public class Wraith extends Enemies {

    // Constructor with comments for each variable
    public Wraith() {
        super(
            /* name: The type or identifier of the enemy */ "Wraith",
            /* level: The enemy's experience or difficulty level */ 1,
            /* hitPoints: The enemy's health value */ 26,
            /* strength: Physical attack power */ 5,
            /* charisma: Social or persuasive ability */ 7,
            /* agility: Speed and evasion capability */ 6,
            /* intelligence: Problem-solving or magical ability */ 8,
            /* wisdom: Decision-making or resistance to effects */ 4,
            /* imagePath: Path to the enemy's image asset */ GameSettings.MonsterImagePath + "Wraith.png",
            /* isMagicUser: Wraith is a magic user */ true,
            /* spellStrength: Wraith's spell strength */ 5
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
        // Wraith relies more on intelligence and charisma for attack
        return (int) ((getIntelligence() * 1.0) + (getCharisma() * 1.0) + getSpellStrength());
    }

    // Defend method: reduces incoming damage based on agility and a base defense
    public int defend(int incomingDamage) {
        int baseDefense = 8;
        int agility = getAgility();
        int reductionPercent = (baseDefense + agility) / 2;
        if (reductionPercent > 80) reductionPercent = 80; // Cap at 80%
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        System.out.println(getName() + " defends and reduces damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    @Override
    public String toString() {
        return "Wraith{" +
                "name='" + getName() + '\'' +
                ", level=" + getLevel() +
                ", hitPoints=" + getHitPoints() +
                ", strength=" + getStrength() +
                ", charisma=" + getCharisma() +
                ", agility=" + getAgility() +
                ", intelligence=" + getIntelligence() +
                ", wisdom=" + getWisdom() +
                ", imagePath='" + getImagePath() + '\'' +
                ", isMagicUser=" + isMagicUser() +
                ", spellStrength=" + getSpellStrength() +
                '}';
    }
}
