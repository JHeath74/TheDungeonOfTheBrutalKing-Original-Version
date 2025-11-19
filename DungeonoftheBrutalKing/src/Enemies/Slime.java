
// src/Enemies/Slime.java
package Enemies;

import SharedData.GameSettings;
import DungeonoftheBrutalKing.MainGameScreen;
import Status.PoisonStatus;
import DungeonoftheBrutalKing.Character;

public class Slime extends Enemies {

    // Constructor with comments for each variable
    public Slime() {
        super(
            /* name: The type or identifier of the enemy */ "Slime",
            /* level: The enemy's experience or difficulty level */ 1,
            /* hitPoints: The enemy's health value */ 15,
            /* strength: Physical attack power */ 3,
            /* charisma: Social or persuasive ability */ 1,
            /* agility: Speed and evasion capability */ 2,
            /* intelligence: Problem-solving or magical ability */ 1,
            /* wisdom: Decision-making or resistance to effects */ 1,
            /* imagePath: Path to the enemy's image asset */ GameSettings.MonsterImagePath + "Slime.png",
            /* isMagicUser: Slime is not a magic user */ false,
            /* spellStrength: Slime has no spell strength */ 0
        );
        this.level = 1;
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
    public int attack() {
        return (int) ((getStrength() * 1.2) + (getAgility() * 0.4));
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

    // Attack with a chance to apply poison status effect
    public int attack(Character target) {
        boolean poisonApplied = Math.random() < 0.2; // 20% chance
        if (poisonApplied) {
            MainGameScreen.appendToMessageTextPane(getName() + " attacks and applies poison!");
            target.addStatus(new PoisonStatus(2)); // Poison for 2 turns
        } else {
            MainGameScreen.appendToMessageTextPane(getName() + " attacks.");
        }
        return attack();
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
        return "Slime{" +
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
