
// File: `src/DungeonoftheBrutalKing/Enemies/Champion.java`
package DungeonoftheBrutalKing.Enemies;

import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.RandomFactory;

import java.io.IOException;
import java.text.ParseException;

public class Champion extends Enemies {
    private int level;

    private final int strength;
    private final int charisma;
    private final int agility;
    private final int intelligence;
    private final int wisdom;
    private final int vitality;

    private final Alignment alignment = Alignment.GOOD;

    public Champion() {
        this(randomLevel(), 9, 8, 8, 7, 8, 8);
    }

    public Champion(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality) {
        super(
            "Champion",
            level,
            (level * 5) + (vitality * 7),
            strength,
            charisma,
            agility,
            intelligence,
            wisdom,
            GameSettings.MonsterImagePath + "Champion.png",
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
    }

    public int getLevel() { return level; }
    public int getStrength() { return strength; }
    public int getCharisma() { return charisma; }
    public int getAgility() { return agility; }
    public int getIntelligence() { return intelligence; }
    public int getWisdom() { return wisdom; }
    public int getVitality() { return vitality; }

    @Override
    public void takeDamage(int damage) {
        int blockChancePercent = 15;
        if (RandomFactory.gameplayDouble() * 100 < blockChancePercent) {
            appendMessageSafely(getName() + " blocks the attack with a shield!");
            return;
        }

        int mitigated = defend(damage);
        super.takeDamage(mitigated);

        if (isDead()) {
            appendMessageSafely(getName() + " falls, glory undimmed.");
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
        boolean critical = RandomFactory.gameplayDouble() < 0.15;
        int base = (int) ((getStrength() * 1.7) + (getWisdom() * 1.0));
        return critical ? base * 2 : base;
    }

    @Override
    public int defend(int incomingDamage) {
        int baseDefense = 12;
        int reductionPercent = (baseDefense + getWisdom()) / 2;
        if (reductionPercent > 75) reductionPercent = 75;

        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        appendMessageSafely(getName() + " parries bravely, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public String getImagePath() {
        if (getHitPoints() < 10) {
            return GameSettings.MonsterImagePath + "Champion_injured.png";
        }
        return super.getImagePath();
    }

    @Override
    public int getExperienceReward() {
        int base = level * 16;
        int offset = (int) ((RandomFactory.gameplayDouble() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 10;
        int offset = (int) ((RandomFactory.gameplayDouble() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    private static int randomLevel() {
        return 6 + RandomFactory.gameplayInt(3);
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

    private void appendMessageSafely(String message) {
        try {
            MainGameScreen.getInstance().appendToMessageTextPane(message);
        } catch (IOException | InterruptedException | ParseException | RuntimeException ignored) {
            // UI unavailable; keep combat logic running.
        }
    }

    @Override
    public String toString() {
        return "Champion{" +
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
