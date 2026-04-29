
// src/DungeonoftheBrutalKing/Enemies/Sage.java
package DungeonoftheBrutalKing.Enemies;

import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.RandomFactory;
import DungeonoftheBrutalKing.MainGameScreen;

public class Sage extends Enemies {
    private int level;
    private final int strength;
    private final int charisma;
    private final int agility;
    private final int intelligence;
    private final int wisdom;
    private final int vitality;
    private int hitPoints;
    private final Alignment alignment = Alignment.GOOD;

    public Sage() {
        this(randomLevel(), 5, 10, 7, 12, 14, 7);
    }

    public Sage(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality) {
        super(
            "Sage",
            level,
            (level * 5) + (vitality * 7),
            strength,
            charisma,
            agility,
            intelligence,
            wisdom,
            GameSettings.getMonsterImagePath() + "Sage.png",
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
        setHitPoints(getHitPoints() - defend(damage, mainGameScreen));
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " falls, wisdom lost.");
    }

    @Override
    public int defend(int incomingDamage, MainGameScreen mainGameScreen) {
        int baseDefense = 7;
        int reductionPercent = (baseDefense + getWisdom()) / 2;
        if (reductionPercent > 80) reductionPercent = 80;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " uses ancient wisdom, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public int attack() {
        boolean critical = RandomFactory.gameplayDouble() < 0.17;
        int base = (int) ((getStrength() * 0.7) + (getWisdom() * 2.2) + getSpellStrength());
        int damage = critical ? base * 2 : base;
        return damage;
    }

    @Override
    public int attack(MainGameScreen mainGameScreen) {
        int damage = attack();
        MainGameScreen.appendToMessageTextPane(getName() + " invokes arcane knowledge for " + damage + " damage!" + (damage > ((getStrength() * 0.7) + (getWisdom() * 2.2) + getSpellStrength()) ? " Critical hit!" : ""));
        return damage;
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
    public String getImagePath() {
        if (getHitPoints() < 15) {
            return GameSettings.getMonsterImagePath() + "Sage_injured.png";
        }
        return super.getImagePath();
    }

    @Override
    public String toString() {
        return "Sage{" +
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
                ", spellStrength=" + getSpellStrength() +
                '}';
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
    public String getClassName() {
        return getName();
    }
}
