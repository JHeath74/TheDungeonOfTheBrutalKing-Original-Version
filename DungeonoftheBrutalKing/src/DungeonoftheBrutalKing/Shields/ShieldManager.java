package DungeonoftheBrutalKing.Shields;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages properties and behaviors of a shield item.
 * Each instance represents a specific shield type.
 */
public class ShieldManager {

    /** Singleton instance for global access. */
    private static ShieldManager instance;

    /** Name of the shield. */
    private String name;

    /** Minimum strength required to equip the shield. */
    private int requiredStrength;

    /** Defense value provided by the shield. */
    private int defenseProvided;

    /** Static list to store all created shields. */
    private static final List<ShieldManager> allShields = new ArrayList<>();

    /**
     * Constructs a shield item and adds it to the global list.
     * @param name Name of the shield
     * @param requiredStrength Minimum strength required
     * @param defenseProvided Defense value
     */
    public ShieldManager(String name, int requiredStrength, int defenseProvided) {
        this.name = name;
        this.requiredStrength = requiredStrength;
        this.defenseProvided = defenseProvided;
        allShields.add(this);
    }

    /**
     * Returns the singleton instance of ShieldManager.
     * If not created, initializes with a default shield.
     */
    public static ShieldManager getInstance() {
        if (instance == null) {
            instance = new ShieldManager("Default Shield", 0, 0);
        }
        return instance;
    }

    /** @return the name of the shield */
    public String getName() {
        return name;
    }

    /** @return the required strength to equip the shield */
    public int getRequiredStrength() {
        return requiredStrength;
    }

    /** Sets the required strength to equip this shield. */
    public void setRequiredStrength(int requiredStrength) {
        this.requiredStrength = requiredStrength;
    }

    /** @return the defense value provided by the shield */
    public int getDefenseProvided() {
        return defenseProvided;
    }

    /** Sets the defense value provided by the shield. */
    public void setDefenseProvided(int defenseProvided) {
        this.defenseProvided = defenseProvided;
    }

    /** @return a list of all created shields */
    public static List<ShieldManager> getAllShields() {
        return allShields;
    }

    /** @return a string representation of the shield */
    @Override
    public String toString() {
        return "Shield: " + name + ", Required Strength: " + requiredStrength + ", Defense: " + defenseProvided;
    }
}
