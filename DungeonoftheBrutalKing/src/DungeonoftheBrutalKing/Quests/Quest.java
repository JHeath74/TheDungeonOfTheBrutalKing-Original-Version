package DungeonoftheBrutalKing.Quests;

import java.io.IOException;
import java.text.ParseException;

public interface Quest {
    String getName();
    String getDescription();
    boolean isCompleted();
    void completeQuest() throws IOException, InterruptedException, ParseException;
    String serialize();
    QuestType getType();
}