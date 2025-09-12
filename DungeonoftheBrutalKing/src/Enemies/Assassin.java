package Enemies;

import DungeonoftheBrutalKing.EffectManager;
import SharedData.GameSettings;

// The Assassin class represents a specific type of enemy in the game.
// It extends the Enemies class and includes attributes and methods specific to an Assassin.
public class Assassin extends Enemies {
    // Attributes for the Assassin's name, level, stats, and image path
    String enemiesname, name;
    int level;
    int hitPoints;
    int strength;
    int charisma;
    int agility;
    int intelligence;
    int wisdom;
    String imagePath;

    // EffectManager instance to manage effects applied to the Assassin
    private EffectManager effectManager = new EffectManager();

    GameSettings myGameSettings = new GameSettings();

    // Constructor to initialize an Assassin with a specific name
    public Assassin(String name) {
        this.name = name; // Assign parameter to field
        this.enemiesname = "Assassin"; // Default enemy type name
        this.level = 1; // Initial level
        this.hitPoints = 30; // Initial hit points
        this.strength = 8; // Initial strength
        this.charisma = 5; // Initial charisma
        this.agility = 7; // Initial agility
        this.intelligence = 6; // Initial intelligence
        this.wisdom = 3; // Initial wisdom
        this.imagePath = GameSettings.MonsterImagePath + "Assassin.png"; // Path to the Assassin's image
    }

    // Default constructor to initialize an Assassin with default values
    public Assassin() {
        this.enemiesname = "Assassin"; // Default enemy type name
        this.level = 1; // Initial level
        this.hitPoints = 30; // Initial hit points
        this.strength = 8; // Initial strength
        this.charisma = 5; // Initial charisma
        this.agility = 7; // Initial agility
        this.intelligence = 6; // Initial intelligence
        this.wisdom = 3; // Initial wisdom
        this.imagePath = "src\\DungeonoftheBrutalKing\\Images\\Monsters\\Assassin.png"; // Path to the Assassin's image
    }

    // Getter for the EffectManager instance
    public EffectManager getEffectManager() {
        return effectManager;
    }

    // Method to apply damage to the Assassin
    public void takeDamage(int damage) {
        this.hitPoints -= damage; // Reduce hit points by the damage amount
        if (this.hitPoints < 0) {
            this.hitPoints = 0; // Ensure hit points do not go below 0
        }
        if (isDead()) { // Check if the Assassin is dead
            System.out.println(name + " has died."); // Print a death message
        }
    }

    // Method to check if the Assassin is dead
    @Override
    public boolean isDead() {
        return this.hitPoints <= 0; // Return true if hit points are 0 or less
    }

    // Method to calculate the Assassin's attack strength
    @Override
    public int attack() {
        return (int) ((strength * 1.5) + (agility * 0.5)); // Calculate attack strength based on stats
    }

    // Getter for the image path of the Assassin
    @Override
    public String getImagePath() {
        return imagePath;
    }

    // Getter for the Assassin's name
    @Override
    public String getName() {
        return name != null ? name : enemiesname;
    }

    // Getter for the Assassin's hit points
    @Override
    public int getHP() {
        return hitPoints;
    }

    // Override the toString method to provide a string representation of the Assassin
    @Override
    public String toString() {
        return "Assassin{" +
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
