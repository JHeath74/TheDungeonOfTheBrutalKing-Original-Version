
package DungeonoftheBrutalKing.Narrative.Triggers;

import DungeonoftheBrutalKing.Narrative.Encounters.EncounterEvent;

import java.util.*;
import java.util.function.Consumer;

public final class TriggerManager {

    private final Map<String, List<Consumer<EncounterEvent>>> listeners = new LinkedHashMap<>();
    private final Set<String> activeFlags = new HashSet<>();

    /** Register a listener for a specific encounter id. */
    public void on(String encounterId, Consumer<EncounterEvent> handler) {
        if (encounterId == null || handler == null) return;
        listeners.computeIfAbsent(encounterId, k -> new ArrayList<>()).add(handler);
    }

    /** Fire an encounter event, notifying all registered listeners. */
    public void fire(EncounterEvent event) {
        if (event == null) return;
        List<Consumer<EncounterEvent>> handlers = listeners.get(event.getId());
        if (handlers != null) {
            for (Consumer<EncounterEvent> h : handlers) h.accept(event);
        }
    }

    public void setFlag(String flag)      { if (flag != null) activeFlags.add(flag); }
    public void clearFlag(String flag)    { activeFlags.remove(flag); }
    public boolean hasFlag(String flag)   { return flag != null && activeFlags.contains(flag); }
    public Set<String> getAllFlags()      { return Collections.unmodifiableSet(activeFlags); }
}
