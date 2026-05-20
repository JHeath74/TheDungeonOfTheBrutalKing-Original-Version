
package DungeonoftheBrutalKing.Narrative.Triggers;

import DungeonoftheBrutalKing.Narrative.Api.Quest;
import java.util.Objects;

public final class TileEnterQuestTrigger implements TriggerManager.Trigger {

    private final int targetX;
    private final int targetY;
    private final Quest quest;
    private final boolean oneShot;

    public TileEnterQuestTrigger(int targetX, int targetY, Quest quest, boolean oneShot) {
        this.targetX = targetX;
        this.targetY = targetY;
        this.quest = Objects.requireNonNull(quest);
        this.oneShot = oneShot;
    }

    @Override
    public boolean tryFire(TriggerManager.GameContext ctx) {
        if (ctx.playerX == targetX && ctx.playerY == targetY) {
            quest.start();
            return oneShot;
        }
        return false;
    }

    @Override
    public void onTileEnter(int tileX, int tileY) throws Exception {
        if (tileX == targetX && tileY == targetY) {
            quest.start();
        }
    }
}
