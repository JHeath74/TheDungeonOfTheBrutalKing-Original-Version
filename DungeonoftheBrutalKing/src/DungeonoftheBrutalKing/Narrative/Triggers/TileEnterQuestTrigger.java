
// src/DungeonoftheBrutalKing/Quests/triggers/TileEnterQuestTrigger.java
package DungeonoftheBrutalKing.Quests.Triggers;
import DungeonoftheBrutalKing.Quests.Core.QuestManager;
import java.util.Objects;
public final class TileEnterQuestTrigger implements TriggerManager.Trigger {
private final int targetX;
private final int targetY;
private final String questId;
private final QuestManager questManager;
private final boolean oneShot;

public TileEnterQuestTrigger(int targetX, int targetY, String questId,
                              QuestManager questManager, boolean oneShot) {
    this.targetX = targetX;
    this.targetY = targetY;
    this.questId = Objects.requireNonNull(questId);
    this.questManager = Objects.requireNonNull(questManager);
    this.oneShot = oneShot;
}

@Override
public boolean tryFire(TriggerManager.GameContext ctx) {
    if (ctx.playerX == targetX && ctx.playerY == targetY) {
        questManager.startQuest(questId);
        return oneShot;
    }
    return false;
}

}