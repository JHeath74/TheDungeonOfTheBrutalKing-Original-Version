
// src/Enemies/Flame_Demon.java
package Enemies;

import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.GameSettings;
import Status.Fire;
import DungeonoftheBrutalKing.Charecter;

public class Flame_Demon extends Enemies {

	public Flame_Demon() {
	    super(
	        /* name: The type or identifier of the enemy */ "Flame Demon",
	        /* level: The enemy's experience or difficulty level */ 1,
	        /* hitPoints: The enemy's health value */ 30,
	        /* strength: Physical attack power */ 8,
	        /* charisma: Social or persuasive ability */ 5,
	        /* agility: Speed and evasion capability */ 7,
	        /* intelligence: Problem-solving or magical ability */ 6,
	        /* wisdom: Decision-making or resistance to effects */ 3,
	        /* imagePath: Path to the enemy's image asset */ GameSettings.MonsterImagePath + "Flame_Demon.png",
	        /* isMagicUser: Flame Demon is not a magic user */ false,
	        /* spellStrength: Flame Demon has no spell strength */ 0
	    );
	}

    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) setHitPoints(0);
        if (isDead()) System.out.println(getName() + " has died.");
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    @Override
    public int getAttackDamage() {
        return (int) ((getStrength() * 1.5) + (getAgility() * 0.5));
    }

    // Default attack (no target)
    @Override
    public int attack() {
        int fireSwordAttack = 10 + getStrength();
        MainGameScreen.appendToMessageTextPane(getName() + " attacks with Fire Sword.");
        return fireSwordAttack;
    }

    // Attack with fire status effect
    public int attack(Charecter target) {
        int fireSwordAttack = 10 + getStrength();
        boolean fireStatusApplied = Math.random() < 0.3; // 30% chance
        if (fireStatusApplied) {
            MainGameScreen.appendToMessageTextPane(getName() + " attacks with Fire Sword and applies fire status!");
            target.addStatus(new Fire());
        } else {
            MainGameScreen.appendToMessageTextPane(getName() + " attacks with Fire Sword.");
        }
        return fireSwordAttack;
    }

    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    @Override
    public String toString() {
        return "Flame_Demon{" +
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
    public int defend(int incomingDamage) {
        int baseDefense = 10;
        int agility = getAgility();
        int reductionPercent = (baseDefense + agility) / 2;
        if (reductionPercent > 80) reductionPercent = 80;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        System.out.println(getName() + " defends and reduces damage to " + reducedDamage + ".");
        return reducedDamage;
    }
}
