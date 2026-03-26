
// src/Enemies/Phoenix.java
package Enemies;

import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.Alignment;
import SharedData.GameSettings;
import Status.FireStatus;
import DungeonoftheBrutalKing.Charecter;

public class Phoenix extends Enemies {
    private int level;
    private final int strength;
    private final int charisma;
    private final int agility;
    private final int intelligence;
    private final int wisdom;
    private final int vitality;
    private int hitPoints;
    private final int spellStrength;
    private final Alignment alignment = Alignment.GOOD;

    public Phoenix() {
        this(randomLevel(), 8, 5, 7, 6, 3, 6, 10); // Example default stats
    }

  
    	public Phoenix(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality, int spellStrength) {
    	    super(
    	        "Phoenix",
    	        level,
    	        (level * 6) + (vitality * 6),
    	        strength,
    	        charisma,
    	        agility,
    	        intelligence,
    	        wisdom,
    	        GameSettings.MonsterImagePath + "Phoenix.png",
    	        true // isMagicUser
    	    );
    	    this.level = level;
    	    this.strength = strength;
    	    this.charisma = charisma;
    	    this.agility = agility;
    	    this.intelligence = intelligence;
    	    this.wisdom = wisdom;
    	    this.vitality = vitality;
    	    this.hitPoints = (level * 6) + (vitality * 6);
    	    this.spellStrength = spellStrength;
    	}

    public int getLevel() { return level; }
    public int getStrength() { return strength; }
    public int getCharisma() { return charisma; }
    public int getAgility() { return agility; }
    public int getIntelligence() { return intelligence; }
    public int getWisdom() { return wisdom; }
    public int getVitality() { return vitality; }
    public int getHitPoints() { return hitPoints; }
    public void setHitPoints(int hitPoints) { this.hitPoints = Math.max(hitPoints, 0); }
    public int getSpellStrength() { return spellStrength; }

    @Override
    public void takeDamage(int damage) {
        int dodgeChance = 12;
        if (Math.random() * 100 < dodgeChance) {
            MainGameScreen.appendToMessageTextPane(getName() + " bursts into flame and dodges the attack!");
            return;
        }
        setHitPoints(getHitPoints() - defend(damage));
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " is reduced to ashes.");
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
        // Optionally, recalculate hitPoints if level changes:
        // this.hitPoints = (level * 6) + (vitality * 6);
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    // Phoenix attack applies fire status with 30% chance
    public int attack(Charecter target) {
        boolean critical = Math.random() < 0.18;
        int base = (int) ((getStrength() * 1.2) + (getAgility() * 0.8) + (getSpellStrength() * 1.5));
        int damage = critical ? base * 2 : base;
        boolean fireStatusApplied = Math.random() < 0.3;
        if (fireStatusApplied) {
            MainGameScreen.appendToMessageTextPane(getName() + " engulfs the target in flames and applies fire status!");
            target.addStatus(new FireStatus());
        } else {
            MainGameScreen.appendToMessageTextPane(getName() + " attacks with blazing fire for " + damage + " damage!");
        }
        return damage;
    }

    @Override
    public int attack() {
        boolean critical = Math.random() < 0.18;
        int base = (int) ((getStrength() * 1.2) + (getAgility() * 0.8) + (getSpellStrength() * 1.5));
        int damage = critical ? base * 2 : base;
        MainGameScreen.appendToMessageTextPane(getName() + " attacks with blazing fire for " + damage + " damage!");
        return damage;
    }

    @Override
    public int defend(int incomingDamage) {
        int baseDefense = 12;
        int reductionPercent = (baseDefense + getAgility()) / 2;
        if (reductionPercent > 80) reductionPercent = 80;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " defends with fiery wings, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public String getImagePath() {
        if (getHitPoints() < 12) {
            return GameSettings.MonsterImagePath + "Phoenix_injured.png";
        }
        return super.getImagePath();
    }

    @Override
    public int getExperienceReward() {
        int base = level * 12;
        int offset = (int) ((Math.random() * (2 * level * 8 + 1)) - (level * 8));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 6;
        int offset = (int) ((Math.random() * (2 * level * 8 + 1)) - (level * 8));
        return Math.max(base + offset, 0);
    }

    private static int randomLevel() {
        return 8 + (int) (Math.random() * 2); // Phoenix is high-level
    }

    @Override
    public int getAlignmentImpact() {
        int offset = (int) (Math.random() * ((level / 5) * 2 + 1)) - (level / 5);
        return -level + offset;
    }

    @Override
    public Alignment getAlignment() {
        return alignment;
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
                ", vitality=" + getVitality() +
                ", spellStrength=" + getSpellStrength() +
                ", imagePath='" + getImagePath() + '\'' +
                ", isMagicUser=" + isMagicUser() +
                '}';
    }
}
