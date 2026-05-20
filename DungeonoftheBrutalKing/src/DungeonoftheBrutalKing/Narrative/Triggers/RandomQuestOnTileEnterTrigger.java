
package DungeonoftheBrutalKing.Narrative.Triggers;

import DungeonoftheBrutalKing.Narrative.Api.Quest;
import DungeonoftheBrutalKing.Narrative.Triggers.TriggerManager.GameContext;

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
    public void onTileEnter(int tileX, int tileY) throws Exception {
        if (tileX == x && tileY == y && !questFactories.isEmpty()) {
            ThrowingSupplier<? extends Quest> factory =
                questFactories.get(rng.nextInt(questFactories.size()));
            Quest quest = factory.get();
            quest.start();
        }
    }

	@Override
	public boolean tryFire(GameContext ctx) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
}
