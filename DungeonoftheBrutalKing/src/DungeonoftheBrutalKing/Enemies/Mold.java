
// src/DungeonoftheBrutalKing/Enemies/Mold.java
package DungeonoftheBrutalKing.Enemies;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.RandomFactory;
import DungeonoftheBrutalKing.Status.PoisonStatus;

public class Mold extends Enemies {
    private int level;
    private final int strength;
    private final int charisma;
    private final int agility;
    private final int intelligence;
    private final int wisdom;
    private final int vitality;
    private int hitPoints;
    private final Alignment alignment = Alignment.EVIL;

    public Mold() {
        this(randomLevel(), 4, 2, 3, 1, 1, 5);
    }

    public Mold(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality) {
        super(
            "Mold",
            level,
            (level * 4) + (vitality * 6),
            strength,
            charisma,
            agility,
            intelligence,
            wisdom,
            GameSettings.getMonsterImagePath() + "Mold.png",
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
        this.hitPoints = (level * 4) + (vitality * 6);
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
        int dodgeChance = 8;
        if (RandomFactory.gameplayDouble() * 100 < dodgeChance) {
            MainGameScreen.appendToMessageTextPane(getName() + " oozes away and dodges the attack!");
            return;
        }
        setHitPoints(getHitPoints() - defend(damage, mainGameScreen));
        if (isDead()) {
            MainGameScreen.appendToMessageTextPane(getName() + " has dried up and died.");
        }
    }

    @Override
    public int defend(int incomingDamage, MainGameScreen mainGameScreen) {
        int baseDefense = 10;
        int reductionPercent = (baseDefense + getAgility()) / 2;
        if (reductionPercent > 80) reductionPercent = 80;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " defends and reduces damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public String getClassName() {
        return getName();
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
        boolean critical = RandomFactory.gameplayDouble() < 0.12;
        int base = (int) ((getStrength() * 1.2) + (getVitality() * 0.7));
        int damage = critical ? base * 2 : base;
        MainGameScreen.appendToMessageTextPane(getName() + " lashes out, dealing " + damage + " damage!" + (critical ? " Critical hit!" : ""));
        return damage;
    }

    public int attack(Character target, MainGameScreen mainGameScreen) {
        int damage = attack(mainGameScreen);
        if (RandomFactory.gameplayDouble() < 0.3) {
            MainGameScreen.appendToMessageTextPane(getName() + " poisons " + target.getName() + "!");
            target.addStatus(new PoisonStatus(damage));
        }
        return damage;
    }

    @Override
    public String getImagePath() {
        if (getHitPoints() < 8) {
            return GameSettings.getMonsterImagePath() + "Mold_injured.png";
        }
        return super.getImagePath();
    }

    @Override
    public int getExperienceReward() {
        int base = level * 7;
        int offset = (int) ((RandomFactory.gameplayDouble() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 3;
        int offset = (int) ((RandomFactory.gameplayDouble() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    private static int randomLevel() {
        return 1 + RandomFactory.gameplayInt(2);
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
        return "Mold{" +
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
