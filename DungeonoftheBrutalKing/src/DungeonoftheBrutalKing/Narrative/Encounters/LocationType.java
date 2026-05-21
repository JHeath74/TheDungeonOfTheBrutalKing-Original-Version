
package DungeonoftheBrutalKing.Narrative.Encounters;

/**
 * Classifies whether a game location is fixed on the map (STATIC)
 * or spawned/procedurally placed (RANDOM).
 */
public enum LocationType {

    // ── Static (always at the same map position) ─────────────────────────────
    INN                  (Category.STATIC),
    QUEST_BOARD          (Category.STATIC),
    QUEST_GIVER_NPC      (Category.STATIC),
    QUEST_TURN_IN_NPC    (Category.STATIC),
    SHOP                 (Category.STATIC),
    BLACKSMITH           (Category.STATIC),
    TEMPLE               (Category.STATIC),
    SHRINE               (Category.STATIC),
    TOWN_CENTER          (Category.STATIC),
    VILLAGE              (Category.STATIC),
    DUNGEON_ENTRANCE     (Category.STATIC),
    CASTLE               (Category.STATIC),
    GUILD_HALL           (Category.STATIC),
    LIBRARY              (Category.STATIC),
    ARENA                (Category.STATIC),
    PRISON               (Category.STATIC),
    GRAVEYARD            (Category.STATIC),

    // ── Random (procedurally placed or chance-spawned) ────────────────────────
    RANDOM_COMBAT_ZONE       (Category.RANDOM),
    RANDOM_QUEST_OBJECTIVE   (Category.RANDOM),
    RANDOM_TREASURE_ROOM     (Category.RANDOM),
    RANDOM_TRAP_ROOM         (Category.RANDOM),
    RANDOM_SHRINE            (Category.RANDOM),
    RANDOM_WANDERING_NPC     (Category.RANDOM),
    RANDOM_AMBUSH_POINT      (Category.RANDOM),
    RANDOM_CAMPSITE          (Category.RANDOM),
    RANDOM_RUIN              (Category.RANDOM),
    RANDOM_SECRET_AREA       (Category.RANDOM),
    RANDOM_HAZARD_ZONE       (Category.RANDOM),
    RANDOM_MERCHANT          (Category.RANDOM),
    RANDOM_DUNGEON_ROOM      (Category.RANDOM),
    RANDOM_BOSS_ROOM         (Category.RANDOM),
    RANDOM_LORE_LOCATION     (Category.RANDOM),

    // ── Unknown / Unclassified ────────────────────────────────────────────────
    UNKNOWN (Category.NONE);

    // ── Category inner enum ───────────────────────────────────────────────────
    public enum Category {
        STATIC,
        RANDOM,
        NONE
    }

    private final Category category;

    LocationType(Category category) {
        this.category = category;
    }

    /** Returns the category (STATIC, RANDOM, or NONE). */
    public Category getCategory() {
        return category;
    }

    /** Returns true if this is a fixed map location. */
    public boolean isStatic() {
        return category == Category.STATIC;
    }

    /** Returns true if this is a procedurally placed / chance-spawned location. */
    public boolean isRandom() {
        return category == Category.RANDOM;
    }

    /** Returns true if the location type has not been classified. */
    public boolean isUnknown() {
        return category == Category.NONE;
    }
}
