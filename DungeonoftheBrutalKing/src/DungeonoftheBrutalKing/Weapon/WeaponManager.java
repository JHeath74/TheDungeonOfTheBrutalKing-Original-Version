package DungeonoftheBrutalKing.Weapon;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Status.HasHitPoints;
import DungeonoftheBrutalKing.Status.StatusType;

/**
 * Manages properties and behaviors of a weapon item.
 * Each instance represents a specific weapon type.
 */
public class WeaponManager {

    /** Name of the weapon. */
    protected String name;

    /** Minimum strength required to equip. */
    protected int requiredStrength;

    /** Damage value of the weapon. */
    protected int damage;

    /** Weight of the weapon. */
    private int weight;

    /** Critical hit chance (0.0 - 1.0). */
    private double criticalHitChance;

    /** Status effect granted by the weapon, if any. */
    private StatusType statusEffect = StatusType.NONE;

    /** Singleton instance (if needed for global access). */
    private static WeaponManager instance;

    /**
     * Returns the singleton instance of WeaponManager.
     * Note: Only use this if you want a single global WeaponManager.
     */
    public static WeaponManager getInstance() {
        if (instance == null) {
            // Default weapon, adjust as needed
            instance = new WeaponManager("Default", 0, 0, null, 0);
        }
        return instance;
    }

    /**
     * Constructs a weapon item.
     * @param name Name of the weapon
     * @param requiredStrength Minimum strength required
     * @param damage Damage value
     * @param effect Status effect as string (optional)
     * @param weight Weight of the weapon
     */
    public WeaponManager(String name, int requiredStrength, int damage, String effect, int weight) {
        this.name = name;
        this.requiredStrength = requiredStrength;
        this.damage = damage;
        this.weight = weight;
        setEffect(effect);
    }

    /** @return the weight of the weapon */
    public double getWeight() {
        return weight;
    }

    /** Sets the weight of the weapon. */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /** @return the name of the weapon */
    public String getName() {
        return name;
    }

    /** @return the damage value of the weapon */
    public double getDamage() {
        return damage;
    }

    /** Sets the damage value of the weapon. */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /** @return the critical hit chance (0.0 - 1.0) */
    public double getCriticalHitChance() {
        return criticalHitChance;
    }

    /** Sets the critical hit chance (0.0 - 1.0). */
    public void setCriticalHitChance(double criticalHitChance) {
        this.criticalHitChance = Math.max(0.0, Math.min(1.0, criticalHitChance));
    }

    /** @return the status effect granted by the weapon */
    public StatusType getStatusEffect() {
        return statusEffect;
    }

    /** Sets the required strength to equip this weapon. */
    public void setRequiredStrength(int requiredStrength) {
        this.requiredStrength = requiredStrength;
    }

    /**
     * Sets the effect string and parses it into a StatusType.
     * If the effect is invalid or blank, sets to StatusType.NONE.
     */
    public void setEffect(String effect) {
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

    /**
     * @return a description of the weapon, including damage, weight, and effect
     */
    public String getDescription() {
        return String.format("%s (Damage: %d, Weight: %d, Effect: %s)", name, damage, weight, statusEffect);
    }

    /**
     * Applies the weapon's combat effect to a character target.
     * @param target The character being affected
     */
    public void applyCombatEffect(Character target) {
        // Implement effect logic if needed
    }

    /**
     * Applies the weapon's combat effect to a generic hit points target.
     * @param target The target being affected
     */
    public void applyCombatEffect(HasHitPoints target) {
        // Implement effect logic if needed
    }

    /**
     * Attempts to equip this weapon to the given character.
     * @param wearer The character attempting to equip
     * @return true if equipped successfully, false otherwise
     */
    public boolean equip(Character wearer) {
        // Implement equip logic as needed
        return false;
    }

    /**
     * Attempts to unequip this weapon from the given character.
     * @param wearer The character attempting to unequip
     * @return true if unequipped successfully, false otherwise
     */
    public boolean unequip(Character wearer) {
        // Implement unequip logic as needed
        return false;
    }

    /**
     * Applies the weapon's effect to a generic hit points target.
     * @param target The target being affected
     */
    public void applyEffect(HasHitPoints target) {
        // Implement effect logic if needed
    }
}
