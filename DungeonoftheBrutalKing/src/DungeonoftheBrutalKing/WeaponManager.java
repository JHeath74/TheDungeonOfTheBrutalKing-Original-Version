
// Weapons.java
package DungeonoftheBrutalKing;

public class WeaponManager {

    protected Singleton myChar = new Singleton();

    public String name;
    public int requiredStrength;
    public static String charStrength;
    private static double damage;
    private static double weight;
    private static double criticalHitChance;
    private static StatusEffect statusEffect;

    public WeaponManager(String name, int requiredStrength, double damage, String effect, double weight) {
        this.name = name;
        this.requiredStrength = requiredStrength;
        this.damage = damage;
        this.weight = weight;
        if (effect != null) {
            WeaponManager.statusEffect = StatusEffect.valueOf(effect.toUpperCase());
        } else {
            WeaponManager.statusEffect = StatusEffect.NONE;
        }
    }

    public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
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
