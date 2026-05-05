package DungeonoftheBrutalKing.Quests;

/**
 * Represents the type of encounter that can occur in a quest.
 */
public enum EncounterType {
    /** A conversation-based encounter. */
    CONVERSATION,
    /** An encounter with a randomly generated person. */
    RANDOM_PERSON,
    /** An encounter with a specific, static person. */
    STATIC_PERSON,
    /** An encounter with a randomly generated item. */
    RANDOM_ITEM,
    /** An encounter with a specific, static item. */
    STATIC_ITEM
}
