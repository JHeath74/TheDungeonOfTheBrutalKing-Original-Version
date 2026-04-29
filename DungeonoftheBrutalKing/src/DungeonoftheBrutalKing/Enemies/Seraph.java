
// src/DungeonoftheBrutalKing/Enemies/Seraph.java
package DungeonoftheBrutalKing.Enemies;

import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.RandomFactory;

public class Seraph extends Enemies {
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

    public Seraph() {
        this(randomLevel(), 7, 15, 8, 13, 15, 8, 30);
    }

    public Seraph(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality, int spellStrength) {
        super(
            "Seraph",
            level,
            (level * 5) + (vitality * 7),
            strength,
            charisma,
            agility,
            intelligence,
            wisdom,
            GameSettings.getMonsterImagePath() + "Seraph.png",
            true,
            vitality
        );
        this.level = level;
        this.strength = strength;
        this.charisma = charisma;
        this.agility = agility;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.vitality = vitality;
        this.hitPoints = (level * 5) + (vitality * 7);
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
    public void takeDamage(int damage, MainGameScreen mainGameScreen) {
        int dodgeChance = 10;
        if (RandomFactory.gameplayDouble() * 100 < dodgeChance) {
            MainGameScreen.appendToMessageTextPane(getName() + " evades the attack with celestial grace!");
            return;
        }
        setHitPoints(getHitPoints() - defend(damage, mainGameScreen));
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " falls, celestial fire extinguished.");
    }

    @Override
    public int attack() {
        boolean critical = RandomFactory.gameplayDouble() < 0.18;
        int base = (int) ((getStrength() * 0.8) + (getWisdom() * 2.4) + getSpellStrength());
        int damage = critical ? base * 2 : base;
        return damage;
    }

    @Override
    public int attack(MainGameScreen mainGameScreen) {
        int damage = attack();
        MainGameScreen.appendToMessageTextPane(getName() + " unleashes radiant energy, dealing " + damage + " damage!" + (damage > ((getStrength() * 0.8) + (getWisdom() * 2.4) + getSpellStrength()) ? " Critical hit!" : ""));
        return damage;
    }

    @Override
    public int defend(int incomingDamage, MainGameScreen mainGameScreen) {
        int baseDefense = 7;
        int reductionPercent = (baseDefense + getWisdom()) / 2;
        if (reductionPercent > 80) reductionPercent = 80;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " conjures a fiery barrier, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public String getImagePath() {
        if (getHitPoints() < 15) {
            return GameSettings.getMonsterImagePath() + "Seraph_injured.png";
        }
        return super.getImagePath();
    }

    @Override
    public int getExperienceReward() {
        int base = level * 15;
        int offset = (int) ((RandomFactory.gameplayDouble() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 8;
        int offset = (int) ((RandomFactory.gameplayDouble() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    private static int randomLevel() {
        return 1 + RandomFactory.gameplayInt(5);
    }

    @Override
    public int getAlignmentImpact() {
        int offset = (int) (RandomFactory.gameplayDouble() * ((level / 5) * 2 + 1)) - (level / 5);
        return -(level + offset);
    }

    @Override
    public Alignment getAlignment() {
        return alignment;
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    @Override
    public String toString() {
        return "Seraph{" +
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

    @Override
    public String getClassName() {
        return getName();
    }
}
