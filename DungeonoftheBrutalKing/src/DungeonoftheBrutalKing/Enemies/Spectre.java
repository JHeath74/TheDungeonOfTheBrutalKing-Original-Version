
// src/DungeonoftheBrutalKing/Enemies/Spectre.java
package DungeonoftheBrutalKing.Enemies;

import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.RandomFactory;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.Status.PoisonStatus;
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
        this(randomLevel(), 7, 8, 9, 10, 8, 6);
        this.undead = true;
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
            GameSettings.getMonsterImagePath() + "Spectre.png",
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
        this.hitPoints = (level * 7) + (vitality * 5);
        this.undead = true;
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
        int dodgeChance = 18;
        if (RandomFactory.gameplayDouble() * 100 < dodgeChance) {
            MainGameScreen.appendToMessageTextPane(getName() + " phases and dodges the attack!");
            return;
        }
        setHitPoints(getHitPoints() - defend(damage, mainGameScreen));
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " dissipates into mist.");
    }

    @Override
    public int defend(int incomingDamage, MainGameScreen mainGameScreen) {
        int baseDefense = 12;
        int reductionPercent = (baseDefense + getAgility()) / 2;
        if (reductionPercent > 85) reductionPercent = 85;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " becomes ethereal, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    public int attack(Character target, MainGameScreen mainGameScreen) {
        boolean critical = RandomFactory.gameplayDouble() < 0.18;
        int base = (int) ((getIntelligence() * 1.5) + (getAgility() * 1.2));
        int damage = critical ? base * 2 : base;
        boolean poisonApplied = RandomFactory.gameplayDouble() < 0.30;
        if (poisonApplied) {
            MainGameScreen.appendToMessageTextPane(getName() + " attacks and applies spectral poison!");
            target.addStatus(new PoisonStatus(3));
        } else {
            MainGameScreen.appendToMessageTextPane(getName() + " attacks for " + damage + " damage!" + (critical ? " Critical hit!" : ""));
        }
        return damage;
    }

    @Override
    public int attack() {
        boolean critical = RandomFactory.gameplayDouble() < 0.18;
        int base = (int) ((getIntelligence() * 1.5) + (getAgility() * 1.2));
        int damage = critical ? base * 2 : base;
        return damage;
    }

    @Override
    public int attack(MainGameScreen mainGameScreen) {
        int damage = attack();
        MainGameScreen.appendToMessageTextPane(getName() + " attacks for " + damage + " damage!");
        return damage;
    }

    @Override
    public String getImagePath() {
        if (getHitPoints() < 10) {
            return GameSettings.getMonsterImagePath() + "Spectre_injured.png";
        }
        return super.getImagePath();
    }

    @Override
    public int getExperienceReward() {
        int base = level * 13;
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
        return 5 + RandomFactory.gameplayInt(2);
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
                ", isUndead=" + isUndead() +
                '}';
    }

    @Override
    public String getClassName() {
        return getName();
    }
}
