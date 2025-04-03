
// src/DungeonoftheBrutalKing/Enemies.java
package DungeonoftheBrutalKing;

public class Enemies {
    protected String name;
    protected int level;
    protected int hitPoints;
    protected static int strength;
    protected int charisma;
    protected static int agility;
    protected int intelligence;
    protected int wisdom;
    protected String imagePath;


    // Getter for hitPoints
    public int getHitPoints() {
        return hitPoints;
    }

    // Getter for strength
    public static int getStrength() {
        return strength;
    }

    // Getter for agility
    public static int getAgility() {
        return agility;
    }
    
    // Method to check if the monster is dead
    public boolean isDead() {
        return this.hitPoints <= 0;
    }

    // Method to calculate attack strength
    public int attack() {
        return (int) ((strength * 1.5) + (agility * 0.5));
    }

    // Getter for imagePath
    public String getImagePath() {
        return imagePath;
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

	public static Enemies Singleton() {
		// TODO Auto-generated method stub
		return null;
	}
}
