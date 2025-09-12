
// src/DungeonoftheBrutalKing/Enemies.java
package Enemies;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Enemies {
    private static final Map<String, String> monsterImages = new HashMap<>();

    protected String name;
    protected int level;
    protected int hitPoints;
    protected int strength;
    protected int charisma;
    protected int agility;
    protected int intelligence;
    protected int wisdom;
    protected String imagePath;

    static {
    //    monsterImages.put("Goblin", "src/resources/images/goblin.png");
    //    monsterImages.put("Orc", "src/resources/images/orc.png");
    //    monsterImages.put("Dragon", "src/resources/images/dragon.png");
     //   monsterImages.put("Troll", "src/resources/images/troll.png");
    }

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
    
    public Enemies()
    {
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

    public int getHitPoints() {
        return hitPoints;
    }

    public int getStrength() {
        return strength;
    }

    public int getAgility() {
        return agility;
    }

    public boolean isDead() {
        return this.hitPoints <= 0;
    }

    public int attack() {
        return (int) ((strength * 1.5) + (agility * 0.5));
    }

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

    public String getMonsterImagePath() {
        Object[] monsters = monsterImages.keySet().toArray();
        String selectedMonster = (String) monsters[new Random().nextInt(monsters.length)];
        this.name = selectedMonster;
        return monsterImages.get(selectedMonster);
    }

    public static Enemies Singleton() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getName() {
        return name;
    }

    public int getHP() {
        return hitPoints;
    }

    public int getWeaponDamage() {
        Random random = new Random();
        int baseDamage = (int) (strength * 1.2);
        int randomFactor = random.nextInt(5) + 1;
        return baseDamage + randomFactor;
    }

    public void setHP(int i) {
        this.hitPoints = i;
    }
}
