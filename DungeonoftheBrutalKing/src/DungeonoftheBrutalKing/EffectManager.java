
package DungeonoftheBrutalKing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Manages effects for an entity (character, weapon, or monster).
 */
public class EffectManager {
    private List<Effect> activeEffects = new ArrayList<>();

    public void addEffect(Effect effect) {
        activeEffects.add(effect);
    }

    public void removeEffect(String effectName) {
        activeEffects.removeIf(effect -> effect.getName().equals(effectName));
    }

    public boolean hasEffect(String effectName) {
        return activeEffects.stream().anyMatch(effect -> effect.getName().equals(effectName));
    }

    public List<Effect> getActiveEffects() {
        return new ArrayList<>(activeEffects);
    }

    public void updateEffects(Object target, int timeElapsed) {
        Iterator<Effect> iterator = activeEffects.iterator();
        while (iterator.hasNext()) {
            Effect effect = iterator.next();
            effect.reduceDuration(timeElapsed);
            effect.applyEffect(target);
            if (effect.isExpired()) {
                iterator.remove();
            }
        }
    }
}
