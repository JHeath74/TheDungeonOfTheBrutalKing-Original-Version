
// File: `src/DungeonoftheBrutalKing/Quests/QuestTriggerConfig.java`
package DungeonoftheBrutalKing.Quests.Triggers;

import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.Quests.Api.Quest;
import DungeonoftheBrutalKing.Quests.Content.QuestCleanseCursedShrine;
import DungeonoftheBrutalKing.Quests.Content.QuestFeedHungryBeast;
import DungeonoftheBrutalKing.Quests.Content.QuestForgiveBetrayer;
import DungeonoftheBrutalKing.Quests.Content.QuestGuideTheLostSoul;
import DungeonoftheBrutalKing.Quests.Content.QuestLieToTheLost;
import DungeonoftheBrutalKing.Quests.Content.QuestSlayTheHelpLess;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Random;

public final class QuestTriggerConfig {

    private static final Random RNG = new Random();

    // Index 0 == level 1, index 9 == level 10.
    // Each level can define up to 10 (x,y) trigger points.
    private static final int[][][] TRIGGER_POINTS_BY_LEVEL = {
            // Level 1
            {
                    {10, 5}, {3, 7}, {14, 2}, {6, 12}, {8, 9},
                    {1, 1}, {20, 4}, {11, 11}, {5, 16}, {9, 3}
            },
            // Level 2
            {
                    {2, 2}
            },
            // Level 3
            {
                    {4, 8}
            },
            // Level 4
            {
                    {7, 1}
            },
            // Level 5
            {
                    {12, 6}
            },
            // Level 6
            {
                    {9, 14}
            },
            // Level 7
            {
                    {15, 3}
            },
            // Level 8
            {
                    {5, 10}
            },
            // Level 9
            {
                    {18, 7}
            },
            // Level 10
            {
                    {1, 15}
            }
    };

    private QuestTriggerConfig() {}

    public static void register(TriggerManager questTriggerManager, int level) {
        if (level < 1 || level > TRIGGER_POINTS_BY_LEVEL.length) {
            throw new IllegalArgumentException("Invalid level: " + level);
        }

        int[][] points = TRIGGER_POINTS_BY_LEVEL[level - 1];
        for (int[] p : points) {
            if (p == null || p.length != 2) continue;
            addRandomQuestTrigger(questTriggerManager, p[0], p[1]);
        }
    }

    private static void addRandomQuestTrigger(TriggerManager questTriggerManager, int x, int y) {
        MainGameScreen screen = getScreenUnchecked();

        List<RandomQuestOnTileEnterTrigger.ThrowingSupplier<? extends Quest>> questFactories = List.of(
                () -> new QuestCleanseCursedShrine(screen),
                () -> new QuestFeedHungryBeast(screen),
                () -> new QuestForgiveBetrayer(screen),
                () -> new QuestGuideTheLostSoul(screen),
                () -> new QuestLieToTheLost(screen),
                () -> new QuestSlayTheHelpLess(screen)
        );

        questTriggerManager.add(new RandomQuestOnTileEnterTrigger(x, y, questFactories, RNG));
    }

    private static MainGameScreen getScreenUnchecked() {
        try {
            return MainGameScreen.getInstance();
        } catch (ParseException | IOException | InterruptedException e) {
            Thread.currentThread().interrupt(); // preserves interrupt status when applicable
            throw new RuntimeException("Failed to get MainGameScreen instance", e);
        }
    }
}
