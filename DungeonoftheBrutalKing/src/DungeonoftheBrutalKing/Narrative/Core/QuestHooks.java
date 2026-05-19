
// File: `src/DungeonoftheBrutalKing/Quests/QuestHooks.java`
package DungeonoftheBrutalKing.Narrative.Core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import DungeonoftheBrutalKing.Quests.Encounters.EncounterEvent;
import DungeonoftheBrutalKing.Narrative.*;

public final class QuestHooks {
    private final QuestManager questManager;

    public QuestHooks(QuestManager questManager) {
        this.questManager = Objects.requireNonNull(questManager, "questManager");
    }

    public void onNpcInteract(String npcId) {
        if (isBlank(npcId)) return;
        questManager.onEncounter(EncounterEvent.of(EncounterType.NPC_INTERACT, npcId));
    }

    public void onDialogChoice(String choiceId, String npcId) {
        if (isBlank(choiceId)) return;

        Map<String, Object> data = null;
        if (!isBlank(npcId)) {
            data = new HashMap<>();
            data.put("npcId", npcId);
        }

        questManager.onEncounter(EncounterEvent.of(EncounterType.DIALOG_CHOICE, choiceId, data));
    }

    public void onLocationEnter(String locationId) {
        if (isBlank(locationId)) return;
        questManager.onEncounter(EncounterEvent.of(EncounterType.LOCATION_ENTER, locationId));
    }

    public void onInventoryGained(String itemId, int amount) {
        if (isBlank(itemId) || amount == 0) return;

        Map<String, Object> data = new HashMap<>();
        data.put("amount", amount);

        questManager.onEncounter(EncounterEvent.of(EncounterType.INVENTORY_GAINED, itemId, data));
    }

    public void onStoryFlagSet(String flagId) {
        if (isBlank(flagId)) return;
        questManager.onEncounter(EncounterEvent.of(EncounterType.STORY_FLAG_SET, flagId));
    }

    public void onUiAction(String actionId) {
        if (isBlank(actionId)) return;
        questManager.onEncounter(EncounterEvent.of(EncounterType.UI_ACTION, actionId));
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
