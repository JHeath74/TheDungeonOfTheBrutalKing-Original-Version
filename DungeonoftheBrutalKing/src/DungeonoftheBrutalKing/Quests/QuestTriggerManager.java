
// File: `src/DungeonoftheBrutalKing/Quests/QuestTriggerManager.java`
package DungeonoftheBrutalKing.Quests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class QuestTriggerManager {

    public interface QuestTrigger {
        /** Return true to consume/remove this trigger after it fires once. */
        boolean tryFire(GameContext ctx) throws Exception;
    }

    /** Minimal context you can expand as needed (player position, flags, etc.). */
    public static final class GameContext {
        public final int playerX;
        public final int playerY;

        public GameContext(int playerX, int playerY) {
            this.playerX = playerX;
            this.playerY = playerY;
        }
    }

    private final List<QuestTrigger> triggers = new ArrayList<>();

    public void add(QuestTrigger trigger) {
        triggers.add(trigger);
    }

    /** Call from your main game loop/tick after movement updates. */
    public void update(GameContext ctx) {
        for (Iterator<QuestTrigger> it = triggers.iterator(); it.hasNext(); ) {
            QuestTrigger t = it.next();
            try {
                boolean consume = t.tryFire(ctx);
                if (consume) it.remove();
            } catch (Exception e) {
                // Don’t break the game loop on a trigger failure.
                e.printStackTrace();
                it.remove();
            }
        }
    }
}
