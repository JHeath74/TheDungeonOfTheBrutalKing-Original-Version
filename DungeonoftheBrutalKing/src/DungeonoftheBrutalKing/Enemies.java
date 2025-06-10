
// src/DungeonoftheBrutalKing/Enemies.java
package DungeonoftheBrutalKing;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Enemies {
    // Map to store monster names and their corresponding image paths
    private static final Map<String, String> monsterImages = new HashMap<>();

    // Monster attributes
    protected String name; // Name of the monster
    protected int level; // Level of the monster
    protected int hitPoints; // Hit points (health) of the monster
    protected static int strength; // Strength attribute of the monster
    protected int charisma; // Charisma attribute of the monster
    protected static int agility; // Agility attribute of the monster
    protected int intelligence; // Intelligence attribute of the monster
    protected int wisdom; // Wisdom attribute of the monster
    protected String imagePath; // Path to the monster's image

    // Static block to initialize the map with monster names and their image paths
    static {
        monsterImages.put("Goblin", "src/resources/images/goblin.png");
        monsterImages.put("Orc", "src/resources/images/orc.png");
        monsterImages.put("Dragon", "src/resources/images/dragon.png");
        monsterImages.put("Troll", "src/resources/images/troll.png");
    }

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

    // Method to calculate the attack strength of the monster
    public int attack() {
        return (int) ((strength * 1.5) + (agility * 0.5));
    }

    // Getter for imagePath
    public String getImagePath() {
        return imagePath;
    }

    // Override toString method to provide a string representation of the monster
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

    // Method to get the image path of a randomly selected monster
    public String getMonsterImagePath() {
        // Randomly select a monster from the map
        Object[] monsters = monsterImages.keySet().toArray();
        String selectedMonster = (String) monsters[new Random().nextInt(monsters.length)];
        this.name = selectedMonster; // Set the name of the selected monster
        return monsterImages.get(selectedMonster); // Return the image path of the selected monster
    }

	public static Enemies Singleton() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getName() {
		return name;
	}

	public int getHP() {
		// TODO Auto-generated method stub
		return 0;
	}


public int getWeaponDamage() {
    Random random = new Random();
    int baseDamage = (int) (strength * 1.2); // Base damage based on strength
    int randomFactor = random.nextInt(5) + 1; // Random factor between 1 and 5
    return baseDamage + randomFactor; // Total weapon damage
}

public void setHP(int i) {
	
	this.hitPoints= i;
	}

}
