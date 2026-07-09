
package DungeonoftheBrutalKing.Narrative.Core;

import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.Narrative.Encounters.EncounterEvent;
import DungeonoftheBrutalKing.Narrative.Encounters.EncounterType;

import java.util.Objects;

public final class QuestHooks {

    private final QuestManager questManager;

    public QuestHooks(QuestManager questManager) {
        this.questManager = Objects.requireNonNull(questManager);
    }

    public void onNpcInteract(String npcId) {
        questManager.onEncounter(EncounterEvent.of(EncounterType.NPC_INTERACT, npcId));
    }

    public void onLocationEnter(String locationId) {
        questManager.onEncounter(EncounterEvent.of(EncounterType.LOCATION_ENTER, locationId));
    }

    public void onItemGained(String itemId) {
        questManager.onEncounter(EncounterEvent.of(EncounterType.INVENTORY_GAINED, itemId));
    }

    public void onPuzzleSolved(String puzzleId) {
        questManager.onEncounter(EncounterEvent.of(EncounterType.PUZZLE_SOLVED, puzzleId));
    }

    public void onStoryFlag(String flag) {
        questManager.setFlag(flag);
        questManager.onEncounter(EncounterEvent.of(EncounterType.STORY_FLAG_SET, flag));
    }
    

public void onUiAction(String action) {
    if (action == null) return;
    switch (action) {
        case "DISPLAY_ACTIVE_QUESTS" -> questManager.displayActiveQuests(msg ->
            MainGameScreen.appendToMessageTextPane(msg + "\n"));
        default -> MainGameScreen.appendToMessageTextPane("Unknown UI action: " + action + "\n");
    }
}

}
