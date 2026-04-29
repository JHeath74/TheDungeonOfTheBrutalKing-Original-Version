package DungeonoftheBrutalKing.Enemies;

import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.GameSettings;

public class Archon extends Enemies {
    private int level;
    private final int strength;
    private final int charisma;
    private final int agility;
    private final int intelligence;
    private final int wisdom;
    private final int vitality;
    private int hitPoints;
    private final Alignment alignment = Alignment.GOOD;

    public Archon() {
        this(randomLevel(), 8, 8, 8, 9, 10, 9);
    }

    public Archon(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality) {
        super(
            "Archon",
            level,
            (level * 5) + (vitality * 7),
            strength,
            charisma,
            agility,
            intelligence,
            wisdom,
            GameSettings.getInstance().getMonsterImagePath() + "Archon.png",
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
        int blockChance = 18;
        if (Math.random() * 100 < blockChance) {
            MainGameScreen.appendToMessageTextPane(getName() + " blocks the attack with a celestial barrier!");
            return;
        }
        setHitPoints(getHitPoints() - defend(damage, mainGameScreen));
        if (isDead()) {
            MainGameScreen.appendToMessageTextPane(getName() + " falls, celestial light dims.");
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
        boolean critical = Math.random() < 0.16;
        int base = (int) ((getStrength() * 1.3) + (getWisdom() * 1.6) + getSpellStrength());
        int damage = critical ? base * 2 : base;
        MainGameScreen.appendToMessageTextPane(getName() + " unleashes divine wrath, dealing " + damage + " damage!" + (critical ? " Critical hit!" : ""));
        return damage;
    }

    @Override
    public int defend(int incomingDamage, MainGameScreen mainGameScreen) {
        int baseDefense = 9;
        int reductionPercent = (baseDefense + getWisdom()) / 2;
        if (reductionPercent > 80) reductionPercent = 80;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " radiates divine shield, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public String getImagePath() {
        if (getHitPoints() < 15) {
            return GameSettings.getInstance().getMonsterImagePath() + "Archon_injured.png";
        }
        return super.getImagePath();
    }

    @Override
    public int getExperienceReward() {
        int base = level * 18;
        int offset = (int) ((Math.random() * (2 * level * 9 + 1)) - (level * 9));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 10;
        int offset = (int) ((Math.random() * (2 * level * 9 + 1)) - (level * 9));
        return Math.max(base + offset, 0);
    }

    private static int randomLevel() {
        return 5 + (int) (Math.random() * 3);
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
        return getName();
    }

    @Override
    public String toString() {
        return "Archon{" +
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
