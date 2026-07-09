
package DungeonoftheBrutalKing.Narrative.Encounters;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class EncounterEvent {

    private final EncounterType type;
    private final String key;
    private final LocationType locationType;
    private final Map<String, Object> data;

    /**
     * Full constructor.
     *
     * @param type         the encounter event type (required)
     * @param key          identifier for the specific target (npcId, itemId, etc.)
     * @param locationType static or random location classification
     * @param data         optional extra payload
     */
    public EncounterEvent(EncounterType type,
                          String key,
                          LocationType locationType,
                          Map<String, Object> data) {
        this.type         = Objects.requireNonNull(type, "type must not be null");
        this.key          = key != null ? key : "";
        this.locationType = locationType != null ? locationType : LocationType.UNKNOWN;
        this.data         = data != null
                ? Collections.unmodifiableMap(new HashMap<>(data))
                : Collections.emptyMap();
    }

    /** Convenience — no extra data map. */
    public EncounterEvent(EncounterType type, String key, LocationType locationType) {
        this(type, key, locationType, null);
    }

    /** Backwards-compatible — defaults to UNKNOWN location type. */
    public EncounterEvent(EncounterType type, String key) {
        this(type, key, LocationType.UNKNOWN, null);
    }

    /** Minimal — type only. */
    public EncounterEvent(EncounterType type) {
        this(type, "", LocationType.UNKNOWN, null);
    }

    // ── Static Factory Methods ────────────────────────────────────────────────

    /** Full factory — type, key, locationType and data map. */
    public static EncounterEvent of(EncounterType type,
                                    String key,
                                    LocationType locationType,
                                    Map<String, Object> data) {
        return new EncounterEvent(type, key, locationType, data);
    }

    /** Factory — type, key and locationType. */
    public static EncounterEvent of(EncounterType type,
                                    String key,
                                    LocationType locationType) {
        return new EncounterEvent(type, key, locationType, null);
    }

    /** Factory — type and key, defaults to UNKNOWN location type. */
    public static EncounterEvent of(EncounterType type, String key) {
        return new EncounterEvent(type, key, LocationType.UNKNOWN, null);
    }

    /** Factory — type only. */
    public static EncounterEvent of(EncounterType type) {
        return new EncounterEvent(type, "", LocationType.UNKNOWN, null);
    }

    // ── Accessors ────────────────────────────────────────────────────────────

    public EncounterType       getType()         { return type; }
    public String              getKey()          { return key; }
    public LocationType        getLocationType() { return locationType; }
    public Map<String, Object> getData()         { return data; }

    // ── Location helpers — delegate to LocationType.Category ─────────────────

    /** Returns true when this event fired at a fixed map location (inn, shop, temple, etc.). */
    public boolean isStaticLocation() {
        return locationType.isStatic();
    }

    /** Returns true when this event fired at a procedurally placed / random tile. */
    public boolean isRandomLocation() {
        return locationType.isRandom();
    }

    /** Returns true when the location classification is not yet set. */
    public boolean isUnknownLocation() {
        return locationType == LocationType.UNKNOWN;
    }

    // ── Data helpers ──────────────────────────────────────────────────────────

    /** Safely retrieve a typed value from the data map. Returns null if absent or wrong type. */
    @SuppressWarnings("unchecked")
    public <T> T get(String dataKey) {
        try {
            return (T) data.get(dataKey);
        } catch (ClassCastException e) {
            return null;
        }
    }

    /** Returns true if the data map contains the given key. */
    public boolean hasData(String dataKey) {
        return data.containsKey(dataKey);
    }

    // ── Object overrides ──────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "EncounterEvent{type=" + type
                + ", key='" + key + "'"
                + ", location=" + locationType
                + (!data.isEmpty() ? ", data=" + data : "")
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EncounterEvent)) return false;
        EncounterEvent other = (EncounterEvent) o;
        return type == other.type
                && key.equals(other.key)
                && locationType == other.locationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, key, locationType);
    }
}
