package Quests;

public enum QuestType {
    // High-level quest categories
    STANDARD,   // regular/side quests
    GUILD,      // guild-related quests
    MAIN,       // main story / campaign quests

    // Existing granular types (can still be used for quest design or filtering)
    RESCUE,
    COMBAT,
    ESCORT,
    DELIVERY,
    PUZZLE,
    DISCOVERY,
    NEGOTIATION
}