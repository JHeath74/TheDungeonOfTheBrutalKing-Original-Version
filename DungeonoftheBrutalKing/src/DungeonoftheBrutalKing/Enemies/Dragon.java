
// File: `src/DungeonoftheBrutalKing/Enemies/Dragon.java`
package DungeonoftheBrutalKing.Enemies;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.RandomFactory;
import DungeonoftheBrutalKing.Status.FireStatus;

import java.io.IOException;
import java.text.ParseException;

/**
 * Represents a Dragon enemy with advanced combat abilities.
 */
public class Dragon extends Enemies {

    private int level; // Used for rewards and scaling
    private final Alignment alignment = Alignment.EVIL;
    private final int alignmentImpact = 5;

    /**
     * Constructs a Dragon with predefined stats and image.
     */
    public Dragon() {
        super(
            "Dragon",
            9,
            30,
            8,
            5,
            7,
            6,
            3,
            GameSettings.MonsterImagePath + "Dragon.png",
            false,
            8
        );
        this.level = 9;
    }

    @Override
    public void takeDamage(int damage) {
        int mitigated = defend(damage);
        super.takeDamage(mitigated);

        if (isDead()) {
            appendMessageSafely(getName() + " has died.\n");
        }
    }

    /**
     * Attacks a target character, with a chance to inflict burn.
     * @param target The character being attacked.
     * @return The damage dealt.
     */
    public int attack(Charecter target) {
        int damage = getAttackDamage();

        double baseBurnChance = 0.30;
        double defenseFactor = Math.max(0.0, 1.0 - (target.getDefense() / 100.0));
        double finalBurnChance = baseBurnChance * defenseFactor;

        appendMessageSafely(getName() + " attacks for " + damage + " damage.\n");
        target.takeDamage(damage);

        if (RandomFactory.gameplayDouble() < finalBurnChance) {
            appendMessageSafely(getName() + " breathes fire! The target is burned.\n");
            FireStatus fireStatus = new FireStatus();
            target.getStatusManager().addStatus(fireStatus, target);
        }

        return damage;
    }

    /**
     * Throws an exception; use attack(Charecter target) instead.
     * @throws UnsupportedOperationException Always thrown to enforce correct usage.
     */
    @Override
    public int attack() {
        throw new UnsupportedOperationException("Use attack(Charecter target) instead.");
    }

    /**
     * Calculates reduced damage when defending, based on base defense and agility.
     * Caps reduction at 80%. Displays a message with the reduced damage.
     * @param incomingDamage The original damage to be reduced.
     * @return The reduced damage after defense.
     */
    @Override
    public int defend(int incomingDamage) {
        int baseDefense = 10;
        int reductionPercent = (baseDefense + getAgility()) / 2;
        if (reductionPercent > 80) reductionPercent = 80;

        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        appendMessageSafely(getName() + " defends and reduces damage to " + reducedDamage + ".\n");
        return reducedDamage;
    }

    @Override
    public boolean isDead() {
        return super.isDead();
    }

    @Override
    public int getAttackDamage() {
        return (int) ((getStrength() * 1.5) + (getAgility() * 0.5));
    }

    @Override
    public String getImagePath() {
        if (getHitPoints() < 10) {
            return GameSettings.MonsterImagePath + "Dragon_injured.png";
        }
        return super.getImagePath();
    }

    @Override
    public String toString() {
        return "Dragon{" +
            "name='" + getName() + '\'' +
            ", level=" + getLevel() +
            ", hitPoints=" + getHitPoints() +
            ", strength=" + getStrength() +
            ", charisma=" + getCharisma() +
            ", agility=" + getAgility() +
            ", intelligence=" + getIntelligence() +
            ", wisdom=" + getWisdom() +
            ", imagePath='" + getImagePath() + '\'' +
            '}';
    }

    public int getLevel() {
        return level;
    }

    @Override
    public int getExperienceReward() {
        int base = level * 10;
        int offset = (int) ((RandomFactory.gameplayDouble() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 5;
        int offset = (int) ((RandomFactory.gameplayDouble() * (2 * level * 7 + 1)) - (level * 7));
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
            // UI unavailable; keep combat logic running.
        }
    }
}
