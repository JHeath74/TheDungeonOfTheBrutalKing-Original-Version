
// src/Enemies/Seraph.java
package Enemies;

import SharedData.GameSettings;
import SharedData.Alignment;
import DungeonoftheBrutalKing.MainGameScreen;

/**
 * Represents a Seraph enemy with good alignment and magic abilities.
 */
public class Seraph extends Enemies {

    // --- Fields ---
    private int level;
    private final Alignment alignment = Alignment.GOOD;
    private final int alignmentImpact = -4;

    // --- Constructor ---
    public Seraph() {
        super(
            "Seraph",           // Enemy name
            14,                 // Level
            54,                 // Hit points
            10,                 // Strength
            18,                 // Charisma
            13,                 // Agility
            24,                 // Intelligence
            21,                 // Wisdom
            GameSettings.MonsterImagePath + "Seraph.png", // Image path
            true,               // Is magic user
            29                  // Spell strength
        );
        this.level = 14;
    }

    // --- Combat Methods ---
    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) setHitPoints(0);
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " ascends in a cascade of divine light.");
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    @Override
    public int attack() {
        return (int) ((getIntelligence() * 1.8) + (getWisdom() * 1.6) + getSpellStrength());
    }

    public int defend(int incomingDamage) {
        int baseDefense = 15;
        int wisdom = getWisdom();
        int reductionPercent = (baseDefense + wisdom) / 2;
        if (reductionPercent > 92) reductionPercent = 92;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " envelops itself in celestial wings, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    // --- Utility Methods ---
    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    @Override
    public String toString() {
        return "Seraph{" +
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
        int base = level * 27;
        int offset = (int) ((Math.random() * (2 * level * 8 + 1)) - (level * 8));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 19;
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
