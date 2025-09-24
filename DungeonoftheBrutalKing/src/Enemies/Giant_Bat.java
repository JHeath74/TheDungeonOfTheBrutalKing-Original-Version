
package Enemies;

import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.GameSettings;

public class Giant_Bat extends Enemies {

    // Constructor with comments for each variable
    public Giant_Bat() {
        super(
            /* name: The type or identifier of the enemy */ "Giant Bat",
            /* level: The enemy's experience or difficulty level */ 1,
            /* hitPoints: The enemy's health value */ 30,
            /* strength: Physical attack power */ 8,
            /* charisma: Social or persuasive ability */ 5,
            /* agility: Speed and evasion capability */ 7,
            /* intelligence: Problem-solving or magical ability */ 6,
            /* wisdom: Decision-making or resistance to effects */ 3,
            /* imagePath: Path to the enemy's image asset */ GameSettings.MonsterImagePath + "Giant_bat.png",
            /* isMagicUser: Giant Bat is not a magic user */ false,
            /* spellStrength: Giant Bat has no spell strength */ 0
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
        return (int) ((getStrength() * 1.5) + (getAgility() * 0.5));
    }

    @Override
    public String getImagePath() {
        return super.getImagePath();
    }
    

@Override
public int defend(int incomingDamage) {
    int baseDefense = 5;
    int agility = getAgility();
    int reductionPercent = baseDefense + agility;
    if (reductionPercent > 60) reductionPercent = 60; // Cap at 60%
    int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
    MainGameScreen.appendToMessageTextPane(getName() + " dodges and reduces damage to " + reducedDamage + ".");
    return reducedDamage;
}


    @Override
    public String toString() {
        return "Giant_Bat{" +
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
