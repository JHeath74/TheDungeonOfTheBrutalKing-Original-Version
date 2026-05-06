
// File: src/DungeonoftheBrutalKing/Quests/Quest.java
package DungeonoftheBrutalKing.Quests;

import java.io.IOException;
import java.text.ParseException;

/**
 * Represents a quest definition + minimal runtime hooks.
 *
 * Implementations may override onEncounter(...) to update internal progress
 * when the QuestManager dispatches encounter events.
 */
public interface Quest {

    /**
     * @return the unique identifier for this quest (stable key for save/load)
     */
    String getId();

    /**
     * @return the name of the quest
     */
    String getName();

    /**
     * @return the description of the quest
     */
    String getDescription();

    /**
     * @return the type/category of the quest
     */
    QuestType getType();

    /**
     * @return true if the quest is completed, false otherwise
     */
    boolean isCompleted();

    /**
     * Called by the quest system when an encounter/event happens.
     * Return true if this quest changed state/progress because of the event.
     *
     * Default implementation is a no-op for backward compatibility.
     */
    default boolean onEncounter(EncounterEvent event, QuestManager questManager) {
        return false;
    }

    /**
     * Marks the quest as completed and performs any completion logic.
     * Implementations should set their completed state and apply rewards.
     *
     * @throws IOException if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     * @throws ParseException if a parsing error occurs
     */
    void completeQuest() throws IOException, InterruptedException, ParseException;

    /**
     * @return a serialized representation of the quest state
     */
    String serialize();
}
