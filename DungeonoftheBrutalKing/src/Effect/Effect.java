
package Effect;
import java.util.function.Consumer;

import DungeonoftheBrutalKing.Charecter;



/**
 * Represents an effect that can be applied to various entities in the game, such as characters, weapons, armor, or shields.
 *
 * What it affects:
 * - Charecter: Can modify attributes like health, strength, or apply statuses (e.g., "Poisoned", "Shielded").
 * - Weapon: Can enhance damage, add elemental effects, or grant special abilities.
 * - Armour: Can increase defense, add resistances, or reduce damage taken.
 * - Shield: Can improve block rate, durability, or add special defensive properties.
 *
 * What the effects are:
 * - Temporary changes that last for a specified duration.
 * - Can include buffs (positive effects) or debuffs (negative effects).
 * - Defined by a name, duration, and an action to be performed on the target entity.
 */

public class Effect {
	private String name; // Name of the effect
    private int startTime; // Time the effect started
    private int duration; // Duration of the effect
    private Runnable onExpire; // Action to perform when the effect expires
    private boolean cured = false; // Flag to indicate if the effect is cured
    private boolean isCured = false; // Flag to indicate if the effect is cured
    private Consumer<Object> effectAction; // Action to apply the effect

    /**
     * Constructor to initialize an Effect object.
     *
     * @param name         The name of the effect.
     * @param duration     The duration of the effect in time units.
     * @param effectAction The action to apply the effect on a target.
     * @param startTime The time the effect starts, typically represented in game time units (e.g., hours or ticks).

     */
    public Effect(String name, int startTime, int duration, Runnable onExpire) {
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
        this.onExpire = onExpire;
    }

    /**
     * Gets the name of the effect.
     *
     * @return The name of the effect.
     */
    public String getName() {
        return name;
    }

    public int getStartTime() {
        return startTime;
    }

    public Runnable getOnExpire() {
        return onExpire;
    }

    public boolean isCured() {
        return cured;
    }

    public void cure() {
        this.cured = true;
    }

    /**
     * Gets the duration of the effect.
     *
     * @return The duration of the effect in time units.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Reduces the duration of the effect by a specified amount.
     *
     * @param amount The amount to reduce the duration by.
     */
    public void reduceDuration(int amount) {
        duration -= amount;
    }

    /**
     * Checks if the effect has expired.
     *
     * @return True if the effect's duration is less than or equal to zero, otherwise false.
     */
    public boolean isExpired() {
        return duration <= 0;
    }

    /**
     * Applies the effect to a target.
     *
     * @param target The target object to which the effect is applied.
     */
    public void applyEffect(Object target) {
        if (effectAction != null) {
            effectAction.accept(target);
        }
    }

    /**
     * Removes the effect from the character.
     */
    public void removeFromCharacter() {
        Charecter character = Charecter.Singleton(); // Get the singleton instance of Charecter
        character.removeEffect(name); // Remove the effect by name
    }
}
