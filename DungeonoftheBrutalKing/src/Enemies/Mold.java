package Enemies;

import SharedData.GameSettings;
import DungeonoftheBrutalKing.MainGameScreen;

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
            /* imagePath: Path to the enemy's image asset */ GameSettings.MonsterImagePath + "Mold.png",
            /* isMagicUser: Mold is not a magic user */ false,
            /* spellStrength: Mold has no spell strength */ 0
        );
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
        return (int) ((getStrength() * 1.2) + (getAgility() * 0.3));
    }

    // Defend method: reduces incoming damage based on agility and a base defense
    public int defend(int incomingDamage) {
        int baseDefense = 10;
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
                ", isMagicUser=" + isMagicUser() +
                ", spellStrength=" + getSpellStrength() +
                '}';
    }
}
