
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
            /* imagePath: Path to the enemy's image asset */ GameSettings.MonsterImagePath + "Wizard.png",
            /* isMagicUser: Wizard is a magic user */ true,
            /* spellStrength: Wizard's spell strength */ 7
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
        // Wizard relies more on intelligence, wisdom, and spell strength for attack
        return (int) ((getIntelligence() * 1.2) + (getWisdom() * 0.8) + getSpellStrength());
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
                ", isMagicUser=" + isMagicUser() +
                ", spellStrength=" + getSpellStrength() +
                '}';
    }
}
