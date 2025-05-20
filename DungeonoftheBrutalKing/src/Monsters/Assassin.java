package Monsters;

import DungeonoftheBrutalKing.EffectManager;
import DungeonoftheBrutalKing.Enemies;

public class Assassin extends Enemies{
    String enemiename, name;
    int level;
    int hitPoints;
    int strength;
    int charisma;
    int agility;
    int intelligence;
    int wisdom;
    String imagePath;
    
    private EffectManager effectManager = new EffectManager();


    // Constructor
    public Assassin(String name) {
        this.enemiename = "Assassin";
        this.name = name;
        this.level = 1;
        this.hitPoints = 30;
        this.strength = 8;
        this.charisma = 5;
        this.agility = 7;
        this.intelligence = 6;
        this.wisdom = 3;
        this.imagePath = "src/DungeonftheBrutalKing/Monsters/Assassin.png"; // Set the image path
    }
    
    public Assassin() {
        this.enemiename = "Assassin";
        this.level = 1;
        this.hitPoints = 30;
        this.strength = 8;
        this.charisma = 5;
        this.agility = 7;
        this.intelligence = 6;
        this.wisdom = 3;
        this.imagePath = "src/DungeonftheBrutalKing/Monsters/Assassin.png"; // Set the image path
    }

    public EffectManager getEffectManager() {
        return effectManager;
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

    // Getter for imagePath
    public String getImagePath() {
        return imagePath;
    }

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