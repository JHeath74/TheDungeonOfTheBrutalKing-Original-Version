package DungeonoftheBrutalKing.Enemies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing. Status.HasHitPoints;
import DungeonoftheBrutalKing.Status.Status;

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

    private final List<Status> statuses = new ArrayList<>();

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

    // \[Statuses\]

    /** Returns an unmodifiable view of all statuses on this enemy. */
    public List<Status> getStatuses() {
        return Collections.unmodifiableList(statuses);
    }

    // Expose status queries for combat and spells
    public boolean hasStatus(String statusName) {
        if (statusName == null) return false;
        for (Status s : statuses) {
            try {
                if (statusName.equalsIgnoreCase(s.getName())) return true;
            } catch (Exception ignored) { }
        }
        return false;
    }

    public Status getStatusByName(String statusName) {
        if (statusName == null) return null;
        for (Status s : statuses) {
            try {
                if (statusName.equalsIgnoreCase(s.getName())) return s;
            } catch (Exception ignored) { }
        }
        return null;
    }

    /**
     * Computes the total damage multiplier from all statuses.
     * Multiplies each `Status.damageTakenMultiplier()` together.
     */
    public double getDamageTakenMultiplier() {
        double mult = 1.0;
        for (Status s : statuses) {
            try {
                mult *= s.damageTakenMultiplier();
            } catch (Exception ignored) { }
        }
        return mult;
    }

    // \[Basic stats\]

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

    // \[Damage & combat\]

    public void takeDamage(int damage) {
        this.hitPoints = Math.max(0, this.hitPoints - damage);
    }

    /**
     * Apply incoming damage while considering active Status.damageTakenMultiplier()
     * on this enemy. Multiplies all active status multipliers together.
     */
    public void takeDamageWithStatuses(int damage) {
        double mult = getDamageTakenMultiplier();
        int finalDamage = (int) Math.round(damage * mult);
        takeDamage(finalDamage);
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

    // \[Hooks for subclasses / AI\]

    public void attemptApplyEffect(Charecter target) { }

    public int attack(Charecter target) { return 0; }

    public int attack() { return 0; }

    public Alignment getAlignment() { return null; }

    public int getAlignmentImpact() { return 0; }

    public void setLevel(int level) { }

    @Override
    public void addStatus(Status status) {
        if (status == null) return;

        // If status is negative and this enemy has a Purity Ward, resist it.
        try {
            if (status.isNegative()) {
                for (Status s : statuses) {
                    if (s instanceof DungeonoftheBrutalKing.Status.PurityWardStatus) {
                        try { System.out.println(getName() + " is protected by a Purity Ward; negative status '" + status.getName() + "' resisted."); } catch (Exception ignored) { }
                        return;
                    }
                    try {
                        if (s.getClass().getSimpleName().equals("PurityWardStatus")) { try { System.out.println(getName() + " is protected by a Purity Ward; negative status '" + status.getName() + "' resisted."); } catch (Exception ignored) { } return; }
                    } catch (Exception ignored) { }
                }
            }
        } catch (Exception ignored) { }

        if (status != null) {
            statuses.add(status);
        }
    }

    public boolean isUndead() {
        return undead;
    }
}