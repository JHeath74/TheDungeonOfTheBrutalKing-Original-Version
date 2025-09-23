// src/Enemies/Enemies.java

package Enemies;

public class Enemies {
    private String name;
    private int level;
    private int hitPoints;
    private int strength;
    private int charisma;
    private int agility;
    private int intelligence;
    private int wisdom;
    private String imagePath;
    private boolean isMagicUser;
    private int spellStrength;

    public Enemies(String name, int level, int hitPoints, int strength, int charisma, int agility, int intelligence, int wisdom, String imagePath, boolean isMagicUser, int spellStrength) {
        this.name = name;
        this.level = level;
        this.hitPoints = hitPoints;
        this.strength = strength;
        this.charisma = charisma;
        this.agility = agility;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.imagePath = imagePath;
        this.isMagicUser = isMagicUser;
        this.spellStrength = spellStrength;
    }

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

    public int getAttackDamage() {
        if (isMagicUser) {
            return (int)((strength * 1.2) + (spellStrength * 1.5) + (agility * 0.3));
        } else {
            return (int)((strength * 1.5) + (agility * 0.5));
        }
    }

    // Reduces hit points by the given amount, not below zero
    public void takeDamage(int amount) {
        hitPoints = Math.max(0, hitPoints - amount);
    }

    // Reduces incoming damage based on agility
    public int defend(int incomingDamage) {
        int reduction = agility / 4; // Example: agility reduces damage
        int actualDamage = Math.max(0, incomingDamage - reduction);
        return actualDamage;
    }

    public boolean isDead() {
        return hitPoints <= 0;
    }
}
