
// src/DungeonoftheBrutalKing/Enemies/Flame_Demon.java
package DungeonoftheBrutalKing.Enemies;

import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.RandomFactory;
import DungeonoftheBrutalKing.Status.FireStatus;
import DungeonoftheBrutalKing.Character;

public class Flame_Demon extends Enemies {
    private int level;
    private final int strength;
    private final int charisma;
    private final int agility;
    private final int intelligence;
    private final int wisdom;
    private final int vitality;
    private int hitPoints;
    private final Alignment alignment = Alignment.EVIL;

    public Flame_Demon() {
        this(randomLevel(), 8, 5, 7, 6, 3, 6);
    }

    public Flame_Demon(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality) {
        super(
            "Flame Demon",
            level,
            (level * 5) + (vitality * 7),
            strength,
            charisma,
            agility,
            intelligence,
            wisdom,
            GameSettings.getMonsterImagePath() + "Flame_Demon.png",
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
        int dodgeChance = 10;
        if (RandomFactory.gameplayDouble() * 100 < dodgeChance) {
            MainGameScreen.appendToMessageTextPane(getName() + " vanishes in a burst of flame and dodges the attack!");
            return;
        }
        setHitPoints(getHitPoints() - defend(damage, mainGameScreen));
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " has died in a fiery explosion.");
    }

    @Override
    public int defend(int incomingDamage, MainGameScreen mainGameScreen) {
        int baseDefense = 10;
        int reductionPercent = (baseDefense + getAgility()) / 2;
        if (reductionPercent > 80) reductionPercent = 80;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " defends with a wall of fire, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    public int attack(Character target, MainGameScreen mainGameScreen) {
        int damage = attack(mainGameScreen);
        boolean fireStatusApplied = RandomFactory.gameplayDouble() < 0.3;
        if (fireStatusApplied) {
            MainGameScreen.appendToMessageTextPane(getName() + " engulfs the target in flames!");
            target.addStatus(new FireStatus());
        }
        target.takeDamage(damage);
        return damage;
    }

    @Override
    public int attack() {
        boolean critical = RandomFactory.gameplayDouble() < 0.15;
        int base = (int) ((getStrength() * 1.5) + (getAgility() * 0.7));
        int damage = critical ? base * 2 : base;
        return damage;
    }

    @Override
    public int attack(MainGameScreen mainGameScreen) {
        int damage = attack();
        MainGameScreen.appendToMessageTextPane(getName() + " attacks with a flaming sword for " + damage + " damage!" + (damage > ((getStrength() * 1.5) + (getAgility() * 0.7)) ? " Critical hit!" : ""));
        return damage;
    }

    @Override
    public String getImagePath() {
        if (getHitPoints() < 10) {
            return GameSettings.getMonsterImagePath() + "Flame_Demon_injured.png";
        }
        return super.getImagePath();
    }

    @Override
    public int getExperienceReward() {
        int base = level * 12;
        int offset = (int) ((RandomFactory.gameplayDouble() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 7;
        int offset = (int) ((RandomFactory.gameplayDouble() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    private static int randomLevel() {
        return 4 + RandomFactory.gameplayInt(4);
    }

    @Override
    public int getAlignmentImpact() {
        int offset = (int) (RandomFactory.gameplayDouble() * ((level / 5) * 2 + 1)) - (level / 5);
        return level + offset;
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
        return "Flame_Demon{" +
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
