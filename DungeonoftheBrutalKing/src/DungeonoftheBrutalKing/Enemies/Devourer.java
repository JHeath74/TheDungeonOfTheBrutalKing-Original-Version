
// File: `src/DungeonoftheBrutalKing/Enemies/Devourer.java`
package DungeonoftheBrutalKing.Enemies;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.RandomFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class Devourer extends Enemies {
    private int level;

    private final int strength;
    private final int charisma;
    private final int agility;
    private final int intelligence;
    private final int wisdom;
    private final int vitality;

    private final Alignment alignment = Alignment.EVIL;

    public Devourer() {
        this(randomLevel(), 8, 5, 7, 6, 3, 6);
    }

    public Devourer(int level, int strength, int charisma, int agility, int intelligence, int wisdom, int vitality) {
        super(
            "Devourer",
            level,
            (level * 5) + (vitality * 7),
            strength,
            charisma,
            agility,
            intelligence,
            wisdom,
            GameSettings.MonsterImagePath + "Devourer.png",
            false,
            vitality
        );

        this.level = level;
        this.strength = strength;
        this.charisma = charisma;
        this.agility = agility;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.vitality = vitality;

        setMagicUser(false);
    }

    public int getLevel() { return level; }
    public int getStrength() { return strength; }
    public int getCharisma() { return charisma; }
    public int getAgility() { return agility; }
    public int getIntelligence() { return intelligence; }
    public int getWisdom() { return wisdom; }
    public int getVitality() { return vitality; }

    @Override
    public void takeDamage(int damage) {
        int dodgeChancePercent = 12;
        if (RandomFactory.gameplayDouble() * 100 < dodgeChancePercent) {
            appendMessageSafely(getName() + " dodged the attack!");
            return;
        }

        int mitigated = defend(damage);
        super.takeDamage(mitigated);

        if (isDead()) {
            appendMessageSafely(getName() + " has died.");
        }
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean isDead() {
        return super.isDead();
    }

    @Override
    public int attack() {
        boolean critical = RandomFactory.gameplayDouble() < 0.15;
        int base = (int) ((getStrength() * 1.5) + (getAgility() * 0.7));
        return critical ? base * 2 : base;
    }

    @Override
    public int defend(int incomingDamage) {
        int baseDefense = 10;
        int reductionPercent = (baseDefense + getAgility()) / 2;
        if (reductionPercent > 80) reductionPercent = 80;

        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        appendMessageSafely(getName() + " defends and reduces damage to " + reducedDamage + ".");
        return reducedDamage;
    }

    public void tryStealItem(Charecter player) {
        double stealChance = 0.2;

        ArrayList<String> inventory = new ArrayList<>(player.getCharInventory());
        if (inventory.isEmpty()) return;

        if (RandomFactory.gameplayDouble() < stealChance) {
            int index = RandomFactory.gameplayInt(inventory.size());
            String stolen = inventory.get(index);
            player.removeFromInventory(stolen);
            appendMessageSafely(getName() + " has stolen " + stolen + "!");
        }
    }

    @Override
    public String getImagePath() {
        if (getHitPoints() < 10) {
            return GameSettings.MonsterImagePath + "Devourer_injured.png";
        }
        return super.getImagePath();
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

    private static int randomLevel() {
        return 1 + RandomFactory.gameplayInt(5);
    }

    @Override
    public int getAlignmentImpact() {
        int offset = (int) (RandomFactory.gameplayDouble() * ((level / 5) * 2 + 1)) - (level / 5);
        return level + offset;
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

    @Override
    public String toString() {
        return "Devourer{" +
            "name='" + getName() + '\'' +
            ", level=" + getLevel() +
            ", hitPoints=" + getHitPoints() +
            ", strength=" + getStrength() +
            ", charisma=" + getCharisma() +
            ", agility=" + getAgility() +
            ", intelligence=" + getIntelligence() +
            ", wisdom=" + getWisdom() +
            ", vitality=" + getVitality() +
            ", imagePath='" + getImagePath() + '\'' +
            ", isMagicUser=" + isMagicUser() +
            '}';
    }
}
