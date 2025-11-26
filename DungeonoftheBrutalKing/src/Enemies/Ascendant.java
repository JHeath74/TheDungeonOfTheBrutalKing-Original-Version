
// src/Enemies/Ascendant.java
package Enemies;

import SharedData.GameSettings;
import SharedData.Alignment;
import DungeonoftheBrutalKing.MainGameScreen;

/**
 * Represents an Ascendant enemy with good alignment and magic abilities.
 */
public class Ascendant extends Enemies {

    // --- Fields ---
    private int level;
    private final Alignment alignment = Alignment.GOOD;
    private final int alignmentImpact = -2;

    // --- Constructor ---
    public Ascendant() {
        super(
            "Ascendant",        // Enemy name
            12,                 // Level
            46,                 // Hit points
            8,                  // Strength
            16,                 // Charisma
            11,                 // Agility
            20,                 // Intelligence
            19,                 // Wisdom
            GameSettings.MonsterImagePath + "Ascendant.png", // Image path
            true,               // Is magic user
            25                  // Spell strength
        );
        this.level = 12;
    }

    // --- Combat Methods ---
    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) setHitPoints(0);
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " ascends in a burst of celestial energy.");
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    @Override
    public int attack() {
        return (int) ((getIntelligence() * 1.6) + (getWisdom() * 1.4) + getSpellStrength());
    }

    public int defend(int incomingDamage) {
        int baseDefense = 13;
        int wisdom = getWisdom();
        int reductionPercent = (baseDefense + wisdom) / 2;
        if (reductionPercent > 88) reductionPercent = 88;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " manifests a celestial shield, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    // --- Utility Methods ---
    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    @Override
    public String toString() {
        return "Ascendant{" +
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
        int base = level * 23;
        int offset = (int) ((Math.random() * (2 * level * 8 + 1)) - (level * 8));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 15;
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
