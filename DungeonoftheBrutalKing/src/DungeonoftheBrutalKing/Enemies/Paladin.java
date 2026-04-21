package DungeonoftheBrutalKing.Enemies;

import DungeonoftheBrutalKing.SharedData.GameSettings; import DungeonoftheBrutalKing.SharedData.Alignment; import DungeonoftheBrutalKing.SharedData.RandomFactory; import DungeonoftheBrutalKing.MainGameScreen;

public class Paladin extends Enemies { private int level; private final int strength; private final int charisma; private final int agility; private final int intelligence; private final int wisdom; private final int vitality; private int hitPoints; private final Alignment alignment = Alignment.GOOD;

public Paladin() {
    this(randomLevel(), 7, 8, 7, 8, 9, 8);
}

public Paladin(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality) {
    super(
        "Paladin",
        level,
        (level * 5) + (vitality * 7),
        strength,
        charisma,
        agility,
        intelligence,
        wisdom,
        GameSettings.MonsterImagePath + "Paladin.png",
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
    int dodgeChance = 10;
    if (RandomFactory.gameplayDouble() * 100 < dodgeChance) {
        mainGameScreen.appendToMessageTextPane(getName() + " parries with holy resolve and dodges the attack!");
        return;
    }
    setHitPoints(getHitPoints() - defend(damage, mainGameScreen));
    if (isDead()) mainGameScreen.appendToMessageTextPane(getName() + " falls, honor unbroken.");
}

@Override
public int defend(int incomingDamage, MainGameScreen mainGameScreen) {
    int baseDefense = 8;
    int reductionPercent = (baseDefense + getWisdom()) / 2;
    if (reductionPercent > 80) reductionPercent = 80;
    int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
    mainGameScreen.appendToMessageTextPane(getName() + " raises shield of faith, reducing damage by " + reductionPercent + "%!");
    return reducedDamage;
}

@Override
public int attack(MainGameScreen mainGameScreen) {
    boolean critical = RandomFactory.gameplayDouble() < 0.15;
    int base = (int) ((getStrength() * 1.2) + (getWisdom() * 1.5) + getSpellStrength());
    int damage = critical ? base * 2 : base;
    mainGameScreen.appendToMessageTextPane(getName() + " strikes with radiant power for " + damage + " damage!" + (critical ? " Critical hit!" : ""));
    return damage;
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
    if (getHitPoints() < 12) {
        return GameSettings.MonsterImagePath + "Paladin_injured.png";
    }
    return super.getImagePath();
}

@Override
public int getSpellStrength() {
    return (getLevel() * 2) + (getWisdom() * 2) + (getIntelligence());
}

@Override
public int getExperienceReward() {
    int base = level * 16;
    int offset = (int) ((RandomFactory.gameplayDouble() * (2 * level * 8 + 1)) - (level * 8));
    return Math.max(base + offset, 0);
}

@Override
public int getGoldReward() {
    int base = level * 9;
    int offset = (int) ((RandomFactory.gameplayDouble() * (2 * level * 8 + 1)) - (level * 8));
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
public String toString() {
    return "Paladin{" +
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
public String getClassName() {
    return "Paladin";
}
}