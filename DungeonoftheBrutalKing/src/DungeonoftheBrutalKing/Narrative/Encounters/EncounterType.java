
package DungeonoftheBrutalKing.Narrative.Encounters;

/**
 * High-level categories of game events that can trigger or progress quests,
 * encounters, NPCs, puzzles, and other narrative elements.
 *
 * Put specifics (npcId, itemId, locationId, puzzleId, etc.)
 * into {@link EncounterEvent#getKey()} and extra details into {@link EncounterEvent#getData()}.
 */
public enum EncounterType {

    // ── NPC / Character interactions ────────────────────────────────────────
    NPC_INTERACT,
    NPC_HOSTILE,
    NPC_FRIENDLY,
    NPC_MERCHANT,
    CONVERSATION,
    DIALOG_CHOICE,
    STATIC_PERSON,

    // ── UI-driven triggers ──────────────────────────────────────────────────
    UI_ACTION,

    // ── Exploration / World events ──────────────────────────────────────────
    LOCATION_ENTER,
    LOCATION_EXIT,
    LOCATION_EVENT,
    LOCATION_DISCOVERED,

    // ── Combat ──────────────────────────────────────────────────────────────
    COMBAT_START,
    COMBAT_END,
    COMBAT_VICTORY,
    COMBAT_DEFEAT,
    COMBAT_FLED,

    // ── Puzzle events ───────────────────────────────────────────────────────
    PUZZLE_START,
    PUZZLE_SOLVED,
    PUZZLE_FAILED,
    PUZZLE_HINT_USED,
    PUZZLE_RESET,

    // ── Inventory / Progression ─────────────────────────────────────────────
    INVENTORY_GAINED,
    INVENTORY_LOST,
    ITEM_USED,
    ITEM_EQUIPPED,

    // ── Quest events ────────────────────────────────────────────────────────
    QUEST_STARTED,
    QUEST_COMPLETED,
    QUEST_FAILED,
    QUEST_UPDATED,

    // ── Story progression flags ─────────────────────────────────────────────
    STORY_FLAG_SET,
    STORY_FLAG_CLEARED,

    // ── Random / Procedural encounters ─────────────────────────────────────
    RANDOM_ENCOUNTER,
    PROCEDURAL_QUEST_GENERATED,
    FACTION_NEED_GENERATED,

    // ── Chance-based trigger hooks ──────────────────────────────────────────
    CHANCE_TRIGGER_FIRED,

    // ── Hidden triggers ─────────────────────────────────────────────────────
    HIDDEN_TRIGGER_ACTION,
    HIDDEN_TRIGGER_CONDITION_MET,

    // ── Sequence-based triggers ─────────────────────────────────────────────
    SEQUENCE_STEP_COMPLETED,
    SEQUENCE_COMPLETED,
    SEQUENCE_FAILED,

    // ── Player behavior tracking ─────────────────────────────────────────────
    BEHAVIOR_THRESHOLD_REACHED,
    BEHAVIOR_PATTERN_DETECTED,

    // ── Meta / Achievement ───────────────────────────────────────────────────
    ACHIEVEMENT_UNLOCKED
}
