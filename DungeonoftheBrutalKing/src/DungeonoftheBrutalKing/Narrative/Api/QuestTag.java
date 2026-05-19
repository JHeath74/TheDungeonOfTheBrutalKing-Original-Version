
// File: `src/DungeonoftheBrutalKing/Quests/QuestTag.java`
package DungeonoftheBrutalKing.Narrative.Api;

/**
 * Optional granular tags for filtering/grouping (not used for pooling).
 *
 * Keep these orthogonal to {@link QuestType}; tags are for UI filters/search,
 * analytics, and quest board grouping.
 */
public enum QuestTag {
    // Existing
    RESCUE,
    COMBAT,
    ESCORT,
    DELIVERY,
    PUZZLE,
    DISCOVERY,
    NEGOTIATION,

    // Hidden / obscure triggers (campfire sits, instruments, etc.)
    HIDDEN,
    OBSCURE_ACTION,

    // Sequence / ordered triggers (runes in order, multi-step puzzles)
    SEQUENCE,
    RIDDLE,

    // Meta / fourth-wall triggers
    ACHIEVEMENT,

    // Player behavior tracking (stealing a lot, violence, etc.)
    BEHAVIOR,
    CRIME,

    // Procedural / system-generated hooks
    PROCEDURAL,
    FACTION,

    // RNG / probability based hooks
    CHANCE,

    // Narrative / world-state
    STORY,
    WORLD_EVENT
}
