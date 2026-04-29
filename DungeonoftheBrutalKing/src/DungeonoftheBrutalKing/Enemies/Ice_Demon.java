
// src/DungeonoftheBrutalKing/Enemies/Ice_Demon.java
package DungeonoftheBrutalKing.Enemies;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.RandomFactory;
import DungeonoftheBrutalKing.Status.IceStatus;

public class Ice_Demon extends Enemies {
    private int level;
    private final int strength;
    private final int charisma;
    private final int agility;
    private final int intelligence;
    private final int wisdom;
    private final int vitality;
    private int hitPoints;
    private final Alignment alignment = Alignment.EVIL;

    public Ice_Demon() {
        this(randomLevel(), 9, 6, 8, 7, 4, 7);
    }

    public Ice_Demon(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality) {
        super(
            "Ice Demon",
            level,
            (level * 6) + (vitality * 8),
            strength,
            charisma,
            agility,
            intelligence,
            wisdom,
            GameSettings.getMonsterImagePath() + "Ice_Demon.png",
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
        this.hitPoints = (level * 6) + (vitality * 8);
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
        int dodgeChance = 13;
        if (RandomFactory.gameplayDouble() * 100 < dodgeChance) {
            MainGameScreen.appendToMessageTextPane(getName() + " freezes the air and dodges the attack!");
            return;
        }
        setHitPoints(getHitPoints() - defend(damage, mainGameScreen));
        if (isDead()) {
            MainGameScreen.appendToMessageTextPane(getName() + " shatters into icy shards.");
        }
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
    public int attack(MainGameScreen mainGameScreen) {
        boolean critical = RandomFactory.gameplayDouble() < 0.13;
        int base = (int) ((getStrength() * 1.5) + (getAgility() * 1.0));
        int damage = critical ? base * 2 : base;
        MainGameScreen.appendToMessageTextPane(getName() + " slashes with icy claws, dealing " + damage + " damage!" + (critical ? " Critical hit!" : ""));
        return damage;
    }

    public int attack(Character target, MainGameScreen mainGameScreen) {
        int damage = attack(mainGameScreen);
        if (RandomFactory.gameplayDouble() < 0.25) {
            MainGameScreen.appendToMessageTextPane(getName() + " unleashes a freezing blast! The target is frozen!");
            target.addStatus(new IceStatus());
        }
        target.takeDamage(damage);
        return damage;
    }

    @Override
    public int defend(int incomingDamage, MainGameScreen mainGameScreen) {
        int baseDefense = 11;
        int reductionPercent = (baseDefense + getAgility()) / 2;
        if (reductionPercent > 80) reductionPercent = 80;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " defends with icy armor, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public String getImagePath() {
        if (getHitPoints() < 12) {
            return GameSettings.getMonsterImagePath() + "Ice_Demon_injured.png";
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
        return 6 + RandomFactory.gameplayInt(3);
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
        return "Ice_Demon{" +
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
