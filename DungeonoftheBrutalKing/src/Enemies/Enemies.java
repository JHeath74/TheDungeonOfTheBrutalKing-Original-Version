
package Enemies;

import java.util.ArrayList;
import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Alignment;
import Status.HasHitPoints;
import Status.Status;

public abstract class Enemies implements HasHitPoints {
    private final String name;
    protected int level;
    private int hitPoints;
    protected int strength;
    private final int charisma;
    private final int agility;
    private final int intelligence;
    private final int wisdom;
    protected final String imagePath;
    protected boolean isMagicUser;
    private int spellStrength;
    protected int maxHitPoints;

    protected boolean undead = false;

    public Enemies(
            String name,
            int level,
            int hitPoints,
            int strength,
            int charisma,
            int agility,
            int intelligence,
            int wisdom,
            String imagePath,
            boolean undead,
            int vitality
    ) {
        this.name = name;
        this.level = level;
        this.hitPoints = hitPoints;
        this.maxHitPoints = hitPoints;

        this.strength = strength;
        this.charisma = charisma;
        this.agility = agility;
        this.intelligence = intelligence;
        this.wisdom = wisdom;

        this.imagePath = imagePath;
        this.undead = undead;

        this.isMagicUser = false;
        this.spellStrength = 0;
    }

    private final List<Status> statuses = new ArrayList<>();

    public String getName() { return name; }
    public int getLevel() { return level; }
    public int getHitPoints() { return hitPoints; }
    public int getStrength() { return strength; }
    public int getCharisma() { return charisma; }
    public int getAgility() { return agility; }
    public int getIntelligence() { return intelligence; }
    public int getWisdom() { return wisdom; }
    public String getImagePath() { return imagePath; }
    public boolean isMagicUser() { return isMagicUser; }
    public int getSpellStrength() { return spellStrength; }

    public void setHitPoints(int hitPoints) { this.hitPoints = hitPoints; }
    public void setMagicUser(boolean isMagicUser) { this.isMagicUser = isMagicUser; }
    public void setSpellStrength(int spellStrength) { this.spellStrength = spellStrength; }

    public void takeDamage(int damage) {
        this.hitPoints = Math.max(0, this.hitPoints - damage);
    }

    public int defend(int incomingDamage) {
        int reduction = agility / 4;
        return Math.max(0, incomingDamage - reduction);
    }

    public boolean isDead() {
        return hitPoints <= 0;
    }

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public int getAttackDamage() {
        int baseDamage = (int) ((strength * 1.5) + (agility * 0.5));
        return isMagicUser ? baseDamage + spellStrength : baseDamage;
    }

    public int getExperienceReward() {
        return level * 10;
    }

    public int getGoldReward() {
        return level * 5;
    }

    public void attemptApplyEffect(Charecter target) { }

    public int attack(Charecter target) { return 0; }

    public int attack() { return 0; }

    public Alignment getAlignment() { return null; }

    public int getAlignmentImpact() { return 0; }

    public void setLevel(int level) { }

    @Override
    public void addStatus(Status status) {
        if (status != null) statuses.add(status);
    }

    public boolean isUndead() {
        return undead;
    }
}
