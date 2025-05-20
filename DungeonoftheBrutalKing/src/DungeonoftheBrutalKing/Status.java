
package DungeonoftheBrutalKing;

import java.util.function.Consumer;

/**
 * Represents a status effect that can be applied to a character.
 * Each status has a name, duration, and an effect that modifies the character's attributes or behavior.
 */
public class Status {
    private String name; // Name of the status (e.g., "Poison", "Hunger")
    private int duration; // Duration of the status in time units
    private Consumer<Charecter> effect; // Effect applied to the character

    /**
     * Constructor to initialize a status effect.
     *
     * @param name    The name of the status.
     * @param duration The duration of the status in time units.
     * @param effect   The effect to be applied to the character.
     */
    public Status(String name, int duration, Consumer<Charecter> effect) {
        this.name = name;
        this.duration = duration;
        this.effect = effect;
    }

    /**
     * Gets the name of the status.
     *
     * @return The name of the status.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the remaining duration of the status.
     *
     * @return The duration of the status.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Reduces the duration of the status by a specified amount.
     *
     * @param amount The amount to reduce the duration by.
     */
    public void reduceDuration(int amount) {
        duration -= amount;
    }

    /**
     * Checks if the status has expired.
     *
     * @return True if the duration is 0 or less, false otherwise.
     */
    public boolean isExpired() {
        return duration <= 0;
    }

    /**
     * Applies the effect of the status to the specified character.
     *
     * @param character The character to apply the effect to.
     */
    public void applyEffect(Charecter character) {
        if (effect != null) {
            effect.accept(character);
        }
    }
}
