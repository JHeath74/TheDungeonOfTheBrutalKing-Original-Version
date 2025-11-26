
// src/Enemies/Priest.java
package Enemies;

import SharedData.GameSettings;
import SharedData.Alignment;
import DungeonoftheBrutalKing.MainGameScreen;

/**
 * Represents a Priest enemy with good alignment and magic abilities.
 */
public class Priest extends Enemies {

    // --- Fields ---
    private int level;
    private final Alignment alignment = Alignment.GOOD;
    private final int alignmentImpact = -2;

    // --- Constructor ---
    /**
     * Creates a Priest enemy with specific stats and good alignment.
     * Sets name, level, hit points, attributes, image path, magic user status, and spell strength.
     */
    public Priest() {
        super(
            "Priest",           // Enemy name
            5,                  // Level
            20,                 // Hit points
            4,                  // Strength
            8,                  // Charisma
            5,                  // Agility
            7,                  // Intelligence
            10,                 // Wisdom
            GameSettings.MonsterImagePath + "Priest.png", // Image path
            true,               // Is magic user
            12                  // Spell strength
        );
        this.level = 5;         // Set level field
    }

    // --- Combat Methods ---
    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) setHitPoints(0);
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " is defeated, but faith endures.");
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    @Override
    public int attack() {
        return (int) ((getStrength() * 1.0) + (getWisdom() * 1.5) + getSpellStrength());
    }

    public int defend(int incomingDamage) {
        int baseDefense = 5;
        int wisdom = getWisdom();
        int reductionPercent = (baseDefense + wisdom) / 2;
        if (reductionPercent > 70) reductionPercent = 70;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " chants a prayer, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    // --- Utility Methods ---
    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    @Override
    public String toString() {
        return "Priest{" +
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
        int base = level * 9;
        int offset = (int) ((Math.random() * (2 * level * 8 + 1)) - (level * 8));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 4;
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
