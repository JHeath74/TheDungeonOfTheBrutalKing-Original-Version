
// src/DungeonoftheBrutalKing/Enemies/Spectre.java
package DungeonoftheBrutalKing.Enemies;

import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.Status.PoisonStatus;
import DungeonoftheBrutalKing.Charecter;

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
            GameSettings.MonsterImagePath + "Spectre.png",
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

    public void takeDamage(int damage, MainGameScreen mainGameScreen) {
        int dodgeChance = 18;
        if (Math.random() * 100 < dodgeChance) {
            mainGameScreen.appendToMessageTextPane(getName() + " phases and dodges the attack!");
            return;
        }
        setHitPoints(getHitPoints() - defend(damage, mainGameScreen));
        if (isDead()) mainGameScreen.appendToMessageTextPane(getName() + " dissipates into mist.");
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    public int attack(Charecter target, MainGameScreen mainGameScreen) {
        boolean critical = Math.random() < 0.18;
        int base = (int) ((getIntelligence() * 1.5) + (getAgility() * 1.2));
        int damage = critical ? base * 2 : base;
        boolean poisonApplied = Math.random() < 0.30;
        if (poisonApplied) {
            mainGameScreen.appendToMessageTextPane(getName() + " attacks and applies spectral poison!");
            target.addStatus(new PoisonStatus(3));
        } else {
            mainGameScreen.appendToMessageTextPane(getName() + " attacks for " + damage + " damage!");
        }
        return damage;
    }

    @Override
    public int attack() {
        boolean critical = Math.random() < 0.18;
        int base = (int) ((getIntelligence() * 1.5) + (getAgility() * 1.2));
        int damage = critical ? base * 2 : base;
        return damage;
    }

    public int defend(int incomingDamage, MainGameScreen mainGameScreen) {
        int baseDefense = 12;
        int reductionPercent = (baseDefense + getAgility()) / 2;
        if (reductionPercent > 85) reductionPercent = 85;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        mainGameScreen.appendToMessageTextPane(getName() + " becomes ethereal, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public String getImagePath() {
        if (getHitPoints() < 10) {
            return GameSettings.MonsterImagePath + "Spectre_injured.png";
        }
        return super.getImagePath();
    }

    @Override
    public int getExperienceReward() {
        int base = level * 13;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 7;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    private static int randomLevel() {
        return 5 + (int) (Math.random() * 2);
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

    public String getClassName(MainGameScreen mainGameScreen) {
        mainGameScreen.appendToMessageTextPane("Class: Spectre");
        return "Spectre";
    }

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return null;
	}
}
