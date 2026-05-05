
package DungeonoftheBrutalKing.Quests;

import java.io.IOException;
import java.text.ParseException;

/**
 * Represents a quest in the game.
 */
public interface Quest {
    /**
     * @return the name of the quest
     */
    String getName();

    /**
     * @return the description of the quest
     */
    String getDescription();

    /**
     * @return true if the quest is completed, false otherwise
     */
    boolean isCompleted();

    /**
     * Marks the quest as completed and performs any completion logic.
     * @throws IOException if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     * @throws ParseException if a parsing error occurs
     */
    void completeQuest() throws IOException, InterruptedException, ParseException;

    /**
     * @return a serialized representation of the quest state
     */
    String serialize();

    /**
     * @return the type/category of the quest
     */
    QuestType getType();
}
