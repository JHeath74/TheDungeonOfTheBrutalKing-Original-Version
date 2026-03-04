
// src/Enemies/Seraph.java
package Enemies;

import SharedData.Alignment;
import DungeonoftheBrutalKing.MainGameScreen;

public class Seraph extends Enemies {
    private int level;
    private final int strength;
    private final int charisma;
    private final int agility;
    private final int intelligence;
    private final int wisdom;
    private final int vitality;
    private int hitPoints;
    private final Alignment alignment = Alignment.GOOD;

    public Seraph() {
        this(randomLevel(), 7, 15, 8, 13, 15, 8);
    }

    public Seraph(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality) {
        // Use only params/literals in super(...); don’t call instance methods here.
        // NOTE: This assumes an Enemies constructor matching this signature exists in your project.
        super(
                "Seraph",
                level,
                (level * 5) + (vitality * 7),
                strength,
                agility,
                intelligence,
                wisdom,
                0,          // imagePath placeholder (adjust/remove to match Enemies ctor)
                "",         // isMagicUser placeholder (adjust/remove to match Enemies ctor)
                false,      // undead flag literal (no instance method call in ctor chaining)
                vitality
        );

        try {
            super.setMagicUser(true);
        } catch (Throwable ignored) {
        }

        this.level = level;
        this.strength = strength;
        this.charisma = charisma;
        this.agility = agility;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.vitality = vitality;
        this.hitPoints = (level * 5) + (vitality * 7);
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
        setHitPoints(getHitPoints() - damage);
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " falls, celestial fire extinguished.");
    }

    @Override
    public int getSpellStrength() {
        return (getLevel() * 2) + (getWisdom() * 2) + getIntelligence();
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    @Override
    public int attack() {
        return (int) ((getStrength() * 0.8) + (getWisdom() * 2.4) + getSpellStrength());
    }

    public int defend(int incomingDamage) {
        int baseDefense = 7;
        int reductionPercent = (baseDefense + getWisdom()) / 2;
        if (reductionPercent > 80) reductionPercent = 80;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " conjures a fiery barrier, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public int getExperienceReward() {
        int base = level * 15;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 8;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    private static int randomLevel() {
        return 1 + (int) (Math.random() * 5);
    }

    @Override
    public int getAlignmentImpact() {
        int offset = (int) (Math.random() * ((level / 5) * 2 + 1)) - (level / 5);
        return -(level + offset);
    }

    @Override
    public Alignment getAlignment() {
        return alignment;
    }

    @Override
    public String getClassName() {
        return "Seraph";
    }
}
