
// src/Enemies/Thief.java
package Enemies;

import SharedData.GameSettings;
import DungeonoftheBrutalKing.MainGameScreen;

public class Thief extends Enemies {

    // Constructor with comments for each variable
    public Thief() {
        super(
            /* name: The type or identifier of the enemy */ "Thief",
            /* level: The enemy's experience or difficulty level */ 4,
            /* hitPoints: The enemy's health value */ 22,
            /* strength: Physical attack power */ 6,
            /* charisma: Social or persuasive ability */ 4,
            /* agility: Speed and evasion capability */ 10,
            /* intelligence: Problem-solving or magical ability */ 5,
            /* wisdom: Decision-making or resistance to effects */ 2,
            /* imagePath: Path to the enemy's image asset */ GameSettings.MonsterImagePath + "Thief.png",
            /* isMagicUser: Thief is not a magic user */ false,
            /* spellStrength: Thief has no spell strength */ 0
        );
        this.level = 4;
    }

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

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
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
    public int attack() {
        // Thief relies more on agility for attack
        return (int) ((getStrength() * 1.0) + (getAgility() * 1.0));
    }

    // Defend method: reduces incoming damage based on agility and a base defense
    public int defend(int incomingDamage) {
        int baseDefense = 8;
        int agility = getAgility();
        int reductionPercent = (baseDefense + agility) / 2;
        if (reductionPercent > 80) reductionPercent = 80; // Cap at 80%
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " defends and reduces damage to " + reducedDamage + ".");
        return reducedDamage;
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
                ", isMagicUser=" + isMagicUser() +
                ", spellStrength=" + getSpellStrength() +
                '}';
    }
}
