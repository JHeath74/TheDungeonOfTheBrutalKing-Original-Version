
package Enemies;

public abstract class Enemies {
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
    protected int maxHitPoints;

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
        this.hitPoints = hitPoints;
        this.maxHitPoints = hitPoints;
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

    public abstract int attack();

    public void takeDamage(int damage) {
        this.hitPoints = Math.max(0, this.hitPoints - damage);
    }

    public int defend(int incomingDamage) {
        int reduction = agility / 4;
        int actualDamage = Math.max(0, incomingDamage - reduction);
        return actualDamage;
    }

    public boolean isDead() {
        return hitPoints <= 0;
    }
    
    public int getMaxHitPoints() {
        return maxHitPoints;
    }


public int getAttackDamage() {
    int baseDamage = (int) ((strength * 1.5) + (agility * 0.5));
    if (isMagicUser) {
        baseDamage += spellStrength;
    }
    return baseDamage;
}

public int getExperienceReward() {
	// TODO Auto-generated method stub
	return level * 10;
}

public int getGoldReward() {
	// TODO Auto-generated method stub
	return level * 5;
}

}
