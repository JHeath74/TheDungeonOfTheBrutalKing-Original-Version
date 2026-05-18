
// src/DungeonoftheBrutalKing/Narrative/api/Quest.java
package DungeonoftheBrutalKing.Narrative.Api;

import java.io.IOException;
import java.text.ParseException;

public interface Quest {
    String getId();
    String getName();
    String getDescription();
    QuestType getType();
    boolean isCompleted();
    String serialize();
    void completeQuest() throws IOException, InterruptedException, ParseException;
	String getTitle();
	QuestStatus getStatus();
	void start();
	void complete() throws IOException, InterruptedException, ParseException;
	void fail();
	boolean isActive();
}
