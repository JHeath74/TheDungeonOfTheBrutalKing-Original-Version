package DungeonoftheBrutalKing.Quests;

public interface Quest {
    String getName();
    String getDescription();
    boolean isCompleted();
    void completeQuest();
    String serialize();
    QuestType getType();
}