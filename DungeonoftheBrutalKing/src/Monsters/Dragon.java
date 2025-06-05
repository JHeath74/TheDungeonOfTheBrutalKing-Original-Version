package Monsters;

import DungeonoftheBrutalKing.Enemies;
import DungeonoftheBrutalKing.GameSettings;

public class Dragon extends Enemies {
    String name;
    int level;
    int hitPoints;
    int strength;
    int charisma;
    int agility;
    int intelligence;
    int wisdom;
	private String imagePath;

    // Constructor
    public Dragon() {
        this.name = "Dragon";
        this.level = 1;
        this.hitPoints = 30;
        this.strength = 8;
        this.charisma = 5;
        this.agility = 7;
        this.intelligence = 6;
        this.wisdom = 3;
        this.imagePath = GameSettings.MonsterImagePath + "Dragon.png"; // Set the image path
    }

    // Method to take damage
    public void takeDamage(int damage) {
        this.hitPoints -= damage;
        if (this.hitPoints < 0) {
            this.hitPoints = 0; // Ensure hit points don't go below 0
        }
        if (isDead()) {
            System.out.println(name + " has died.");
        }
    }

    // Method to check if the monster is dead
    public boolean isDead() {
        return this.hitPoints <= 0;
    }

    // Method to calculate attack strength
    public int attack() {
        return (int) ((strength * 1.5) + (agility * 0.5));
    }

    @Override
    public String toString() {
        return "Dragon{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", hitPoints=" + hitPoints +
                ", strength=" + strength +
                ", charisma=" + charisma +
                ", agility=" + agility +
                ", intelligence=" + intelligence +
                ", wisdom=" + wisdom +
                '}';
    }

	public String getImagePath() {
		return imagePath;
	}
}
