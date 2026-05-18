
// File: `src/DungeonoftheBrutalKing/Quests/RandomQuestOnTileEnterTrigger.java`
package DungeonoftheBrutalKing.Quests.Triggers;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import DungeonoftheBrutalKing.Quests.Api.Quest;

/**
 * A one-shot quest trigger that fires when the player enters a specific tile coordinate,
 * selecting a quest factory at random and invoking it.
 *
 * <p>Semantics:</p>
 * \- Fires at most once; after firing it remains "consumed".  
 * \- Matches only when {@code ctx.playerX == x} and {@code ctx.playerY == y}.  
 * \- Randomly selects one quest factory from {@code questFactories} and calls it.  
 * \- Wraps any checked/unchecked exception thrown by the selected factory in a {@link RuntimeException}.  
 */
public final class RandomQuestOnTileEnterTrigger implements TriggerManager.QuestTrigger {

    /**
     * Like {@link java.util.function.Supplier} but allows checked exceptions.
     *
     * @param <T> supplied value type
     */
    @FunctionalInterface
    public interface ThrowingSupplier<T> {
        /**
         * Creates/returns a value.
         *
         * @return created value
         * @throws Exception if creation fails for any reason
         */
        T get() throws Exception;
    }

    /** Target tile X coordinate. */
    private final int x;

    /** Target tile Y coordinate. */
    private final int y;

    /**
     * Factories used to create quests when this trigger fires.
     *
     * <p>Must be non-empty. Individual entries may be {@code null} (they will be ignored).</p>
     */
    private final List<ThrowingSupplier<? extends Quest>> questFactories;

    /** Source of randomness for selecting a quest factory. */
    private final Random rng;

    /** Whether this trigger has already fired (consumed). */
    private boolean fired;

    /**
     * Creates a trigger that, upon first entry into {@code (x, y)}, selects and runs a random quest factory.
     *
     * @param x target tile X coordinate
     * @param y target tile Y coordinate
     * @param questFactories non-empty list of quest factories (copied defensively)
     * @param rng random generator used for selection
     * @throws NullPointerException if {@code questFactories} or {@code rng} is {@code null}
     * @throws IllegalArgumentException if {@code questFactories} is empty
     */
    public RandomQuestOnTileEnterTrigger(
            int x,
            int y,
            List<ThrowingSupplier<? extends Quest>> questFactories,
            Random rng
    ) {
        this.x = x;
        this.y = y;
        this.questFactories = List.copyOf(Objects.requireNonNull(questFactories, "questFactories"));
        this.rng = Objects.requireNonNull(rng, "rng");
        if (this.questFactories.isEmpty()) {
            throw new IllegalArgumentException("questFactories must not be empty");
        }
    }

    /**
     * Attempts to fire the trigger based on the current game context.
     *
     * @param ctx current game context; if {@code null}, the trigger will not fire
     * @return {@code true} if the trigger is consumed (already fired or fired now), otherwise {@code false}
     * @throws RuntimeException if the selected quest factory throws an exception while creating a quest
     */
    @Override
    public boolean tryFire(TriggerManager.GameContext ctx) {
        // Already fired: keep reporting "consumed" so the manager can remove/ignore it.
        if (fired) return true;

        // No context: cannot evaluate position.
        if (ctx == null) return false;

        // Only fire on an exact coordinate match.
        if (ctx.playerX != x || ctx.playerY != y) return false;

        // Mark consumed before running side effects, so it cannot re-fire if re-entered quickly.
        fired = true;

        // Select and invoke one factory at random.
        int idx = rng.nextInt(questFactories.size());
        ThrowingSupplier<? extends Quest> factory = questFactories.get(idx);
        if (factory != null) {
            try {
                // Quest constructors may throw checked exceptions; invoke here and wrap failures.
                factory.get();
            } catch (Exception e) {
                throw new RuntimeException("Failed to create quest for trigger at (" + x + "," + y + ")", e);
            }
        }

        return true;
    }
}
