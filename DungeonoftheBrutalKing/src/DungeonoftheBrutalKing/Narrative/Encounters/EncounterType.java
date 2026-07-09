
package DungeonoftheBrutalKing.Narrative.Encounters;

public enum EncounterType {

    // ── NPC / Character interactions ────────────────────────────────────────
    NPC_INTERACT,
    NPC_HOSTILE,
    NPC_FRIENDLY,
    NPC_MERCHANT,
    NPC_GUARD,
    NPC_ELDER,
    CONVERSATION,
    DIALOG_CHOICE,
    DIALOG_OATH_TAKEN,
    DIALOG_SECRET_REVEALED,
    STATIC_PERSON,

    // ── UI-driven triggers ──────────────────────────────────────────────────
    UI_ACTION,

    // ── Static Location Events (fixed points on the map) ────────────────────
    STATIC_LOCATION_ENTER,
    STATIC_LOCATION_EXIT,
    INN_ENTERED,
    INN_EXITED,
    INN_RESTED,
    INN_PAID,
    INN_RUMOR_HEARD,
    INN_BRAWL_STARTED,
    QUEST_BOARD_VIEWED,
    QUEST_ACCEPTED_FROM_NPC,
    QUEST_GIVER_NPC_INTERACTED,
    QUEST_TURN_IN_NPC_INTERACTED,
    SHOP_ENTERED,
    SHOP_EXITED,
    SHOP_PURCHASE,
    SHOP_SOLD,
    SHOP_ITEM_IDENTIFIED,
    BLACKSMITH_ENTERED,
    BLACKSMITH_EXITED,
    BLACKSMITH_CRAFTED,
    BLACKSMITH_REPAIRED,
    BLACKSMITH_UPGRADED,
    TEMPLE_ENTERED,
    TEMPLE_EXITED,
    TEMPLE_PRAYED,
    TEMPLE_HEALED,
    TEMPLE_BLESSED,
    TEMPLE_CURSE_REMOVED,
    TEMPLE_DONATED,
    TOWN_ENTERED,
    TOWN_EXITED,
    TOWN_EVENT_TRIGGERED,
    DUNGEON_ENTRANCE_FOUND,
    DUNGEON_ENTERED,
    DUNGEON_EXITED,

    // ── Random Location Events (spawned / procedural tiles) ─────────────────
    RANDOM_LOCATION_ENTER,
    RANDOM_ENCOUNTER,
    RANDOM_COMBAT_TRIGGERED,
    RANDOM_QUEST_LOCATION_FOUND,
    RANDOM_TREASURE_FOUND,
    RANDOM_TRAP_TRIGGERED,
    RANDOM_TRAP_DISARMED,
    RANDOM_AMBUSH,
    RANDOM_NPC_ENCOUNTERED,
    RANDOM_SHRINE_FOUND,
    RANDOM_CAMPSITE_FOUND,
    RANDOM_RUIN_DISCOVERED,
    RANDOM_SECRET_DOOR_FOUND,
    RANDOM_HAZARD_ENCOUNTERED,

    // ── Exploration / World events ──────────────────────────────────────────
    LOCATION_ENTER,
    LOCATION_EXIT,
    LOCATION_EVENT,
    LOCATION_DISCOVERED,
    MAP_REVEALED,
    SECRET_AREA_DISCOVERED,

    // ── Combat ──────────────────────────────────────────────────────────────
    COMBAT_START,
    COMBAT_END,
    COMBAT_VICTORY,
    COMBAT_DEFEAT,
    COMBAT_FLED,
    COMBAT_ROUND_START,
    COMBAT_ROUND_END,
    COMBAT_CRITICAL_HIT,
    COMBAT_KILLING_BLOW,
    ENEMY_DIED,
    BOSS_COMBAT_START,
    BOSS_COMBAT_VICTORY,
    BOSS_COMBAT_DEFEAT,

    // ── Quest objective locations ────────────────────────────────────────────
    QUEST_OBJECTIVE_LOCATION_REACHED,
    QUEST_OBJECTIVE_COMPLETED,
    QUEST_TURN_IN_LOCATION_REACHED,

    // ── Puzzle events ───────────────────────────────────────────────────────
    PUZZLE_START,
    PUZZLE_SOLVED,
    PUZZLE_FAILED,
    PUZZLE_HINT_USED,
    PUZZLE_RESET,

    // ── Inventory / Items ────────────────────────────────────────────────────
    INVENTORY_GAINED,
    INVENTORY_LOST,
    ITEM_USED,
    ITEM_EQUIPPED,
    ITEM_UNEQUIPPED,
    ITEM_DROPPED,
    ITEM_CRAFTED,
    ITEM_REPAIRED,
    ITEM_IDENTIFIED,
    ITEM_DESTROYED,
    TREASURE_LOOTED,
    CHEST_OPENED,

    // ── Character Progression ────────────────────────────────────────────────
    LEVEL_UP,
    EXPERIENCE_GAINED,
    SKILL_LEARNED,
    SKILL_UPGRADED,
    STAT_CHANGED,
    ABILITY_UNLOCKED,
    CLASS_CHANGED,

    // ── Alignment / Morality (key for your moral-choice quests) ─────────────
    ALIGNMENT_SHIFTED_GOOD,
    ALIGNMENT_SHIFTED_EVIL,
    ALIGNMENT_SHIFTED_NEUTRAL,
    KARMA_THRESHOLD_REACHED,
    MORAL_CHOICE_MADE,
    MERCIFUL_ACT,
    CRUEL_ACT,
    HONORABLE_ACT,
    DECEPTIVE_ACT,

    // ── Status Effects ───────────────────────────────────────────────────────
    STATUS_EFFECT_APPLIED,
    STATUS_EFFECT_REMOVED,
    PLAYER_POISONED,
    PLAYER_CURSED,
    PLAYER_BLESSED,
    PLAYER_DISEASED,
    PLAYER_STUNNED,
    PLAYER_FEARED,
    PLAYER_CHARMED,

    // ── Reputation / Faction ─────────────────────────────────────────────────
    REPUTATION_GAINED,
    REPUTATION_LOST,
    REPUTATION_THRESHOLD_REACHED,
    FACTION_STANDING_CHANGED,
    FACTION_ALLIED,
    FACTION_HOSTILE,
    FACTION_NEUTRAL,
    FACTION_QUEST_GIVEN,
    FACTION_NEED_GENERATED,
    FACTION_BETRAYAL,

    // ── Lore / Discovery ─────────────────────────────────────────────────────
    LORE_DISCOVERED,
    JOURNAL_ENTRY_ADDED,
    RUMOR_HEARD,
    SECRET_REVEALED,
    ANCIENT_TEXT_READ,

    // ── Death / Respawn ──────────────────────────────────────────────────────
    PLAYER_DIED,
    PLAYER_RESPAWNED,
    PLAYER_NEAR_DEATH,
    RESURRECTION_USED,

    // ── Quest events ────────────────────────────────────────────────────────
    QUEST_STARTED,
    QUEST_COMPLETED,
    QUEST_FAILED,
    QUEST_UPDATED,
    QUEST_ABANDONED,
    QUEST_STAGE_ADVANCED,

    // ── Story progression flags ─────────────────────────────────────────────
    STORY_FLAG_SET,
    STORY_FLAG_CLEARED,
    STORY_MILESTONE_REACHED,
    CUTSCENE_TRIGGERED,
    FLASHBACK_TRIGGERED,

    // ── Procedural generation ────────────────────────────────────────────────
    PROCEDURAL_QUEST_GENERATED,

    // ── Chance / Hidden / Sequence triggers ─────────────────────────────────
    CHANCE_TRIGGER_FIRED,
    HIDDEN_TRIGGER_ACTION,
    HIDDEN_TRIGGER_CONDITION_MET,
    SEQUENCE_STEP_COMPLETED,
    SEQUENCE_COMPLETED,
    SEQUENCE_FAILED,

    // ── Player behavior tracking ─────────────────────────────────────────────
    BEHAVIOR_THRESHOLD_REACHED,
    BEHAVIOR_PATTERN_DETECTED,

    // ── Meta / Achievement ───────────────────────────────────────────────────
    ACHIEVEMENT_UNLOCKED,
    GAME_OVER,
    GAME_COMPLETED
}
