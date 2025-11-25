package Enemies;

import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.Alignment;
import SharedData.GameSettings;

/**
 * Represents a Brigand enemy with basic combat abilities.
 */
public class Brigand extends Enemies {

    private int level;
    private final Alignment alignment = Alignment.EVIL;
    private final int alignmentImpact = 2;

    /**
     * Constructs a Brigand with predefined stats and image.
     */
    public Brigand() {
        super(
            /* name: The enemy's type or identifier */ "Brigand",
            /* level: Difficulty or experience level (unused, see field below) */ 1,
            /* hitPoints: Starting health value */ 30,
            /* strength: Physical attack power */ 8,
            /* charisma: Social/persuasive ability */ 5,
            /* agility: Speed and evasion */ 7,
            /* intelligence: Problem-solving/magic ability */ 6,
            /* wisdom: Decision-making/resistance */ 3,
            /* imagePath: Path to Brigand's image asset */ GameSettings.MonsterImagePath + "Brigand.png",
            /* isMagicUser: Brigand does not use magic */ false,
            /* spellStrength: No spell strength */ 0
        );
        // Sets the Brigand's level field (used for rewards)
        this.level = 3;
    }

    @Override
    public Alignment getAlignment() {
        return alignment;
    }

    @Override
    public int getAlignmentImpact() {
        return alignmentImpact;
    }

    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) setHitPoints(0);
        if (isDead()) {
            MainGameScreen.appendToMessageTextPane(getName() + " has died.");
        }
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
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

    @Override
    public int attack() {
        return (int) ((getStrength() * 1.5) + (getAgility() * 0.5));
    }

    public int getAttackDamage() {
        return attack();
    }

    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    public int defend(int incomingDamage) {
        int baseDefense = 10;
        int agility = getAgility();
        int reductionPercent = (baseDefense + agility) / 2;
        if (reductionPercent > 80) reductionPercent = 80;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " defends and reduces damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public String toString() {
        return "Brigand{" +
                "name='" + getName() + '\'' +
                ", level=" + level +
                ", hitPoints=" + getHitPoints() +
                ", strength=" + getStrength() +
                ", charisma=" + getCharisma() +
                ", agility=" + getAgility() +
                ", intelligence=" + getIntelligence() +
                ", wisdom=" + getWisdom() +
                ", imagePath='" + getImagePath() + '\'' +
                '}';
    }
}
