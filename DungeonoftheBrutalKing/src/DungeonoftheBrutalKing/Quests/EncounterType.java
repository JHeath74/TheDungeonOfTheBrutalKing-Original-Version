
// File: `src/DungeonoftheBrutalKing/Quests/EncounterType.java`
package DungeonoftheBrutalKing.Quests;

/**
 * High-level categories of game events that can trigger or progress quests.
 *
 * Put specifics (npcId, itemId, locationId, dialogChoiceId, uiActionId, flagId, etc.)
 * into {@link EncounterEvent#getKey()} and extra details into {@link EncounterEvent#getData()}.
 */
public enum EncounterType {
    // NPC / character interactions
    NPC_INTERACT,
    CONVERSATION,
    DIALOG_CHOICE,

    // UI-driven triggers (quest board, journal action, button click)
    UI_ACTION,

    // Exploration / world events
    LOCATION_ENTER,
    LOCATION_EVENT,

    // Inventory / progression
    INVENTORY_GAINED,
    INVENTORY_LOST,

    // Story progression flags
    STORY_FLAG_SET,
    STORY_FLAG_CLEARED,

    // Random encounter hooks (player stumbles into a situation)
    RANDOM_ENCOUNTER,

    // Procedural generation hooks (system-generated missions)
    PROCEDURAL_QUEST_GENERATED,
    FACTION_NEED_GENERATED,

    // Chance-based trigger hooks (after X hours / Y actions / RNG roll)
    CHANCE_TRIGGER_FIRED,

    // Hidden triggers (obscure actions / secret conditions)
    HIDDEN_TRIGGER_ACTION,
    HIDDEN_TRIGGER_CONDITION_MET,

    // Sequence-based triggers (ordered steps, puzzles, runes)
    SEQUENCE_STEP_COMPLETED,
    SEQUENCE_COMPLETED,
    SEQUENCE_FAILED,

    // Meta / fourth-wall triggers
    ACHIEVEMENT_UNLOCKED,

    // Player behavior tracking (thresholds, patterns)
    BEHAVIOR_THRESHOLD_REACHED,
    BEHAVIOR_PATTERN_DETECTED, 
    
    STATIC_PERSON
}
