
package DungeonoftheBrutalKing;

import java.util.function.Consumer;

public class Status {
    private String name; // Name of the status (e.g., "Poison", "Hunger")
    private int duration; // Duration of the status in time units
    private Consumer<Charecter> effect; // Effect applied to the character

    // Constructor
    public Status(String name, int duration, Consumer<Charecter> effect) {
        this.name = name;
        this.duration = duration;
        this.effect = effect;
    }

    // Get the name of the status
    public String getName() {
        return name;
    }

    // Get the remaining duration of the status
    public int getDuration() {
        return duration;
    }

    // Reduce the duration of the status
    public void reduceDuration(int amount) {
        duration -= amount;
    }

    // Check if the status has expired
    public boolean isExpired() {
        return duration <= 0;
    }

    // Apply the effect of the status to the character
    public void applyEffect(Charecter character) {
        if (effect != null) {
            effect.accept(character);
        }
    }
}
