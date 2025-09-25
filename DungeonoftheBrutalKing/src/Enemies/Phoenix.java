
package Enemies;

import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.GameSettings;
import Status.FireStatus;
import DungeonoftheBrutalKing.Charecter;

/**
 * Phoenix enemy class.
 * The constructor sets up all Phoenix attributes, including name, level, stats, image path, magic user flag, and spell strength.
 */
public class Phoenix extends Enemies {

    /**
     * Constructs a Phoenix enemy with predefined attributes.
     * @see Enemies#Enemies(String, int, int, int, int, int, int, int, String, boolean, int)
     */
    public Phoenix() {
        super(
            /* name: The type or identifier of the enemy */ "Phoenix",
            /* level: The enemy's experience or difficulty level */ 1,
            /* hitPoints: The enemy's health value */ 30,
            /* strength: Physical attack power */ 8,
            /* charisma: Social or persuasive ability */ 5,
            /* agility: Speed and evasion capability */ 7,
            /* intelligence: Problem-solving or magical ability */ 6,
            /* wisdom: Decision-making or resistance to effects */ 3,
            /* imagePath: Path to the enemy's image asset */ GameSettings.MonsterImagePath + "Phoenix.png",
            /* isMagicUser: Phoenix is a magic user */ true,
            /* spellStrength: Phoenix has spell strength */ 10
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
        return (int) ((getStrength() * 1.5) + (getAgility() * 0.5) + getSpellStrength());
    }

    // Default attack (no target)
    @Override
    public int attack() {
        int phoenixFlameAttack = 12 + getStrength() + getSpellStrength();
        MainGameScreen.appendToMessageTextPane(getName() + " attacks with Phoenix Flame.");
        return phoenixFlameAttack;
    }

    // Attack with fire status effect
    public int attack(Charecter target) {
        int phoenixFlameAttack = 12 + getStrength() + getSpellStrength();
        boolean fireStatusApplied = Math.random() < 0.3; // 30% chance
        if (fireStatusApplied) {
            MainGameScreen.appendToMessageTextPane(getName() + " attacks with Phoenix Flame and applies fire status!");
            target.addStatus(new FireStatus());
        } else {
            MainGameScreen.appendToMessageTextPane(getName() + " attacks with Phoenix Flame.");
        }
        return phoenixFlameAttack;
    }

    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    @Override
    public String toString() {
        return "Phoenix{" +
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

    @Override
    public int defend(int incomingDamage) {
        int baseDefense = 12;
        int agility = getAgility();
        int reductionPercent = (baseDefense + agility) / 2;
        if (reductionPercent > 80) reductionPercent = 80;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        System.out.println(getName() + " defends and reduces damage to " + reducedDamage + ".");
        return reducedDamage;
    }
}
