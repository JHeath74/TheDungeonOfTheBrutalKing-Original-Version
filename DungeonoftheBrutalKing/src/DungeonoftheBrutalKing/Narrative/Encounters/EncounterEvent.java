
// File: `src/DungeonoftheBrutalKing/Quests/EncounterEvent.java`
package DungeonoftheBrutalKing.Narrative.Encounters;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import DungeonoftheBrutalKing.Narrative.Core.QuestManager;

/**
 * Quest-routing event sent to {@link QuestManager#onEncounter(EncounterEvent)}.
 *
 * Specific identifiers (npcId, itemId, locationId, dialogChoiceId, uiActionId, flagId, etc.)
 * go into {@link #getKey()} and extra details go into {@link #getData()}.
 */
public final class EncounterEvent {
    private final EncounterType type;
    private final String key;
    private final Map<String, Object> data;

    public EncounterEvent(EncounterType type) {
        this(type, null, null);
    }

    public EncounterEvent(EncounterType type, String key) {
        this(type, key, null);
    }

    public EncounterEvent(EncounterType type, String key, Map<String, Object> data) {
        this.type = Objects.requireNonNull(type, "type");
        this.key = key;
        if (data == null || data.isEmpty()) {
            this.data = Collections.emptyMap();
        } else {
            this.data = Collections.unmodifiableMap(new HashMap<>(data));
        }
    }

    public EncounterType getType() {
        return type;
    }

    /**
     * Optional identifier for what was encountered (npcId, itemId, locationId, etc.).
     */
    public String getKey() {
        return key;
    }

    /**
     * Backward-compatible alias for older code that used `id`.
     */
    public String getId() {
        return key;
    }

    /**
     * Extra details for quests to inspect (immutable).
     */
    public Map<String, Object> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "EncounterEvent{type=" + type + ", key=" + key + ", data=" + data + "}";
    }

    public static EncounterEvent of(EncounterType type) {
        return new EncounterEvent(type, null, null);
    }

    public static EncounterEvent of(EncounterType type, String key) {
        return new EncounterEvent(type, key, null);
    }

    public static EncounterEvent of(EncounterType type, String key, Map<String, Object> data) {
        return new EncounterEvent(type, key, data);
    }
}
