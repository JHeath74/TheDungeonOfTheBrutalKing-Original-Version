
// Weapons.java
package DungeonoftheBrutalKing;

public class WeaponManager {

    protected Singleton myChar = new Singleton();

    public String name;
    public int requiredStrength;
    public static String charStrength;
    private static int damage;
    private static double criticalHitChance;
    private static StatusEffect statusEffect;

    public WeaponManager(String name, int requiredStrength, int damage, String effect) {
        this.name = name;
        this.requiredStrength = requiredStrength;
        this.damage = damage;
        if (effect != null) {
            WeaponManager.statusEffect = StatusEffect.valueOf(effect.toUpperCase());
        } else {
            WeaponManager.statusEffect = StatusEffect.NONE;
        }
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

    public static int getDamage() {
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

    public int getAttackDamage() {
        return damage;
    }

    public void setEffect(String effect) {
        if (effect != null) {
            WeaponManager.statusEffect = StatusEffect.valueOf(effect.toUpperCase());
        } else {
            WeaponManager.statusEffect = StatusEffect.NONE;
        }
    }

    public void setAttackDamage(int attackDamage) {
        WeaponManager.damage = attackDamage;
    }

	public static WeaponManager Singleton() {
		// TODO Auto-generated method stub
		return null;
	}
}
