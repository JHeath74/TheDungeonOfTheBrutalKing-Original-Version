
// Weapons.java
package DungeonoftheBrutalKing;

public class Weapons {

    protected Singleton myChar = new Singleton();

    public String name;
    public int requiredStrength;
    public static String charStrength;
    private static int damage;
    private static double criticalHitChance;
    private static StatusEffect statusEffect;

    public Weapons(String name, int requiredStrength, int damage, String effect) {
        this.name = name;
        this.requiredStrength = requiredStrength;
        Weapons.damage = damage;
        if (effect != null) {
            Weapons.statusEffect = StatusEffect.valueOf(effect.toUpperCase());
        } else {
            Weapons.statusEffect = StatusEffect.NONE;
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

    public int getDamage() {
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
            Weapons.statusEffect = StatusEffect.valueOf(effect.toUpperCase());
        } else {
            Weapons.statusEffect = StatusEffect.NONE;
        }
    }

    public void setAttackDamage(int attackDamage) {
        Weapons.damage = attackDamage;
    }

	public static Weapons Singleton() {
		// TODO Auto-generated method stub
		return null;
	}
}
