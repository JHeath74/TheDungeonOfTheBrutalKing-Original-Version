
package DungeonoftheBrutalKing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * Represents an effect that can be applied to an entity (character, weapon, or monster).
 */
public class Effect {
    private String name; // Name of the effect
    private int duration; // Duration of the effect in time units
    private Consumer<Object> effectAction; // Action to apply the effect

    public Effect(String name, int duration, Consumer<Object> effectAction) {
        this.name = name;
        this.duration = duration;
        this.effectAction = effectAction;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public void reduceDuration(int amount) {
        duration -= amount;
    }

    public boolean isExpired() {
        return duration <= 0;
    }

    public void applyEffect(Object target) {
        if (effectAction != null) {
            effectAction.accept(target);
        }
    }
}
