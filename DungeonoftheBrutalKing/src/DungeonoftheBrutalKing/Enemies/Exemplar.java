
// src/DungeonoftheBrutalKing/Enemies/Exemplar.java
package DungeonoftheBrutalKing.Enemies;

import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.RandomFactory;

public class Exemplar extends Enemies {
    private int level;
    private final int strength;
    private final int charisma;
    private final int agility;
    private final int intelligence;
    private final int wisdom;
    private final int vitality;
    private int hitPoints;
    private final Alignment alignment = Alignment.GOOD;

    public Exemplar() {
        this(randomLevel(), 9, 8, 7, 7, 9, 8);
    }

    public Exemplar(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality) {
        super(
            "Exemplar",
            level,
            (level * 5) + (vitality * 7),
            strength,
            charisma,
            agility,
            intelligence,
            wisdom,
            GameSettings.getMonsterImagePath() + "Exemplar.png",
            false,
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
    public void takeDamage(int damage, MainGameScreen mainGameScreen) {
        int blockChance = 15;
        if (RandomFactory.gameplayDouble() * 100 < blockChance) {
            MainGameScreen.appendToMessageTextPane(getName() + " blocks the attack with unwavering virtue!");
            return;
        }
        setHitPoints(getHitPoints() - defend(damage, mainGameScreen));
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " falls, ideal shattered.");
    }

    @Override
    public int defend(int incomingDamage, MainGameScreen mainGameScreen) {
        int baseDefense = 16;
        int reductionPercent = (baseDefense + getWisdom()) / 2;
        if (reductionPercent > 75) reductionPercent = 75;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " sets the standard, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public int attack() {
        boolean critical = RandomFactory.gameplayDouble() < 0.15;
        int base = (int) ((getStrength() * 1.4) + (getWisdom() * 1.2));
        int damage = critical ? base * 2 : base;
        return damage;
    }

    @Override
    public int attack(MainGameScreen mainGameScreen) {
        int damage = attack();
        MainGameScreen.appendToMessageTextPane(getName() + " strikes with exemplary force, dealing " + damage + " damage!" + (damage > ((getStrength() * 1.4) + (getWisdom() * 1.2)) ? " Critical hit!" : ""));
        return damage;
    }

    @Override
    public String getImagePath() {
        if (getHitPoints() < 10) {
            return GameSettings.getMonsterImagePath() + "Exemplar_injured.png";
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
        int base = level * 9;
        int offset = (int) ((RandomFactory.gameplayDouble() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    private static int randomLevel() {
        return 6 + RandomFactory.gameplayInt(3);
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
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    @Override
    public String toString() {
        return "Exemplar{" +
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

    @Override
    public String getClassName() {
        return getName();
    }
}
