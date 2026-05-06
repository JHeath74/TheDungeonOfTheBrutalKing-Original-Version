
// File: `src/DungeonoftheBrutalKing/Quests/Triggers/TileEnterQuestTrigger.java`
package DungeonoftheBrutalKing.Quests;

import DungeonoftheBrutalKing.Quests.QuestTriggerManager;

import java.util.Objects;

/**
 * A one\-\-shot quest trigger that fires when the player enters a specific tile coordinate.
 *
 * <p>Semantics:</p>
 * \- Fires at most once (after firing, it stays "consumed").  
 * \- Matches only when {@code ctx.playerX == x} and {@code ctx.playerY == y}.  
 * \- Invokes the provided {@link Runnable} callback on the first matching entry.  
 */
public final class TileEnterQuestTrigger implements QuestTriggerManager.QuestTrigger {

    /** Target tile X coordinate. */
    private final int x;

    /** Target tile Y coordinate. */
    private final int y;

    /** Action to run exactly once when the player first enters the target tile. */
    private final Runnable onEnter;

    /** Whether this trigger has already fired (consumed). */
    private boolean fired;

    /**
     * Creates a trigger that runs {@code onEnter} the first time the player enters {@code (x, y)}.
     *
     * @param x target tile X coordinate
     * @param y target tile Y coordinate
     * @param onEnter callback executed when the trigger fires (must not be {@code null})
     */
    public TileEnterQuestTrigger(int x, int y, Runnable onEnter) {
        this.x = x;
        this.y = y;
        this.onEnter = Objects.requireNonNull(onEnter, "onEnter");
    }

    /**
     * Attempts to fire the trigger based on the current game context.
     *
     * @param ctx current game context; if {@code null}, the trigger will not fire
     * @return {@code true} if the trigger is consumed (already fired or fired now), otherwise {@code false}
     */
    @Override
    public boolean tryFire(QuestTriggerManager.GameContext ctx) {
        // Already fired: keep reporting "consumed" so the manager can remove/ignore it.
        if (fired) return true;

        // No context: cannot evaluate position.
        if (ctx == null) return false;

        // Only fire on an exact coordinate match.
        if (ctx.playerX != x || ctx.playerY != y) return false;

        // Mark consumed before running side effects, so it cannot re\-fire if re\-entered quickly.
        fired = true;

        // Execute trigger action.
        onEnter.run();

        // Return true to indicate the trigger should be consumed.
        return true;
    }
}
