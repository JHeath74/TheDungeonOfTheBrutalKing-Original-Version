
// Whirlwind.java
package Enemies;

import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.GameSettings;

public class Whirlwind extends Enemies {

	public Whirlwind() {
        super(
            /* name: The type or identifier of the enemy */ "Whirlwind",
            /* level: The enemy's experience or difficulty level */ 3,
            /* hitPoints: The enemy's health value */ 28,
            /* strength: Physical attack power */ 7,
            /* charisma: Social or persuasive ability */ 4,
            /* agility: Speed and evasion capability */ 9,
            /* intelligence: Problem-solving or magical ability */ 5,
            /* wisdom: Decision-making or resistance to effects */ 2,
            /* imagePath: Path to the enemy's image asset */ GameSettings.MonsterImagePath + "Whirlwind.png",
            /* isMagicUser: Whirlwind is not a magic user */ false,
            /* spellStrength: Whirlwind has no spell strength */ 0
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
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    @Override
    public int attack() {
        return (int) ((getStrength() * 1.0) + (getAgility() * 1.0));
    }

    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    @Override
    public String toString() {
        return "Whirlwind{" +
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
