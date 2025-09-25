
package Enemies;

import SharedData.GameSettings;
import Status.IceStatus;
import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;
import java.util.Random;

public class Ice_Demon extends Enemies {

	public Ice_Demon() {
        super(
            /* name: The type or identifier of the enemy */ "Ice Demon",
            /* level: The enemy's experience or difficulty level */ 1,
            /* hitPoints: The enemy's health value */ 30,
            /* strength: Physical attack power */ 8,
            /* charisma: Social or persuasive ability */ 5,
            /* agility: Speed and evasion capability */ 7,
            /* intelligence: Problem-solving or magical ability */ 6,
            /* wisdom: Decision-making or resistance to effects */ 3,
            /* imagePath: Path to the enemy's image asset */ GameSettings.MonsterImagePath + "Ice_Demon.png",
            /* isMagicUser: Ice Demon is not a magic user */ false,
            /* spellStrength: Ice Demon has no spell strength */ 0
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

    // Default attack (required by Enemies)
    @Override
    public int attack() {
        return (int) ((getStrength() * 1.5) + (getAgility() * 0.5));
    }

    // Attack with frozen status effect
    public int attack(Charecter target) {
        int baseAttack = attack();
        if (new Random().nextInt(100) < 25) {
            MainGameScreen.appendToMessageTextPane(getName() + " uses icy power! The target is frozen!");
            target.addStatus(new IceStatus());
        }
        return baseAttack;
    }

    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    public int defend(int incomingDamage) {
        int baseDefense = 10;
        int agility = getAgility();
        int reductionPercent = (baseDefense + agility) / 2;
        if (reductionPercent > 80) reductionPercent = 80;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " defends and reduces damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public String toString() {
        return "Ice_Demon{" +
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
