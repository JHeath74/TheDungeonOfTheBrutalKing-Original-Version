
package Armour;

import java.util.ArrayList;
import java.util.List;

import DungeonoftheBrutalKing.Charecter;

public class ArmourManager {

    protected String name;
    protected int requiredStrength;
    protected int armourDefense;
    protected int weight;
    protected String effect;
    protected StatusEffect statusEffect;

    protected static List<ArmourManager> allArmour = new ArrayList<>();

    public ArmourManager(String name, int requiredStrength, int armourDefense, int weight, String effect) {
        this.name = name;
        this.requiredStrength = requiredStrength;
        this.armourDefense = armourDefense;
        this.weight = weight;
        this.effect = effect;
        this.statusEffect = (effect != null) ? StatusEffect.valueOf(effect.toUpperCase()) : StatusEffect.NONE;
        allArmour.add(this);
    }

    // Enum to define possible status effects
    public enum StatusEffect {
        NONE, POISON, STUN, BLEED, FIRE, COLD
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

    public static List<ArmourManager> getAllArmour() {
        return allArmour;
    }

    public void setEffect(String effect) {
        this.effect = effect;
        this.statusEffect = (effect != null) ? StatusEffect.valueOf(effect.toUpperCase()) : StatusEffect.NONE;
    }

    public int getArmourDefense() {
        return armourDefense;
    }

    public String getDescription() {
        return name + ": Defence " + armourDefense + ", Weight " + weight + ", Effect " + statusEffect;
    }

    public double getDefense() {
        return (double) armourDefense;
    }

    public double getWeight() {
        return (double) weight;
    }

    public boolean equip(Charecter wearer) {
        // Default: cannot equip, override in subclass
        return false;
    }

    public boolean unequip(Charecter wearer) {
        // Default: cannot unequip, override in subclass
        return false;
    }
}
