
package Armour;

import java.util.ArrayList;
import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import Status.StatusType;

public class ArmourManager {

    protected String name;
    protected int requiredStrength;
    protected int armourDefense;
    protected int weight;
    protected String effect;
    protected StatusType statusEffect;

    protected static List<ArmourManager> allArmour = new ArrayList<>();

    public ArmourManager(String name, int requiredStrength, int armourDefense, int weight, String effect) {
        this.name = name;
        this.requiredStrength = requiredStrength;
        this.armourDefense = armourDefense;
        this.weight = weight;
        setEffect(effect);
        allArmour.add(this);
    }

    public String getName() {
        return name;
    }

    public StatusType getStatusEffect() {
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

        if (effect == null || effect.isBlank()) {
            this.statusEffect = StatusType.NONE; // adjust if your project uses a different "no status" value
            return;
        }

        // Prefer a safe resolver if your StatusType provides one; otherwise this requires exact enum names.
        this.statusEffect = StatusType.valueOf(effect.trim().toUpperCase());
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
        return false;
    }

    public boolean unequip(Charecter wearer) {
        return false;
    }
}
