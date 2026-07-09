
package DungeonoftheBrutalKing.Narrative.Api;

import DungeonoftheBrutalKing.Narrative.Encounters.EncounterEvent;
import java.io.IOException;
import java.text.ParseException;

public interface Quest {
    String getId();
    String getTitle();
    String getDescription();
    QuestStatus getStatus();

    void start();
    void complete() throws IOException, InterruptedException, ParseException;
    void fail();

    default boolean isActive() { return getStatus() == QuestStatus.ACTIVE; }
    default boolean isComplete() { return getStatus() == QuestStatus.COMPLETED; }
    default boolean isFailed() { return getStatus() == QuestStatus.FAILED; }

    
    
    void onEncounter(EncounterEvent event);
	String serialize();
	QuestType getType();
	boolean isCompleted();
	void completeQuest() throws IOException, InterruptedException, ParseException;
	String getName();
}
