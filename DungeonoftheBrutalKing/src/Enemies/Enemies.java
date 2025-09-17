
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

    public Enemies(String name, int level, int hitPoints, int strength, int charisma, int agility, int intelligence, int wisdom, String imagePath) {
        this.name = name;
        this.level = level;
        this.hitPoints = hitPoints;
        this.strength = strength;
        this.charisma = charisma;
        this.agility = agility;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.imagePath = imagePath;
    }

    // Getters
    public String getName() { return name; }
    public int getLevel() { return level; }
    public int getHitPoints() { return hitPoints; }
    public int getStrength() { return strength; }
    public int getCharisma() { return charisma; }
    public int getAgility() { return agility; }
    public int getIntelligence() { return intelligence; }
    public int getWisdom() { return wisdom; }
    public String getImagePath() { return imagePath; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setLevel(int level) { this.level = level; }
    public void setHitPoints(int hitPoints) { this.hitPoints = hitPoints; }
    public void setStrength(int strength) { this.strength = strength; }
    public void setCharisma(int charisma) { this.charisma = charisma; }
    public void setAgility(int agility) { this.agility = agility; }
    public void setIntelligence(int intelligence) { this.intelligence = intelligence; }
    public void setWisdom(int wisdom) { this.wisdom = wisdom; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    // Core methods
    public void takeDamage(int damage) {
        int actualDamage = defend(damage);
        setHitPoints(getHitPoints() - actualDamage);
        if (getHitPoints() < 0) setHitPoints(0);
    }

    public int defend(int damage) {
        double reduction = agility * 0.05;
        int reducedDamage = (int) (damage * (1 - reduction));
        return Math.max(reducedDamage, 0);
    }

    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    public int attack() {
        return (int) ((strength * 1.5) + (agility * 0.5));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", hitPoints=" + hitPoints +
                ", strength=" + strength +
                ", charisma=" + charisma +
                ", agility=" + agility +
                ", intelligence=" + intelligence +
                ", wisdom=" + wisdom +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
