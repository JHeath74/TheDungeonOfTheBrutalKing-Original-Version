
// Druid.java
package Enemies;

import SharedData.GameSettings;
import SharedData.Alignment;
import DungeonoftheBrutalKing.MainGameScreen;

/**
 * Represents a Druid enemy with good alignment and magic abilities.
 */
public class Druid extends Enemies {

    // --- Fields ---
    private int level;
    private final Alignment alignment = Alignment.GOOD;
    private final int alignmentImpact = -3;

    // --- Constructor ---
    public Druid() {
        super(
            "Druid",            // Enemy name
            8,                  // Level
            36,                 // Hit points
            6,                  // Strength
            11,                 // Charisma
            11,                 // Agility
            14,                 // Intelligence
            17,                 // Wisdom
            GameSettings.MonsterImagePath + "Druid.png", // Image path
            true,               // Is magic user
            18                  // Spell strength
        );
        this.level = 8;
    }

    // --- Combat Methods ---
    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) setHitPoints(0);
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " returns to the earth in a swirl of leaves.");
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    @Override
    public int attack() {
        return (int) ((getWisdom() * 1.3) + (getIntelligence() * 1.2) + getSpellStrength());
    }

    public void healWithNature() {
        int healAmount = (int) (getWisdom() * 1.1 + getSpellStrength() * 0.8);
        setHitPoints(getHitPoints() + healAmount);
        MainGameScreen.appendToMessageTextPane(getName() + " calls upon nature to heal for " + healAmount + " hit points.");
    }

    public int defend(int incomingDamage) {
        int baseDefense = 9;
        int agility = getAgility();
        int reductionPercent = (baseDefense + agility) / 2;
        if (reductionPercent > 75) reductionPercent = 75;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " summons roots to shield, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    // --- Utility Methods ---
    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    @Override
    public String toString() {
        return "Druid{" +
                "name='" + getName() + '\'' +
                ", level=" + getLevel() +
                ", hitPoints=" + getHitPoints() +
                ", strength=" + getStrength() +
                ", charisma=" + getCharisma() +
                ", agility=" + getAgility() +
                ", intelligence=" + getIntelligence() +
                ", wisdom=" + getWisdom() +
                ", imagePath='" + getImagePath() + '\'' +
                ", isMagicUser=" + isMagicUser() +
                ", spellStrength=" + getSpellStrength() +
                '}';
    }

    // --- Getters and Alignment Methods ---
    public int getLevel() {
        return level;
    }

    @Override
    public int getExperienceReward() {
        int base = level * 14;
        int offset = (int) ((Math.random() * (2 * level * 6 + 1)) - (level * 6));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 9;
        int offset = (int) ((Math.random() * (2 * level * 6 + 1)) - (level * 6));
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
}
