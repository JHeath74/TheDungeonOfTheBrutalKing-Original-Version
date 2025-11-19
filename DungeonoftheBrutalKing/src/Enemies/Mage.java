
package Enemies;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.GameSettings;
import Status.Status;

public class Mage extends Enemies {

    // Constructor with comments for each variable
    public Mage() {
        super(
            /* name: The type or identifier of the enemy */ "Mage",
            /* level: The enemy's experience or difficulty level */ 5,
            /* hitPoints: The enemy's health value */ 25,
            /* strength: Physical attack power */ 5,
            /* charisma: Social or persuasive ability */ 6,
            /* agility: Speed and evasion capability */ 8,
            /* intelligence: Problem-solving or magical ability */ 10,
            /* wisdom: Decision-making or resistance to effects */ 7,
            /* imagePath: Path to the enemy's image asset */ GameSettings.MonsterImagePath + "Mage.png",
            /* isMagicUser: Mage is a magic user */ true,
            /* spellStrength: Mage has spell strength */ 12
        );
        this.level = 5;
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

    // Required override: returns attack damage
    @Override
    public int attack() {
        return (int) ((getIntelligence() * 1.5) + (getAgility() * 0.5) + (isMagicUser() ? getSpellStrength() : 0));
    }

    // Custom attack with fireball and status effect
    public void attack(Character target, Status fireStatus) {
        int spellDamage = attack();
        MainGameScreen.appendToMessageTextPane(getName() + " attacks with a fireball for " + spellDamage + " damage!");
        target.takeDamage(spellDamage);
        fireStatus.applyEffect(target);
        MainGameScreen.appendToMessageTextPane(getName() + " attempts to apply " + fireStatus.getName() + " effect!");
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
                ", isMagicUser=" + isMagicUser() +
                ", spellStrength=" + getSpellStrength() +
                '}';
    }
}
