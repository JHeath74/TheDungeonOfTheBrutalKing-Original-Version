package Enemies;

import SharedData.GameSettings;

public class Assassin extends Enemies {

	public Assassin() {
        super(
            /* name: The type or identifier of the enemy */ "Assassin",
            /* level: The enemy's experience or difficulty level */ 1,
            /* hitPoints: The enemy's health value */ 30,
            /* strength: Physical attack power */ 8,
            /* charisma: Social or persuasive ability */ 5,
            /* agility: Speed and evasion capability */ 7,
            /* intelligence: Problem-solving or magical ability */ 6,
            /* wisdom: Decision-making or resistance to effects */ 3,
            /* imagePath: Path to the enemy's image asset */ GameSettings.MonsterImagePath + "Assassin.png",
            /* isMagicUser: Assassin is not a magic user */ false,
            /* spellStrength: Assassin has no spell strength */ 0
        );
    }

    @Override
    public void takeDamage(int damage) {
        int dodgeChance = 20;
        if (Math.random() * 100 < dodgeChance) {
            System.out.println(getName() + " dodged the attack!");
            return;
        }
        setHitPoints(getHitPoints() - defend(damage));
        if (getHitPoints() < 0) {
            setHitPoints(0);
        }
        if (isDead()) {
            System.out.println(getName() + " has died.");
        }
    }
    
@Override
public int getGoldReward(int level, int combatCount) {
    int baseGoldPerLevel = 10;
    double decayRate = 0.9; // 10% less gold per combat
    double gold = level * baseGoldPerLevel * Math.pow(decayRate, combatCount);
    return (int) Math.max(gold, 1); // Ensure at least 1 gold
}


    @Override
    public int defend(int damage) {
        double reduction = getAgility() * 0.07;
        int reducedDamage = (int) (damage * (1 - reduction));
        return Math.max(reducedDamage, 0);
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    // Implement the required attack() method
    @Override
    public int attack() {
        boolean critical = Math.random() < 0.2;
        int base = (int) ((getStrength() * 1.5) + (getAgility() * 0.5));
        return critical ? base * 2 : base;
    }

    // This is a helper, not an override
    public int getAttackDamage() {
        return attack();
    }

    @Override
    public String getImagePath() {
        if (getHitPoints() < 10) {
            return GameSettings.MonsterImagePath + "Assassin_injured.png";
        }
        return super.getImagePath();
    }

    @Override
    public String toString() {
        return "Assassin{" +
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
