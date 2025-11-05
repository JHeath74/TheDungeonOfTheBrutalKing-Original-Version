
package Enemies;

import SharedData.GameSettings;
import DungeonoftheBrutalKing.MainGameScreen;

public class Ghost extends Enemies {

    // Constructor with comments for each variable
    public Ghost() {
        super(
            /* name: The type or identifier of the enemy */ "Ghost",
            /* level: The enemy's experience or difficulty level */ 3,
            /* hitPoints: The enemy's health value */ 20,
            /* strength: Physical attack power */ 5,
            /* charisma: Social or persuasive ability */ 4,
            /* agility: Speed and evasion capability */ 10,
            /* intelligence: Problem-solving or magical ability */ 8,
            /* wisdom: Decision-making or resistance to effects */ 7,
            /* imagePath: Path to the enemy's image asset */ GameSettings.MonsterImagePath + "Ghost.png",
            /* isMagicUser: Ghost is not a magic user */ false,
            /* spellStrength: Ghost has no spell strength */ 0
        );
        this.level = 3;
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
    public int getAttackDamage() {
        return (int) ((getStrength() * 1.2) + (getAgility() * 0.8));
    }

    // Implementation of the abstract attack() method
    @Override
    public int attack() {
        int damage = getAttackDamage();
        MainGameScreen.appendToMessageTextPane(getName() + " attacks with a chilling touch for " + damage + " damage!");
        return damage;
    }

    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    @Override
    public String toString() {
        return "Ghost{" +
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
}
