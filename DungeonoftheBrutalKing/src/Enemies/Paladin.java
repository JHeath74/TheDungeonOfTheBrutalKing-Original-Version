
// src/Enemies/Paladin.java
package Enemies;

import SharedData.GameSettings;
import SharedData.Alignment;
import DungeonoftheBrutalKing.MainGameScreen;

/**
 * Represents a Paladin enemy with good alignment and magic abilities.
 */
public class Paladin extends Enemies {

    // --- Fields ---
    private int level;
    private final Alignment alignment = Alignment.GOOD;
    private final int alignmentImpact = 3;

    // --- Constructor ---
    /**
     * Creates a Paladin enemy with specific stats and good alignment.
     * Sets name, level, hit points, attributes, image path, magic user status, and spell strength.
     */
    public Paladin() {
        super(
            "Paladin",          // Enemy name
            8,                  // Level
            30,                 // Hit points
            7,                  // Strength
            6,                  // Charisma
            6,                  // Agility
            7,                  // Intelligence
            8,                  // Wisdom
            GameSettings.MonsterImagePath + "Paladin.png", // Image path
            true,               // Is magic user
            12                  // Spell strength
        );
        this.level = 8;         // Set level field
    }

    // --- Combat Methods ---
    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) setHitPoints(0);
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " falls, but light remains.");
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    @Override
    public int attack() {
        return (int) ((getStrength() * 1.3) + (getWisdom() * 1.2) + getSpellStrength());
    }

    public int defend(int incomingDamage) {
        int baseDefense = 7;
        int wisdom = getWisdom();
        int reductionPercent = (baseDefense + wisdom) / 2;
        if (reductionPercent > 75) reductionPercent = 75;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " invokes holy shield, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    // --- Utility Methods ---
    @Override
    public String getImagePath() {
        return super.getImagePath();
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
        int offset = (int) ((Math.random() * (2 * level * 8 + 1)) - (level * 8));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 7;
        int offset = (int) ((Math.random() * (2 * level * 8 + 1)) - (level * 8));
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
