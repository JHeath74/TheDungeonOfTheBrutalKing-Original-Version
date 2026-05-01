package DungeonoftheBrutalKing.Armour;

import java.util.ArrayList;
import java.util.List;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Status.StatusType;

/**
 * Manages properties and behaviors of an armour item.
 * Each instance represents a specific armour type.
 */
public class ArmourManager {

    /** Singleton instance for global access (if needed). */
    private static ArmourManager instance;

    /** Name of the armour. */
    protected String name;

    /** Minimum strength required to equip. */
    protected int requiredStrength;

    /** Defense value provided by the armour. */
    protected int armourDefense;

    /** Weight of the armour. */
    protected int weight;

    /** String representation of the effect/status (for parsing). */
    private String effect;

    /** Status effect granted by the armour, if any. */
    private StatusType statusEffect = StatusType.NONE;

    /** List of all created armour items. */
    protected static final List<ArmourManager> allArmour = new ArrayList<>();

    /**
     * Constructs an armour item and adds it to the global list.
     * @param name Name of the armour
     * @param requiredStrength Minimum strength required
     * @param armourDefense Defense value
     * @param weight Weight of the armour
     * @param effect Status effect as string (optional)
     */
    public ArmourManager(String name, int requiredStrength, int armourDefense, int weight, String effect) {
        this.name = name;
        this.requiredStrength = requiredStrength;
        this.armourDefense = armourDefense;
        this.weight = weight;
        setEffect(effect);
        allArmour.add(this);
    }

    /**
     * Returns the singleton instance of ArmourManager.
     * If not created, initializes with a default armour.
     */
    public static ArmourManager getInstance() {
        if (instance == null) {
            instance = new ArmourManager("Default Armour", 0, 0, 0, null);
        }
        return instance;
    }

    /** @return the name of the armour */
    public String getName() {
        return name;
    }

    /** @return the status effect granted by the armour */
    public StatusType getStatusEffect() {
        return statusEffect;
    }

    /** Sets the required strength to equip this armour. */
    public void setRequiredStrength(int requiredStrength) {
        this.requiredStrength = requiredStrength;
    }

    /** @return a list of all armour items */
    public static List<ArmourManager> getAllArmour() {
        return allArmour;
    }

    /**
     * Sets the effect string and parses it into a StatusType.
     * If the effect is invalid or blank, sets to StatusType.NONE.
     */
    public void setEffect(String effect) {
        this.effect = effect;
        if (effect == null || effect.isBlank()) {
            this.statusEffect = StatusType.NONE;
        } else {
            try {
                this.statusEffect = StatusType.valueOf(effect.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                this.statusEffect = StatusType.NONE;
            }
        }
    }

    /** @return the defense value of the armour */
    public int getArmourDefense() {
        return armourDefense;
    }

    /**
     * @return a description of the armour, including defense, weight, and effect
     */
    public String getDescription() {
        return name + ": Defence " + armourDefense + ", Weight " + weight + ", Effect " + statusEffect;
    }

    /** @return the defense value as a double */
    public double getDefense() {
        return (double) armourDefense;
    }

    /** @return the weight as a double */
    public double getWeight() {
        return (double) weight;
    }

    /**
     * Attempts to equip this armour to the given character.
     * @param wearer The character attempting to equip
     * @return true if equipped successfully, false otherwise
     */
    public boolean equip(Character wearer) {
        // Implement equip logic as needed
        return false;
    }

    /**
     * Attempts to unequip this armour from the given character.
     * @param wearer The character attempting to unequip
     * @return true if unequipped successfully, false otherwise
     */
    public boolean unequip(Character wearer) {
        // Implement unequip logic as needed
        return false;
    }
}
