
package Enemies;

import SharedData.GameSettings;

public class Cutthroat extends Enemies {
    private String name;
    private int level;
    private int hitPoints;
    private int strength;
    private int charisma;
    private int agility;
    private int intelligence;
    private int wisdom;
    private String imagePath;

    // Constructor
    public Cutthroat() {
        this.name = "Cutthroat";
        this.level = 1;
        this.hitPoints = 30;
        this.strength = 8;
        this.charisma = 5;
        this.agility = 7;
        this.intelligence = 6;
        this.wisdom = 3;
        this.imagePath = GameSettings.MonsterImagePath + "Cutthroat.png";
    }

    public void takeDamage(int damage) {
        this.hitPoints -= damage;
        if (this.hitPoints < 0) {
            this.hitPoints = 0;
        }
        if (isDead()) {
            System.out.println(name + " has died.");
        }
    }

    @Override
    public boolean isDead() {
        return this.hitPoints <= 0;
    }

    @Override
    public int attack() {
        return (int) ((strength * 1.5) + (agility * 0.5));
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHP() {
        return hitPoints;
    }

    @Override
    public String toString() {
        return "Cutthroat{" +
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
}
