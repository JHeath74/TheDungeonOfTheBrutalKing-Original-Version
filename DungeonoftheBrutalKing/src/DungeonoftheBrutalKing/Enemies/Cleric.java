
// File: `src/DungeonoftheBrutalKing/Enemies/Cleric.java`
package DungeonoftheBrutalKing.Enemies;

import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.GameSettings;

import java.io.IOException;
import java.text.ParseException;

public class Cleric extends Enemies {
    private int level;

    private final int strength;
    private final int charisma;
    private final int agility;
    private final int intelligence;
    private final int wisdom;
    private final int vitality;

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
            GameSettings.MonsterImagePath + "Cleric.png",
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

    @Override
    public void takeDamage(int damage) {
        int mitigated = defend(damage);
        super.takeDamage(mitigated);

        if (isDead()) {
            appendMessageSafely(getName() + " falls, prayers unanswered.");
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
        return super.isDead();
    }

    @Override
    public int attack() {
        return (int) ((getStrength() * 1.1) + (getWisdom() * 1.7) + getSpellStrength());
    }

    @Override
    public int defend(int incomingDamage) {
        int baseDefense = 7;
        int reductionPercent = (baseDefense + getWisdom()) / 2;
        if (reductionPercent > 80) reductionPercent = 80;

        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        appendMessageSafely(getName() + " invokes divine protection, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public String getImagePath() {
        return super.getImagePath();
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

    @Override
    public int getExperienceReward() {
        int base = level * 15;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 8;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    private static int randomLevel() {
        return 1 + (int) (Math.random() * 5);
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

    private void appendMessageSafely(String message) {
        try {
            MainGameScreen.getInstance().appendToMessageTextPane(message);
        } catch (IOException | InterruptedException | ParseException | RuntimeException ignored) {
            // UI unavailable; keep combat logic running.
        }
    }
}
