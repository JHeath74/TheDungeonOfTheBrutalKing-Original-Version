
package DungeonoftheBrutalKing;

import java.util.ArrayList;
import java.util.List;

public class ShieldManager {

    private static ShieldManager single_instance_shields;
    protected Singleton myChar = new Singleton();

    public String name;
    public int requiredStrength;
    public String charStrength;
    public int defenseProvided;

    // Static list to store all shields
    protected static List<ShieldManager> allShields = new ArrayList<>();

    public ShieldManager(String name, int requiredStrength, int defenseProvided) {
        this.name = name;
        this.requiredStrength = requiredStrength;
        this.defenseProvided = defenseProvided;
        // Add the shield to the list
        allShields.add(this);
    }

    public static ShieldManager Singleton() {
        // To ensure only one instance is created
        if (single_instance_shields == null) {
            single_instance_shields = new ShieldManager("Default Shield", 0, 0);
        }
        return single_instance_shields;
    }

    // Method to retrieve all shields
    public static List<ShieldManager> getAllShields() {
        return allShields;
    }
    
    public void setRequiredStrength(int requiredStrength) {
        this.requiredStrength = requiredStrength;
    }

    @Override
    public String toString() {
        return "Shield: " + name + ", Required Strength: " + requiredStrength + ", Defense: " + defenseProvided;
    }
}
