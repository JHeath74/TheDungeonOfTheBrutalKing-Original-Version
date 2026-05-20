
package DungeonoftheBrutalKing.Narrative.Encounters;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public final class EncounterEvent {
    private final EncounterType type;
    private final String id;
    private final Map<String, Object> data;

    private EncounterEvent(EncounterType type, String id, Map<String, Object> data) {
        this.type = Objects.requireNonNull(type, "type must not be null");
        this.id   = id;
        this.data = data != null ? Collections.unmodifiableMap(data) : Collections.emptyMap();
    }

    public static EncounterEvent of(EncounterType type, String id) {
        return new EncounterEvent(type, id, null);
    }

    public static EncounterEvent of(EncounterType type, String id, Map<String, Object> data) {
        return new EncounterEvent(type, id, data);
    }

    public EncounterType getType() { return type; }
    public String getId()          { return id; }
    public Map<String, Object> getData() { return data; }

    @Override
    public String toString() {
        return "EncounterEvent{type=" + type + ", id='" + id + "', data=" + data + "}";
    }
}
