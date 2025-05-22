
package DungeonoftheBrutalKing;

import java.util.ArrayList;
import java.util.List;

public class ShieldManager {

    // Static instance for Singleton pattern
    private static ShieldManager single_instance_shields;

    // Reference to the Singleton character instance
    protected Singleton myChar = new Singleton();

    // Shield properties
    public String name; // Name of the shield
    public int requiredStrength; // Strength required to use the shield
    public String charStrength; // Character's strength (not used in this class)
    public int defenseProvided; // Defense value provided by the shield

    // Static list to store all shields
    protected static List<ShieldManager> allShields = new ArrayList<>();

    // Constructor to initialize shield properties
    public ShieldManager(String name, int requiredStrength, int defenseProvided) {
        this.name = name; // Set the shield's name
        this.requiredStrength = requiredStrength; // Set the required strength
        this.defenseProvided = defenseProvided; // Set the defense value
        allShields.add(this); // Add the shield to the list of all shields
    }

    // Method to get the Singleton instance of ShieldManager
    public static ShieldManager Singleton() {
        // Check if the instance is null
        if (single_instance_shields == null) {
            // Create a default shield instance if none exists
            single_instance_shields = new ShieldManager("Default Shield", 0, 0);
        }
        return single_instance_shields; // Return the single instance
    }

    // Method to retrieve the list of all shields
    public static List<ShieldManager> getAllShields() {
        return allShields; // Return the list of all shields
    }

    // Method to set the required strength for the shield
    public void setRequiredStrength(int requiredStrength) {
        this.requiredStrength = requiredStrength; // Update the required strength
    }

    // Override the toString method to provide a string representation of the shield
    @Override
    public String toString() {
        return "Shield: " + name + ", Required Strength: " + requiredStrength + ", Defense: " + defenseProvided;
    }
}
