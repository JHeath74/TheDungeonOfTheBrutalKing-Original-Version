
// src/DungeonoftheBrutalKing/ArmourManager.java
package Armour;

import java.util.ArrayList;
import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Singleton;

public class ArmourManager {

    // Singleton instance representing the character
    protected Singleton myChar = new Singleton();

    // Name of the armour
    public String name;

    // Strength required to equip the armour
    protected int requiredStrength;

    // Effect applied by the armour (e.g., status effect)
    private String effect;

    // Character's strength (static for shared access)
    public static String charStrength;

    // Status effect applied by the armour
    private static StatusEffect statusEffect;

    // Defence value provided by the armour
    private static int armourDefense;

    // Static list to store all armour instances
    protected static List<ArmourManager> allArmour = new ArrayList<>();

    // Constructor to initialize armour properties and add to the list
    public ArmourManager(String name, int requiredStrength, int armourDefense, String effect) {
        this.name = name;
        this.requiredStrength = requiredStrength;
        ArmourManager.armourDefense = armourDefense;
        this.effect = effect;
        allArmour.add(this);

        // Set the status effect based on the provided effect string
        if (effect != null) {
            ArmourManager.statusEffect = StatusEffect.valueOf(effect.toUpperCase());
        } else {
            ArmourManager.statusEffect = StatusEffect.NONE;
        }
    }

    public ArmourManager(String string, String effect2, int weight) {
		// TODO Auto-generated constructor stub
	}

	// Enum to define possible status effects
    public enum StatusEffect {
        NONE,    // No effect
        POISON,  // Poison effect
        STUN,    // Stun effect
        BLEED,   // Bleed effect
        FIRE,    // Fire effect
        COLD     // Cold effect
    }

    // Getter for the name of the armour
    public String getName() {
        return name;
    }

    // Getter for the status effect of the armour
    public StatusEffect getStatusEffect() {
        return statusEffect;
    }

    // Setter for the required strength to equip the armour
    public void setRequiredStrength(int requiredStrength) {
        this.requiredStrength = requiredStrength;
    }

    // Getter for the list of all armour instances
    public static List<ArmourManager> getAllShields() {
        return allArmour;
    }

    // Setter for the effect of the armour
    public void setEffect(String effect) {
        if (effect != null) {
            ArmourManager.statusEffect = StatusEffect.valueOf(effect.toUpperCase());
        } else {
            ArmourManager.statusEffect = StatusEffect.NONE;
        }
    }

    // Getter for the defence value of the armour
    public int getArmourDefense() {
        return armourDefense;
    }

    // Placeholder for the Singleton method (to be implemented)
    public static ArmourManager Singleton() {
        // TODO Auto-generated method stub
        return null;
    }

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public double getDefense() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getWeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void equip(Charecter wearer) {
		// TODO Auto-generated method stub
		
	}

	public void unequip(Charecter wearer) {
		// TODO Auto-generated method stub
		
	}
}
