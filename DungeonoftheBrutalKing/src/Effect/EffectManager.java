
package Effect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.TimeClock;
import Status.Fire;
import Status.Status;

/*Effect: Represents temporary changes (buffs, debuffs) with duration and expiration logic.
EffectManager: Handles registration, updating, and removal of effects.*/

/**
 * Manages the effects applied to entities in the game.
 * This class is responsible for registering, updating, and removing effects.
 * It uses a singleton pattern to ensure a single instance is used throughout the application.
 */

/*
 * predefinedEffects: A Map<String, Effect> that stores predefined effects by
 * their names. These are reusable effects that can be applied multiple times to
 * different entities.
 *
 * activeEffects: A List<Effect> that stores currently active effects. These are
 * effects that have been applied to entities and are being tracked for their
 * duration and expiration.
 */

public class EffectManager {
    private static EffectManager instance; // Singleton instance of EffectManager
    private List<Effect> activeEffects = new ArrayList<>(); // List of currently active effects
    private Map<String, Effect> predefinedEffects = new HashMap<>(); // Map of predefined effects
    private TimeClock timeClock = TimeClock.Singleton(); // Reference to the game's time clock

    /**
     * Default constructor for EffectManager.
     * Initializes the manager for handling effects.
     */
    public EffectManager() {}

    /**
     * Retrieves the singleton instance of EffectManager.
     *
     * @return The singleton instance of EffectManager.
     */
    public static EffectManager Singleton() {
        if (instance == null) {
            instance = new EffectManager();
        }
        return instance;
    }

    /**
     * Registers a new effect to be managed.
     *
     * @param effectName The name of the effect.
     * @param startTime  The time the effect starts.
     * @param duration   The duration of the effect in time units.
     * @param onExpire   The action to perform when the effect expires.
     */
    public void registerEffect(String effectName, int startTime, int duration, Runnable onExpire) {
        activeEffects.add(new Effect(effectName, startTime, duration, onExpire));
    }

    /**
     * Registers a predefined effect.
     *
     * @param effectName The name of the effect.
     * @param duration   The duration of the effect in time units.
     * @param onExpire   The action to perform when the effect expires.
     */
    public void registerPredefinedEffect(String effectName, int duration, Runnable onExpire) {
        predefinedEffects.put(effectName, new Effect(effectName, 0, duration, onExpire));
    }

    /**
     * Retrieves a predefined effect by name.
     *
     * @param effectName The name of the effect.
     * @return The predefined effect, or null if not found.
     */
    public Effect getPredefinedEffect(String effectName) {
        return predefinedEffects.get(effectName);
    }

    /**
     * Updates the active effects, removing expired ones and triggering their expiration actions.
     */

 // EffectManager class modification
 public void updateEffects() {
     int currentTime = timeClock.getCurrentHour();
     Iterator<Effect> iterator = activeEffects.iterator();

     while (iterator.hasNext()) {
         Effect effect = iterator.next();
         if (effect.isCured() || currentTime - effect.getStartTime() >= effect.getDuration()) {
             effect.getOnExpire().run();
             iterator.remove();
         }
     }
 }


//EffectManager.java
public void addStatus(Status status, Charecter target) {
  target.addStatus(status);      // Store the status on the character
  status.applyEffect(target);    // Apply the effect (attack reduction, fire damage)
}




}
