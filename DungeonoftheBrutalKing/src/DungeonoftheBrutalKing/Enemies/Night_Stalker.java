package DungeonoftheBrutalKing.Enemies;

import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.RandomFactory;

public class Night_Stalker extends Enemies {
    private int level;
    private final int strength;
    private final int charisma;
    private final int agility;
    private final int intelligence;
    private final int wisdom;
    private final int vitality;
    private int hitPoints;
    private final Alignment alignment = Alignment.EVIL;

    public Night_Stalker() {
        this(randomLevel(), 8, 5, 7, 6, 3, 6);
    }

    public Night_Stalker(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality) {
        super(
            "Night Stalker",
            level,
            (level * 6) + (vitality * 6),
            strength,
            charisma,
            agility,
            intelligence,
            wisdom,
            GameSettings.getMonsterImagePath() + "Night_Stalker.png",
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
        this.hitPoints = (level * 6) + (vitality * 6);
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
        int dodgeChance = 14;
        if (RandomFactory.gameplayDouble() * 100 < dodgeChance) {
            MainGameScreen.appendToMessageTextPane(getName() + " melts into the darkness and dodges the attack!");
            return;
        }
        setHitPoints(getHitPoints() - defend(damage, mainGameScreen));
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " fades into shadow, defeated.");
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
        boolean critical = RandomFactory.gameplayDouble() < 0.16;
        int base = (int) ((getStrength() * 1.4) + (getAgility() * 1.1));
        int damage = critical ? base * 2 : base;
        MainGameScreen.appendToMessageTextPane(getName() + " strikes from the shadows for " + damage + " damage!" + (critical ? " Critical hit!" : ""));
        return damage;
    }

    @Override
    public int defend(int incomingDamage, MainGameScreen mainGameScreen) {
        int baseDefense = 11;
        int reductionPercent = (baseDefense + getAgility()) / 2;
        if (reductionPercent > 80) reductionPercent = 80;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " defends with shadowy agility, reducing damage by " + reductionPercent + "%!");
        return reducedDamage;
    }

    @Override
    public String getImagePath() {
        if (getHitPoints() < 12) {
            return GameSettings.getMonsterImagePath() + "Night_Stalker_injured.png";
        }
        return super.getImagePath();
    }

    @Override
    public int getExperienceReward() {
        int base = level * 11;
        int offset = (int) ((RandomFactory.gameplayDouble() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 6;
        int offset = (int) ((RandomFactory.gameplayDouble() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    private static int randomLevel() {
        return 5 + RandomFactory.gameplayInt(3);
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
        return "Night_Stalker{" +
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
