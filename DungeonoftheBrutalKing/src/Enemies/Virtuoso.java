
// src/Enemies/Virtuoso.java
package Enemies;

import SharedData.GameSettings;
import SharedData.Alignment;
import DungeonoftheBrutalKing.MainGameScreen;

/**
 * Represents a Virtuoso enemy with good alignment and magic abilities.
 */
public class Virtuoso extends Enemies {

    // --- Fields ---
    private int level;
    private final Alignment alignment = Alignment.GOOD;
    private final int alignmentImpact = -5;

    // --- Constructor ---
    public Virtuoso() {
        super(
            "Virtuoso",         // Enemy name
            11,                 // Level
            42,                 // Hit points
            6,                  // Strength
            15,                 // Charisma
            10,                 // Agility
            18,                 // Intelligence
            17,                 // Wisdom
            GameSettings.MonsterImagePath + "Virtuoso.png", // Image path
            true,               // Is magic user
            23                  // Spell strength
        );
        this.level = 11;
    }

    // --- Combat Methods ---
    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) setHitPoints(0);
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " fades in a final, haunting melody.");
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    @Override
    public int attack() {
        return (int) ((getCharisma() * 1.3) + (getIntelligence() * 1.4) + getSpellStrength());
    }

    public int defend(int incomingDamage) {
        int baseDefense = 12;
        int wisdom = getWisdom();
        int reductionPercent = (baseDefense + wisdom) / 2;
        if (reductionPercent > 85) reductionPercent = 85;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " weaves a magical harmony, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    // --- Utility Methods ---
    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    @Override
    public String toString() {
        return "Virtuoso{" +
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
        int base = level * 21;
        int offset = (int) ((Math.random() * (2 * level * 8 + 1)) - (level * 8));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 13;
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
