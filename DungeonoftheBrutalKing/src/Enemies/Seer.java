
// src/Enemies/Seer.java
package Enemies;

import SharedData.GameSettings;
import SharedData.Alignment;
import DungeonoftheBrutalKing.MainGameScreen;

/**
 * Represents a Seer enemy with good alignment and magic abilities.
 */
public class Seer extends Enemies {

    // --- Fields ---
    private int level;
    private final Alignment alignment = Alignment.GOOD;
    private final int alignmentImpact = -3;

    // --- Constructor ---
    public Seer() {
        super(
            "Seer",             // Enemy name
            8,                  // Level
            32,                 // Hit points
            5,                  // Strength
            12,                 // Charisma
            8,                  // Agility
            15,                 // Intelligence
            16,                 // Wisdom
            GameSettings.MonsterImagePath + "Seer.png", // Image path
            true,               // Is magic user
            17                  // Spell strength
        );
        this.level = 8;
    }

    // --- Combat Methods ---
    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) setHitPoints(0);
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " vanishes in a shimmer of foresight.");
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    @Override
    public int attack() {
        return (int) ((getIntelligence() * 1.4) + (getWisdom() * 1.2) + getSpellStrength());
    }

    public int defend(int incomingDamage) {
        int baseDefense = 7;
        int wisdom = getWisdom();
        int reductionPercent = (baseDefense + wisdom) / 2;
        if (reductionPercent > 75) reductionPercent = 75;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " foresees the attack, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    // --- Utility Methods ---
    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    @Override
    public String toString() {
        return "Seer{" +
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
        int base = level * 13;
        int offset = (int) ((Math.random() * (2 * level * 6 + 1)) - (level * 6));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 8;
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
