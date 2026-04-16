
// File: `src/DungeonoftheBrutalKing/Enemies/Banneret.java`
package DungeonoftheBrutalKing.Enemies;

import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.GameSettings;

import java.io.IOException;
import java.text.ParseException;

/**
 * Represents a Banneret enemy with good alignment and no magic abilities.
 * Uses vitality for hit points calculation.
 */
public class Banneret extends Enemies {

    private int level;
    private final int vitality;
    private final Alignment alignment = Alignment.GOOD;
    private final int alignmentImpact = -3;

    public Banneret() {
        this(8, 8, 9, 7, 7, 8, 7);
    }

    public Banneret(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality) {
        super(
            "Banneret",
            level,
            (level * 5) + (vitality * 7),
            strength,
            charisma,
            agility,
            intelligence,
            wisdom,
            GameSettings.MonsterImagePath + "Banneret.png",
            false,
            vitality
        );
        this.level = level;
        this.vitality = vitality;
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        if (isDead()) {
            appendMessageSafely(getName() + " falls, banner lowered.");
        }
    }

    @Override
    public boolean isDead() {
        return super.isDead();
    }

    @Override
    public int attack() {
        return (int) ((getStrength() * 1.2) + (getCharisma() * 1.5));
    }

    @Override
    public int defend(int incomingDamage) {
        int baseDefense = 12;
        int reductionPercent = (baseDefense + getCharisma()) / 2;
        if (reductionPercent > 75) reductionPercent = 75;

        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        appendMessageSafely(getName() + " rallies allies, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    @Override
    public String toString() {
        return "Banneret{" +
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

    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    public int getVitality() {
        return vitality;
    }

    @Override
    public int getExperienceReward() {
        int base = level * 15;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 9;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getAlignmentImpact() {
        return alignmentImpact;
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
            // UI unavailable; keep game logic running.
        }
    }
}
