
package DungeonoftheBrutalKing.Narrative.Triggers;

import DungeonoftheBrutalKing.Narrative.Encounters.EncounterEvent;

import java.util.*;
import java.util.function.Consumer;

public final class TriggerManager {

    // ── Nested types ──────────────────────────────────────────────────────────

    /** Context passed to triggers when evaluated. */
    public static final class GameContext {
        public final int playerX;
        public final int playerY;

        public GameContext(int playerX, int playerY) {
            this.playerX = playerX;
            this.playerY = playerY;
        }
    }

    /**
     * A discrete trigger that is evaluated each frame/turn.
     * Returns {@code true} from {@link #tryFire} to signal it should be removed (one-shot).
     */
    public interface Trigger {
        /**
         * Evaluate this trigger against the current game context.
         *
         * @param ctx current game state
         * @return {@code true} if the trigger fired and should be removed; {@code false} to keep it active
         */
        boolean tryFire(GameContext ctx);
    }

    // ── Fields ────────────────────────────────────────────────────────────────

    private final Map<String, List<Consumer<EncounterEvent>>> listeners = new LinkedHashMap<>();
    private final Set<String> activeFlags = new HashSet<>();
    private final List<RandomQuestOnTileEnterTrigger> tileEnterTriggers = new ArrayList<>();
    private final List<Trigger> triggers = new ArrayList<>();

    // ── Generic trigger management ────────────────────────────────────────────

    /** Registers a {@link Trigger}. One-shot triggers are removed automatically after firing. */
    public void addTrigger(Trigger trigger) {
        if (trigger == null) throw new IllegalArgumentException("trigger must not be null");
        triggers.add(trigger);
    }

    /**
     * Evaluates all registered {@link Trigger}s against the given context,
     * removing any that report they have fired (one-shot).
     */
    public void evaluateTriggers(GameContext ctx) {
        if (ctx == null) return;
        triggers.removeIf(t -> t.tryFire(ctx));
    }

    // ── Tile-enter trigger management ─────────────────────────────────────────

    /** Registers a {@link RandomQuestOnTileEnterTrigger}. */
    public void add(RandomQuestOnTileEnterTrigger trigger) {
        if (trigger == null) throw new IllegalArgumentException("trigger must not be null");
        tileEnterTriggers.add(trigger);
    }

    /** Notifies all tile-enter triggers that the player entered tile (x, y). 
     * @throws Exception */
    public void onTileEnter(int x, int y) throws Exception {
        for (RandomQuestOnTileEnterTrigger trigger : tileEnterTriggers) {
            trigger.onTileEnter(x, y);
        }
    }

    /** Returns an unmodifiable view of all registered tile-enter triggers. */
    public List<RandomQuestOnTileEnterTrigger> getTileEnterTriggers() {
        return Collections.unmodifiableList(tileEnterTriggers);
    }

    // ── Event listener management ─────────────────────────────────────────────

    /** Register a listener for a specific encounter key. */
    public void on(String encounterKey, Consumer<EncounterEvent> handler) {
        if (encounterKey == null || handler == null) return;
        listeners.computeIfAbsent(encounterKey, key -> new ArrayList<>()).add(handler);
    }

    /** Fire an encounter event, notifying all registered listeners. */
    public void fire(EncounterEvent event) {
        if (event == null) return;
        List<Consumer<EncounterEvent>> handlers = listeners.get(event.getKey());
        if (handlers != null) {
            for (Consumer<EncounterEvent> h : handlers) h.accept(event);
        }
    }

    /** Remove all listeners for a specific encounter key. */
    public void off(String encounterKey) {
        listeners.remove(encounterKey);
    }

    /** Returns true if there are any listeners registered for the given key. */
    public boolean hasListeners(String encounterKey) {
        List<Consumer<EncounterEvent>> handlers = listeners.get(encounterKey);
        return handlers != null && !handlers.isEmpty();
    }

    /** Returns an unmodifiable view of all registered encounter keys. */
    public Set<String> getRegisteredKeys() {
        return Collections.unmodifiableSet(listeners.keySet());
    }

    // ── Flag management ───────────────────────────────────────────────────────

    public void setFlag(String flag)        { if (flag != null) activeFlags.add(flag); }
    public void clearFlag(String flag)      { activeFlags.remove(flag); }
    public boolean hasFlag(String flag)     { return flag != null && activeFlags.contains(flag); }
    public Set<String> getAllFlags()        { return Collections.unmodifiableSet(activeFlags); }
    public void clearAllFlags()             { activeFlags.clear(); }

    // ── Lifecycle ─────────────────────────────────────────────────────────────

    /** Clears all state — useful for scene/level transitions. */
    public void reset() {
        listeners.clear();
        tileEnterTriggers.clear();
        triggers.clear();
        activeFlags.clear();
    }
}
