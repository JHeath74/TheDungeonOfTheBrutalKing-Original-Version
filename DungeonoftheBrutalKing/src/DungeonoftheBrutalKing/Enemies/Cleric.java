package DungeonoftheBrutalKing.Enemies;

import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.RandomFactory;

public class Cleric extends Enemies {
    private int level;
    private final int strength;
    private final int charisma;
    private final int agility;
    private final int intelligence;
    private final int wisdom;
    private final int vitality;
    private int hitPoints;
    private final Alignment alignment = Alignment.GOOD;

    public Cleric() {
        this(randomLevel(), 6, 8, 6, 8, 10, 7);
    }

    public Cleric(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality) {
        super(
            "Cleric",
            level,
            (level * 5) + (vitality * 7),
            strength,
            charisma,
            agility,
            intelligence,
            wisdom,
            GameSettings.getInstance().getMonsterImagePath() + "Cleric.png",
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
        int blockChance = 15;
        if (RandomFactory.gameplayDouble() * 100 < blockChance) {
            MainGameScreen.appendToMessageTextPane(getName() + " blocks the attack with divine ward!");
            return;
        }
        setHitPoints(getHitPoints() - defend(damage, mainGameScreen));
        if (isDead()) {
            MainGameScreen.appendToMessageTextPane(getName() + " falls, prayers unanswered.");
        }
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
    public int attack(MainGameScreen mainGameScreen) {
        boolean critical = RandomFactory.gameplayDouble() < 0.15;
        int base = (int) ((getStrength() * 1.1) + (getWisdom() * 1.7) + getSpellStrength());
        int damage = critical ? base * 2 : base;
        MainGameScreen.appendToMessageTextPane(getName() + " invokes holy wrath, dealing " + damage + " damage!" + (critical ? " Critical hit!" : ""));
        return damage;
    }

    @Override
    public int defend(int incomingDamage, MainGameScreen mainGameScreen) {
        int baseDefense = 7;
        int reductionPercent = (baseDefense + getWisdom()) / 2;
        if (reductionPercent > 80) reductionPercent = 80;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " invokes divine protection, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public String getImagePath() {
        if (getHitPoints() < 10) {
            return GameSettings.getInstance().getMonsterImagePath() + "Cleric_injured.png";
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
    public String getClassName() {
        return getName();
    }

    @Override
    public String toString() {
        return "Cleric{" +
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
}
