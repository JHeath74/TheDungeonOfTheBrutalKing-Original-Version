
// src/Armour/Armour.java
package DungeonoftheBrutalKing;

public class Armour {

    protected Singleton myChar = new Singleton();

    public String name;
    protected int requiredStrength;
    public static String charStrength;
    private static int damage;
    private static double criticalHitChance;
    private static StatusEffect statusEffect;
    private static int armourDefense;

    public Armour(String name, int requiredStrength,int armourDefense, String effect) {
        this.name = name;
        this.requiredStrength = requiredStrength;

        if (effect != null) {
            Armour.statusEffect = StatusEffect.valueOf(effect.toUpperCase());
        } else {
            Armour.statusEffect = StatusEffect.NONE;
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

    public StatusEffect getStatusEffect() {
        return statusEffect;
    }

    public void setRequiredStrength(int requiredStrength) {
        this.requiredStrength = requiredStrength;
    }

    public void setEffect(String effect) {
        if (effect != null) {
            Armour.statusEffect = StatusEffect.valueOf(effect.toUpperCase());
        } else {
            Armour.statusEffect = StatusEffect.NONE;
        }
    }

    public void setArmourDefense(int armourDefense) {
        Armour.armourDefense = armourDefense;
    }

    public int getArmourDefense() {
        return armourDefense;
    }

    public static Armour Singleton() {
        // TODO Auto-generated method stub
        return null;
    }
}
