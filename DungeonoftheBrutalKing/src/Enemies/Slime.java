
// src/Enemies/Slime.java
package Enemies;

import SharedData.GameSettings;
import SharedData.Alignment;
import DungeonoftheBrutalKing.MainGameScreen;
import Status.PoisonStatus;
import DungeonoftheBrutalKing.Character;

public class Slime extends Enemies {
    private int level;
    private final int strength;
    private final int charisma;
    private final int agility;
    private final int intelligence;
    private final int wisdom;
    private final int vitality;
    private int hitPoints;
    private final Alignment alignment = Alignment.EVIL;

    public Slime() {
        this(randomLevel(), 3, 1, 2, 1, 1, 4); // Example default stats
    }

    public Slime(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality) {
        super(
            "Slime",
            level,
            (level * 4) + (vitality * 3),
            strength,
            charisma,
            agility,
            intelligence,
            wisdom,
            GameSettings.MonsterImagePath + "Slime.png",
            false
        );
        this.level = level;
        this.strength = strength;
        this.charisma = charisma;
        this.agility = agility;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.vitality = vitality;
        this.hitPoints = (level * 4) + (vitality * 3);
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
        int dodgeChance = 10;
        if (Math.random() * 100 < dodgeChance) {
            MainGameScreen.appendToMessageTextPane(getName() + " jiggles and dodges the attack!");
            return;
        }
        setHitPoints(getHitPoints() - defend(damage));
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " dissolves into a puddle.");
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
        // Optionally, recalculate hitPoints if level changes:
        // this.hitPoints = (level * 4) + (vitality * 3);
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    // Slime attack applies poison status with 20% chance
    public int attack(Character target) {
        boolean critical = Math.random() < 0.10;
        int base = (int) ((getStrength() * 1.1) + (getAgility() * 0.8));
        int damage = critical ? base * 2 : base;
        boolean poisonApplied = Math.random() < 0.20;
        if (poisonApplied) {
            MainGameScreen.appendToMessageTextPane(getName() + " splashes and applies poison!");
            target.addStatus(new PoisonStatus(2));
        } else {
            MainGameScreen.appendToMessageTextPane(getName() + " attacks for " + damage + " damage!");
        }
        return damage;
    }

    @Override
    public int attack() {
        boolean critical = Math.random() < 0.10;
        int base = (int) ((getStrength() * 1.1) + (getAgility() * 0.8));
        int damage = critical ? base * 2 : base;
        MainGameScreen.appendToMessageTextPane(getName() + " attacks for " + damage + " damage!");
        return damage;
    }

    @Override
    public int defend(int incomingDamage) {
        int baseDefense = 8;
        int reductionPercent = (baseDefense + getAgility()) / 2;
        if (reductionPercent > 80) reductionPercent = 80;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " defends and reduces damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public String getImagePath() {
        if (getHitPoints() < 5) {
            return GameSettings.MonsterImagePath + "Slime_injured.png";
        }
        return super.getImagePath();
    }

    @Override
    public int getExperienceReward() {
        int base = level * 6;
        int offset = (int) ((Math.random() * (2 * level * 3 + 1)) - (level * 3));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 2;
        int offset = (int) ((Math.random() * (2 * level * 3 + 1)) - (level * 3));
        return Math.max(base + offset, 0);
    }

    private static int randomLevel() {
        return 1 + (int) (Math.random() * 2); // Slime is low-level
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
        return "Slime{" +
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
