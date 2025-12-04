
// src/Enemies/Mage.java
package Enemies;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.Alignment;
import SharedData.GameSettings;
import Status.Status;

public class Mage extends Enemies {
    private int level;
    private final int strength;
    private final int charisma;
    private final int agility;
    private final int intelligence;
    private final int wisdom;
    private final int vitality;
    private int hitPoints;
    private final int spellStrength;
    private final Alignment alignment = Alignment.EVIL;

    public Mage() {
        this(randomLevel(), 5, 6, 8, 10, 7, 6, 12); // Example default stats
    }

    public Mage(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality, int spellStrength) {
        super(
            "Mage",
            level,
            (level * 6) + (vitality * 7),
            strength,
            charisma,
            agility,
            intelligence,
            wisdom,
            GameSettings.MonsterImagePath + "Mage.png",
            true
        );
        this.level = level;
        this.strength = strength;
        this.charisma = charisma;
        this.agility = agility;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.vitality = vitality;
        this.hitPoints = (level * 6) + (vitality * 7);
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
            MainGameScreen.appendToMessageTextPane(getName() + " teleports and dodges the attack!");
            return;
        }
        setHitPoints(getHitPoints() - defend(damage));
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " collapses in a burst of arcane energy.");
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
        // Optionally, recalculate hitPoints if level changes:
        // this.hitPoints = (level * 6) + (vitality * 7);
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    @Override
    public int attack() {
        boolean critical = Math.random() < 0.15;
        int base = (int) ((getIntelligence() * 1.5) + (getAgility() * 0.7) + getSpellStrength());
        int damage = critical ? base * 2 : base;
        MainGameScreen.appendToMessageTextPane(getName() + " casts a spell for " + damage + " damage!");
        return damage;
    }

    public int attack(Character target, Status statusEffect) {
        int spellDamage = attack();
        if (Math.random() < 0.25) {
            MainGameScreen.appendToMessageTextPane(getName() + " unleashes a magical effect! The target is afflicted!");
            statusEffect.applyEffect(target);
        }
        return spellDamage;
    }

    @Override
    public int defend(int incomingDamage) {
        int baseDefense = 10;
        int reductionPercent = (baseDefense + getAgility()) / 2;
        if (reductionPercent > 80) reductionPercent = 80;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " conjures a shield, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public String getImagePath() {
        if (getHitPoints() < 12) {
            return GameSettings.MonsterImagePath + "Mage_injured.png";
        }
        return super.getImagePath();
    }

    @Override
    public int getExperienceReward() {
        int base = level * 11;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 6;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    private static int randomLevel() {
        return 5 + (int) (Math.random() * 3); // Example: Mage is mid-level
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
        return "Mage{" +
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
