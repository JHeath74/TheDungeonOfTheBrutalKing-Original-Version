
// src/Enemies/Sunblade.java
package Enemies;

import SharedData.GameSettings;
import SharedData.Alignment;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.Character;
import Status.RadiantStatus; // Example status effect

public class Sunblade extends Enemies {
    private int level;
    private final int strength;
    private final int charisma;
    private final int agility;
    private final int intelligence;
    private final int wisdom;
    private final int vitality;
    private int hitPoints;
    private final Alignment alignment = Alignment.GOOD;

    public Sunblade() {
        this(randomLevel(), 9, 8, 8, 7, 8, 7); // Example default stats
    }

    public Sunblade(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality) {
        super(
            "Sunblade",
            level,
            (level * 7) + (vitality * 5),
            strength,
            charisma,
            agility,
            intelligence,
            wisdom,
            GameSettings.MonsterImagePath + "Sunblade.png",
            false // isMagicUser
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
        int dodgeChance = 15;
        if (Math.random() * 100 < dodgeChance) {
            MainGameScreen.appendToMessageTextPane(getName() + " flashes and dodges the attack!");
            return;
        }
        setHitPoints(getHitPoints() - defend(damage));
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " falls, light dimmed.");
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

    // Sunblade attack applies radiant status with 15% chance
    public int attack(Character target) {
        boolean critical = Math.random() < 0.15;
        int base = (int) ((getStrength() * 1.4) + (getAgility() * 1.2));
        int damage = critical ? base * 2 : base;
        boolean radiantApplied = Math.random() < 0.15;
        if (radiantApplied) {
            MainGameScreen.appendToMessageTextPane(getName() + " strikes with radiant light!");
            target.addStatus(new RadiantStatus(2)); // Example: radiant status for 2 rounds
        } else {
            MainGameScreen.appendToMessageTextPane(getName() + " attacks for " + damage + " damage!");
        }
        return damage;
    }

    @Override
    public int attack() {
        boolean critical = Math.random() < 0.15;
        int base = (int) ((getStrength() * 1.4) + (getAgility() * 1.2));
        int damage = critical ? base * 2 : base;
        MainGameScreen.appendToMessageTextPane(getName() + " attacks for " + damage + " damage!");
        return damage;
    }

    @Override
    public int defend(int incomingDamage) {
        int baseDefense = 13;
        int reductionPercent = (baseDefense + getAgility()) / 2;
        if (reductionPercent > 75) reductionPercent = 75;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " shines with valor, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public String getImagePath() {
        if (getHitPoints() < 12) {
            return GameSettings.MonsterImagePath + "Sunblade_injured.png";
        }
        return super.getImagePath();
    }

    @Override
    public int getExperienceReward() {
        int base = level * 15;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 9;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    private static int randomLevel() {
        return 8 + (int) (Math.random() * 2); // Sunblade is high-level
    }

    @Override
    public int getAlignmentImpact() {
        int offset = (int) (Math.random() * ((level / 5) * 2 + 1)) - (level / 5);
        return -3 + offset;
    }

    @Override
    public Alignment getAlignment() {
        return alignment;
    }

    @Override
    public String toString() {
        return "Sunblade{" +
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
