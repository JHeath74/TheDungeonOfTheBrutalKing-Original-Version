
// src/DungeonoftheBrutalKing/Enemies/Liches.java
package DungeonoftheBrutalKing.Enemies;

import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.RandomFactory;

public class Liches extends Enemies {
    private int level;
    private final int strength;
    private final int charisma;
    private final int agility;
    private final int intelligence;
    private final int wisdom;
    private final int vitality;
    private int hitPoints;
    private final int spellStrength;
    private final Alignment alignment = Alignment.EVIL;

    public Liches() {
        this(randomLevel(), 7, 6, 6, 10, 8, 6, 12);
    }

    public Liches(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality, int spellStrength) {
        super(
            "Liches",
            level,
            (level * 6) + (vitality * 8),
            strength,
            charisma,
            agility,
            intelligence,
            wisdom,
            GameSettings.getMonsterImagePath() + "Liches.png",
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
        this.hitPoints = (level * 6) + (vitality * 8);
        this.spellStrength = spellStrength;
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
    public int getSpellStrength() { return spellStrength; }

    @Override
    public void takeDamage(int damage, MainGameScreen mainGameScreen) {
        int dodgeChance = 14;
        if (RandomFactory.gameplayDouble() * 100 < dodgeChance) {
            MainGameScreen.appendToMessageTextPane(getName() + " phases through the attack!");
            return;
        }
        setHitPoints(getHitPoints() - defend(damage, mainGameScreen));
        if (isDead()) {
            MainGameScreen.appendToMessageTextPane(getName() + " collapses into a pile of ancient bones!");
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
        boolean critical = RandomFactory.gameplayDouble() < 0.15;
        int base = (int) ((getStrength() * 1.0) + (getIntelligence() * 1.7) + getSpellStrength());
        int damage = critical ? base * 2 : base;
        MainGameScreen.appendToMessageTextPane(getName() + " casts a necrotic spell, dealing " + damage + " damage!" + (critical ? " Critical hit!" : ""));
        return damage;
    }

    @Override
    public int defend(int incomingDamage, MainGameScreen mainGameScreen) {
        int baseDefense = 13;
        int reductionPercent = (baseDefense + getIntelligence()) / 2;
        if (reductionPercent > 80) reductionPercent = 80;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " conjures a spectral shield, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public String getImagePath() {
        if (getHitPoints() < 15) {
            return GameSettings.getMonsterImagePath() + "Liches_injured.png";
        }
        return super.getImagePath();
    }

    @Override
    public int getExperienceReward() {
        int base = level * 14;
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
        return 8 + RandomFactory.gameplayInt(3);
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
        return "Liches{" +
            "name='" + getName() + '\'' +
            ", level=" + getLevel() +
            ", hitPoints=" + getHitPoints() +
            ", strength=" + getStrength() +
            ", charisma=" + getCharisma() +
            ", agility=" + getAgility() +
            ", intelligence=" + getIntelligence() +
            ", wisdom=" + getWisdom() +
            ", vitality=" + getVitality() +
            ", spellStrength=" + getSpellStrength() +
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
