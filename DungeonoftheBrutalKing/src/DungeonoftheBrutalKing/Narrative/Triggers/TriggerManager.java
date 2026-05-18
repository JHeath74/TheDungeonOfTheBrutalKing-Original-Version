
// src/DungeonoftheBrutalKing/Quests/triggers/TriggerManager.java
package DungeonoftheBrutalKing.Quests.Triggers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class TriggerManager {

    @FunctionalInterface
    public interface Trigger {
        /** Return true to consume/remove this trigger after firing. */
        boolean tryFire(GameContext ctx) throws Exception;
    }

    public static final class GameContext {
        public final int playerX;
        public final int playerY;
        public final String mapId;
        public final java.util.Map<String, Boolean> flags;

        public GameContext(int playerX, int playerY, String mapId,
                           java.util.Map<String, Boolean> flags) {
            this.playerX = playerX;
            this.playerY = playerY;
            this.mapId = mapId;
            this.flags = flags != null ? java.util.Collections.unmodifiableMap(flags)
                                       : java.util.Collections.emptyMap();
        }
    }

    private final List<Trigger> triggers = new ArrayList<>();

    public void add(Trigger trigger) {
        if (trigger != null) triggers.add(trigger);
    }

    public void remove(Trigger trigger) {
        triggers.remove(trigger);
    }

    public void clear() {
        triggers.clear();
    }

    /** Call once per game loop tick after player movement is resolved. */
    public void update(GameContext ctx) {
        for (Iterator<Trigger> it = triggers.iterator(); it.hasNext(); ) {
            Trigger t = it.next();
            try {
                if (t.tryFire(ctx)) it.remove();
            } catch (Exception e) {
                e.printStackTrace();
                it.remove();
            }
        }
    }
}
