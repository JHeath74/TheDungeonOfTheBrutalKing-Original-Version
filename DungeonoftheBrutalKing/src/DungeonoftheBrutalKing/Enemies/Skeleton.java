
// src/DungeonoftheBrutalKing/Enemies/Skeleton.java
package DungeonoftheBrutalKing.Enemies;

import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.MainGameScreen;

public class Skeleton extends Enemies {
    private int level;
    private final int strength;
    private final int charisma;
    private final int agility;
    private final int intelligence;
    private final int wisdom;
    private final int vitality;
    private final Alignment alignment = Alignment.EVIL;

    public Skeleton() {
        this(randomLevel(), 8, 5, 7, 6, 3, 6);
    }

    public Skeleton(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality) {
        super(
            "Skeleton",
            level,
            (level * 6) + (vitality * 6),
            strength,
            charisma,
            agility,
            intelligence,
            wisdom,
            GameSettings.MonsterImagePath + "Skeleton.png",
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
    }

    public int getLevel() { return level; }
    public int getStrength() { return strength; }
    public int getCharisma() { return charisma; }
    public int getAgility() { return agility; }
    public int getIntelligence() { return intelligence; }
    public int getWisdom() { return wisdom; }
    public int getVitality() { return vitality; }

    public void takeDamage(int damage, MainGameScreen mainGameScreen) {
        int dodgeChance = 12;
        if (Math.random() * 100 < dodgeChance) {
            mainGameScreen.appendToMessageTextPane(getName() + " rattles and dodges the attack!");
            return;
        }
        int reduced = defend(damage, mainGameScreen);
        super.takeDamage(reduced);
        if (isDead()) {
            mainGameScreen.appendToMessageTextPane(getName() + " collapses into a pile of bones.");
        }
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean isDead() {
        return super.isDead();
    }

    @Override
    public int attack() {
        boolean critical = Math.random() < 0.13;
        int base = (int) ((getStrength() * 1.2) + (getAgility() * 1.2));
        int damage = critical ? base * 2 : base;
        return damage;
    }

    public int defend(int incomingDamage, MainGameScreen mainGameScreen) {
        int baseDefense = 10;
        int reductionPercent = (baseDefense + getAgility()) / 2;
        if (reductionPercent > 80) reductionPercent = 80;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        mainGameScreen.appendToMessageTextPane(getName() + " blocks with brittle bones, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public String getImagePath() {
        if (getHitPoints() < 12) {
            return GameSettings.MonsterImagePath + "Skeleton_injured.png";
        }
        return super.getImagePath();
    }

    @Override
    public int getExperienceReward() {
        int base = level * 10;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 5;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    private static int randomLevel() {
        return 2 + (int) (Math.random() * 2);
    }

    @Override
    public int getAlignmentImpact() {
        int offset = (int) (Math.random() * ((level / 5) * 2 + 1)) - (level / 5);
        return level + offset;
    }

    @Override
    public Alignment getAlignment() {
        return alignment;
    }

    @Override
    public String toString() {
        return "Skeleton{" +
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

    public String getClassName(MainGameScreen mainGameScreen) {
        mainGameScreen.appendToMessageTextPane("Class: Skeleton");
        return "Skeleton";
    }

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return null;
	}
}
