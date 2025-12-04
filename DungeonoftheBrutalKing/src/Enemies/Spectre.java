
// src/Enemies/Spectre.java
package Enemies;

import SharedData.GameSettings;
import SharedData.Alignment;
import DungeonoftheBrutalKing.MainGameScreen;
import Status.PoisonStatus;
import DungeonoftheBrutalKing.Character;

public class Spectre extends Enemies {
    private int level;
    private final int strength;
    private final int charisma;
    private final int agility;
    private final int intelligence;
    private final int wisdom;
    private final int vitality;
    private int hitPoints;
    private final Alignment alignment = Alignment.EVIL;

    public Spectre() {
        this(randomLevel(), 7, 8, 9, 10, 8, 6); // Example default stats
    }

    public Spectre(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality) {
        super(
            "Spectre",
            level,
            (level * 7) + (vitality * 5),
            strength,
            charisma,
            agility,
            intelligence,
            wisdom,
            GameSettings.MonsterImagePath + "Spectre.png",
            true // isMagicUser
        );
        this.level = level;
        this.strength = strength;
        this.charisma = charisma;
        this.agility = agility;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.vitality = vitality;
        this.hitPoints = (level * 7) + (vitality * 5);
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
        int dodgeChance = 18;
        if (Math.random() * 100 < dodgeChance) {
            MainGameScreen.appendToMessageTextPane(getName() + " phases and dodges the attack!");
            return;
        }
        setHitPoints(getHitPoints() - defend(damage));
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " dissipates into mist.");
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
        // Optionally, recalculate hitPoints if level changes:
        // this.hitPoints = (level * 7) + (vitality * 5);
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    // Spectre attack applies poison status with 30% chance
    public int attack(Character target) {
        boolean critical = Math.random() < 0.18;
        int base = (int) ((getIntelligence() * 1.5) + (getAgility() * 1.2));
        int damage = critical ? base * 2 : base;
        boolean poisonApplied = Math.random() < 0.30;
        if (poisonApplied) {
            MainGameScreen.appendToMessageTextPane(getName() + " attacks and applies spectral poison!");
            target.addStatus(new PoisonStatus(3));
        } else {
            MainGameScreen.appendToMessageTextPane(getName() + " attacks for " + damage + " damage!");
        }
        return damage;
    }

    @Override
    public int attack() {
        boolean critical = Math.random() < 0.18;
        int base = (int) ((getIntelligence() * 1.5) + (getAgility() * 1.2));
        int damage = critical ? base * 2 : base;
        MainGameScreen.appendToMessageTextPane(getName() + " attacks for " + damage + " damage!");
        return damage;
    }

    @Override
    public int defend(int incomingDamage) {
        int baseDefense = 12;
        int reductionPercent = (baseDefense + getAgility()) / 2;
        if (reductionPercent > 85) reductionPercent = 85;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " becomes ethereal, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public String getImagePath() {
        if (getHitPoints() < 10) {
            return GameSettings.MonsterImagePath + "Spectre_injured.png";
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
        return 5 + (int) (Math.random() * 2); // Spectre is mid-high level
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
        return "Spectre{" +
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
