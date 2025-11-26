
// Healer.java
package Enemies;

import SharedData.GameSettings;
import SharedData.Alignment;
import DungeonoftheBrutalKing.MainGameScreen;

/**
 * Represents a Healer enemy with good alignment and magic abilities.
 */
public class Healer extends Enemies {

    // --- Fields ---
    private int level;
    private final Alignment alignment = Alignment.GOOD;
    private final int alignmentImpact = -4;

    // --- Constructor ---
    public Healer() {
        super(
            "Healer",           // Enemy name
            9,                  // Level
            38,                 // Hit points
            5,                  // Strength
            12,                 // Charisma
            10,                 // Agility
            13,                 // Intelligence
            18,                 // Wisdom
            GameSettings.MonsterImagePath + "Healer.png", // Image path
            true,               // Is magic user
            19                  // Spell strength
        );
        this.level = 9;
    }

    // --- Combat Methods ---
    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) setHitPoints(0);
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " vanishes in a gentle glow.");
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    @Override
    public int attack() {
        return (int) ((getWisdom() * 1.5) + (getCharisma() * 1.2) + getSpellStrength());
    }

    public void healSelf() {
        int healAmount = (int) (getWisdom() * 1.3 + getSpellStrength() * 0.7);
        setHitPoints(getHitPoints() + healAmount);
        MainGameScreen.appendToMessageTextPane(getName() + " heals for " + healAmount + " hit points.");
    }

    public int defend(int incomingDamage) {
        int baseDefense = 8;
        int wisdom = getWisdom();
        int reductionPercent = (baseDefense + wisdom) / 2;
        if (reductionPercent > 80) reductionPercent = 80;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " invokes healing aura, reducing damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    // --- Utility Methods ---
    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    @Override
    public String toString() {
        return "Healer{" +
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
        int base = level * 16;
        int offset = (int) ((Math.random() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 10;
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
}
