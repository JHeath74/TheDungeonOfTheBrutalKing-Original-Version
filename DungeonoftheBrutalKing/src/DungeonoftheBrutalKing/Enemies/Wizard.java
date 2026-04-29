package DungeonoftheBrutalKing.Enemies;

import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Status.StunStatus;
import DungeonoftheBrutalKing.SharedData.RandomFactory;

public class Wizard extends Enemies {
    private int level;
    private final int strength;
    private final int charisma;
    private final int agility;
    private final int intelligence;
    private final int wisdom;
    private final int vitality;
    private int hitPoints;
    private final Alignment alignment = Alignment.EVIL;

    public Wizard() {
        this(randomLevel(), 4, 6, 5, 10, 8, 7);
    }

    public Wizard(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality) {
        super(
            "Wizard",
            level,
            (level * 6) + (vitality * 5),
            strength,
            charisma,
            agility,
            intelligence,
            wisdom,
            GameSettings.getInstance().getMonsterImagePath() + "Wizard.png",
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
        this.hitPoints = (level * 6) + (vitality * 5);
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
        int dodgeChance = 12;
        if (RandomFactory.gameplayDouble() * 100 < dodgeChance) {
            MainGameScreen.appendToMessageTextPane(getName() + " conjures a shield and dodges the attack!");
            return;
        }
        setHitPoints(getHitPoints() - defend(damage, mainGameScreen));
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " collapses, magic spent.");
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    public int attack(Character target, MainGameScreen mainGameScreen) {
        boolean critical = RandomFactory.gameplayDouble() < 0.15;
        int base = (int) ((getIntelligence() * 1.4) + (getWisdom() * 1.2));
        int damage = critical ? base * 2 : base;
        boolean stunApplied = RandomFactory.gameplayDouble() < 0.15;
        if (stunApplied) {
            MainGameScreen.appendToMessageTextPane(getName() + " casts a stunning spell!");
            target.addStatus(new StunStatus(2));
        } else {
            MainGameScreen.appendToMessageTextPane(getName() + " attacks for " + damage + " damage!");
        }
        return damage;
    }

    @Override
    public int attack(MainGameScreen mainGameScreen) {
        boolean critical = RandomFactory.gameplayDouble() < 0.15;
        int base = (int) ((getIntelligence() * 1.4) + (getWisdom() * 1.2));
        int damage = critical ? base * 2 : base;
        MainGameScreen.appendToMessageTextPane(getName() + " attacks for " + damage + " damage!" + (critical ? " Critical hit!" : ""));
        return damage;
    }

    @Override
    public int defend(int incomingDamage, MainGameScreen mainGameScreen) {
        int baseDefense = 8;
        int reductionPercent = (baseDefense + getAgility()) / 2;
        if (reductionPercent > 80) reductionPercent = 80;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " conjures a barrier, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public String getImagePath() {
        if (getHitPoints() < 10) {
            return GameSettings.getInstance().getMonsterImagePath() + "Wizard_injured.png";
        }
        return super.getImagePath();
    }

    @Override
    public int getExperienceReward() {
        int base = level * 10;
        int offset = (int) ((RandomFactory.gameplayDouble() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 5;
        int offset = (int) ((RandomFactory.gameplayDouble() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    private static int randomLevel() {
        return 6 + RandomFactory.gameplayInt(2);
    }

    @Override
    public int getAlignmentImpact() {
        int offset = (int) (RandomFactory.gameplayDouble() * ((level / 5) * 2 + 1)) - (level / 5);
        return 2 + offset;
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
        return "Wizard{" +
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
