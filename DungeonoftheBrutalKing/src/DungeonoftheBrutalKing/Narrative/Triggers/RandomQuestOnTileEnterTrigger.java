
package DungeonoftheBrutalKing.Narrative.Triggers;

import DungeonoftheBrutalKing.Narrative.Api.Quest;

import java.util.List;
import java.util.Random;

public class RandomQuestOnTileEnterTrigger implements TriggerManager.Trigger {

    @FunctionalInterface
    public interface ThrowingSupplier<T> {
        T get() throws Exception;
    }

    private final int x;
    private final int y;
    private final List<ThrowingSupplier<? extends Quest>> questFactories;
    private final Random rng;

    public RandomQuestOnTileEnterTrigger(int x, int y,
            List<ThrowingSupplier<? extends Quest>> questFactories,
            Random rng) {
        this.x = x;
        this.y = y;
        this.questFactories = questFactories;
        this.rng = rng;
    }

    @Override
    public boolean tryFire(TriggerManager.GameContext ctx) {
        if (ctx.playerX == x && ctx.playerY == y && !questFactories.isEmpty()) {
            ThrowingSupplier<? extends Quest> factory =
                questFactories.get(rng.nextInt(questFactories.size()));
            try {
                Quest quest = factory.get();
                quest.start();
            } catch (Exception e) {
                throw new RuntimeException("Failed to start random quest at (" + x + "," + y + ")", e);
            }
            return true;
        }
        return false;
    }

    /**
     * Convenience method — delegates to {@link #tryFire} so this trigger
     * can also be called directly without a full {@link TriggerManager}.
     */
    public void onTileEnter(int tileX, int tileY) {
        tryFire(new TriggerManager.GameContext(tileX, tileY));
    }
}
