
// Weapons.java
package Weapon;

import DungeonoftheBrutalKing.Singleton;

public class WeaponManager {

    protected Singleton myChar = new Singleton();

    public String name;
    public int requiredStrength;
    public static String charStrength;
    private static int damage;
    private static int weight;
    private static double criticalHitChance;
    private static StatusEffect statusEffect;

    public WeaponManager(String name, int requiredStrength, int damage, String effect, int weight) {
        this.name = name;
        this.requiredStrength = requiredStrength;
        WeaponManager.damage = damage;
        WeaponManager.weight = weight;
        if (effect != null) {
            WeaponManager.statusEffect = StatusEffect.valueOf(effect.toUpperCase());
        } else {
            WeaponManager.statusEffect = StatusEffect.NONE;
        }
    }

    public double getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		WeaponManager.weight = weight;
	}

	public enum StatusEffect {
        NONE,
        POISON,
        STUN,
        BLEED,
        FIRE,
        COLD,
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

    public StatusEffect getStatusEffect() {
        return statusEffect;
    }

    public void setRequiredStrength(int requiredStrength) {
        this.requiredStrength = requiredStrength;
    }

    public void setEffect(String effect) {
        if (effect != null) {
            WeaponManager.statusEffect = StatusEffect.valueOf(effect.toUpperCase());
        } else {
            WeaponManager.statusEffect = StatusEffect.NONE;
        }
    }

    public void setDamage(int damage) {
        WeaponManager.damage = damage;
    }

	public static WeaponManager Singleton() {
		// TODO Auto-generated method stub
		return null;
	}
}
