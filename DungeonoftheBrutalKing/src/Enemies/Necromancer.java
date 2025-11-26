
// src/Enemies/Necromancer.java
package Enemies;

import SharedData.GameSettings;
import SharedData.Alignment;
import DungeonoftheBrutalKing.MainGameScreen;

/**
 * Represents a Necromancer enemy with evil alignment and magic abilities.
 */
public class Necromancer extends Enemies {

    // --- Fields ---
    private int level;
    private final Alignment alignment = Alignment.EVIL;
    private final int alignmentImpact = -3;

    // --- Constructor ---
    public Necromancer() {
        super(
            "Necromancer",      // Enemy name
            7,                  // Level
            25,                 // Hit points
            6,                  // Strength
            4,                  // Charisma
            5,                  // Agility
            8,                  // Intelligence
            6,                  // Wisdom
            GameSettings.MonsterImagePath + "Necromancer.png", // Image path
            true,               // Is magic user
            10                  // Spell strength
        );
        this.level = 7;         // Set level field
    }

    // --- Combat Methods ---
    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) setHitPoints(0);
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " has perished in darkness.");
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    @Override
    public int attack() {
        return (int) ((getStrength() * 1.2) + (getIntelligence() * 1.3) + getSpellStrength());
    }

    public int defend(int incomingDamage) {
        int baseDefense = 5;
        int intelligence = getIntelligence();
        int reductionPercent = (baseDefense + intelligence) / 2;
        if (reductionPercent > 70) reductionPercent = 70;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " conjures a shield, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    // --- Utility Methods ---
    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    @Override
    public String toString() {
        return "Necromancer{" +
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
        int base = level * 12;
        int offset = (int) ((Math.random() * (2 * level * 8 + 1)) - (level * 8));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 6;
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
