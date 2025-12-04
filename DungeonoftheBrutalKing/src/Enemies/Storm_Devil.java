
// src/Enemies/Storm_Devil.java
package Enemies;

import SharedData.GameSettings;
import SharedData.Alignment;
import DungeonoftheBrutalKing.MainGameScreen;
import Status.StunStatus;
import DungeonoftheBrutalKing.Character;

public class Storm_Devil extends Enemies {
    private int level;
    private final int strength;
    private final int charisma;
    private final int agility;
    private final int intelligence;
    private final int wisdom;
    private final int vitality;
    private int hitPoints;
    private final Alignment alignment = Alignment.EVIL;

    public Storm_Devil() {
        this(randomLevel(), 8, 5, 7, 9, 6, 7); // Example default stats
    }

    public Storm_Devil(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality) {
        super(
            "Storm Devil",
            level,
            (level * 7) + (vitality * 6),
            strength,
            charisma,
            agility,
            intelligence,
            wisdom,
            GameSettings.MonsterImagePath + "Storm Devil.png",
            true // isMagicUser
        );
        this.level = level;
        this.strength = strength;
        this.charisma = charisma;
        this.agility = agility;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.vitality = vitality;
        this.hitPoints = (level * 7) + (vitality * 6);
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

    @Override
    public void takeDamage(int damage) {
        int dodgeChance = 16;
        if (Math.random() * 100 < dodgeChance) {
            MainGameScreen.appendToMessageTextPane(getName() + " crackles and dodges the attack!");
            return;
        }
        setHitPoints(getHitPoints() - defend(damage));
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " is struck down by its own storm.");
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
        // Optionally, recalculate hitPoints if level changes:
        // this.hitPoints = (level * 7) + (vitality * 6);
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    // Storm Devil attack applies stun status with 25% chance
    public int attack(Character target) {
        boolean critical = Math.random() < 0.20;
        int base = (int) ((getIntelligence() * 1.4) + (getStrength() * 1.2) + (getAgility() * 1.0));
        int damage = critical ? base * 2 : base;
        boolean stunApplied = Math.random() < 0.25;
        if (stunApplied) {
            MainGameScreen.appendToMessageTextPane(getName() + " unleashes a thunderbolt and stuns the target!");
            target.addStatus(new StunStatus(1));
        } else {
            MainGameScreen.appendToMessageTextPane(getName() + " attacks with lightning for " + damage + " damage!");
        }
        return damage;
    }

    @Override
    public int attack() {
        boolean critical = Math.random() < 0.20;
        int base = (int) ((getIntelligence() * 1.4) + (getStrength() * 1.2) + (getAgility() * 1.0));
        int damage = critical ? base * 2 : base;
        MainGameScreen.appendToMessageTextPane(getName() + " attacks with lightning for " + damage + " damage!");
        return damage;
    }

    @Override
    public int defend(int incomingDamage) {
        int baseDefense = 12;
        int reductionPercent = (baseDefense + getAgility()) / 2;
        if (reductionPercent > 85) reductionPercent = 85;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " is surrounded by a storm, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public String getImagePath() {
        if (getHitPoints() < 15) {
            return GameSettings.MonsterImagePath + "Storm Devil_injured.png";
        }
        return super.getImagePath();
    }

    @Override
    public int getExperienceReward() {
        int base = level * 13;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 7;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    private static int randomLevel() {
        return 7 + (int) (Math.random() * 2); // Storm Devil is high-level
    }

    @Override
    public int getAlignmentImpact() {
        int offset = (int) (Math.random() * ((level / 5) * 2 + 1)) - (level / 5);
        return level + offset;
    }

    @Override
    public Alignment getAlignment() {
        return alignment;
    }

    @Override
    public String toString() {
        return "Storm_Devil{" +
                "name='" + getName() + '\'' +
                ", level=" + getLevel() +
                ", hitPoints=" + getHitPoints() +
                ", strength=" + getStrength() +
                ", charisma=" + getCharisma() +
                ", agility=" + getAgility() +
                ", intelligence=" + getIntelligence() +
                ", wisdom=" + getWisdom() +
                ", vitality=" + getVitality() +
                ", imagePath='" + getImagePath() + '\'' +
                ", isMagicUser=" + isMagicUser() +
                '}';
    }
}
