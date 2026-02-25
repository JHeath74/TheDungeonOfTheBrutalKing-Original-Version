
// src/Weapon/WeaponManager.java
package Weapon;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Singleton;
import Status.HasHitPoints;
import Status.StatusType;

public class WeaponManager {

    protected Singleton myChar = new Singleton();

    public String name;
    public int requiredStrength;
    public static String charStrength;
    private int damage;
    private int weight;
    private double criticalHitChance;
    private StatusType statusEffect;

    public WeaponManager(String name, int requiredStrength, int damage, String effect, int weight) {
        this.name = name;
        this.requiredStrength = requiredStrength;
        this.damage = damage;
        this.weight = weight;
        setEffect(effect);
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public double getDamage() {
        return damage;
    }

    public double getCriticalHitChance() {
        return criticalHitChance;
    }

    public StatusType getStatusEffect() {
        return statusEffect;
    }

    public void setRequiredStrength(int requiredStrength) {
        this.requiredStrength = requiredStrength;
    }

    public void setEffect(String effect) {
        if (effect == null || effect.isBlank()) {
            this.statusEffect = StatusType.NONE; // adjust if your project uses a different "no status" value
            return;
        }
        this.statusEffect = StatusType.valueOf(effect.trim().toUpperCase());
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public static WeaponManager Singleton() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

    public void applyCombatEffect(Charecter target) {
        // TODO Auto-generated method stub
    }

    public void applyCombatEffect(HasHitPoints target) {
        // TODO Auto-generated method stub
    }

    public boolean equip(Charecter wearer) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean unequip(Charecter wearer) {
        // TODO Auto-generated method stub
        return false;
        // TODO Auto-generated method stub
    }

    public void applyEffect(HasHitPoints target) {
        // TODO Auto-generated method stub
    }
}
