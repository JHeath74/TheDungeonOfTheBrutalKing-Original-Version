
// File: `src/DungeonoftheBrutalKing/Quests/QuestManager.java`
package DungeonoftheBrutalKing.Quests;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.Quests.Quests.QuestCleanseCursedShrine;
import DungeonoftheBrutalKing.Quests.Quests.QuestFeedHungryBeast;
import DungeonoftheBrutalKing.Quests.Quests.QuestForgiveBetrayer;
import DungeonoftheBrutalKing.Quests.Quests.QuestGuideTheLostSoul;
import DungeonoftheBrutalKing.Quests.Quests.QuestLieToTheLost;
import DungeonoftheBrutalKing.Quests.Quests.QuestRescuetheForgottenPrisoner;
import DungeonoftheBrutalKing.Quests.Quests.QuestSlayTheHelpLess;
import DungeonoftheBrutalKing.SharedData.RandomFactory;

import javax.swing.JPanel;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class QuestManager {
    private final List<Quest> sideQuests;
    private final List<Quest> guildQuests;
    private final List<Quest> mainQuestChain;

    private final List<Quest> activeQuests;
    private final Character character;

    public QuestManager(Character character) throws IOException, InterruptedException, ParseException {
        this.sideQuests = new ArrayList<>();
        this.guildQuests = new ArrayList<>();
        this.mainQuestChain = new ArrayList<>();

        this.character = character;
        this.activeQuests = character != null ? character.getActiveQuests() : new ArrayList<>();

        initializeQuests();
    }

    private void initializeQuests() throws IOException, InterruptedException, ParseException {
        MainGameScreen screen = MainGameScreen.getInstance();

        addToPool(new QuestRescuetheForgottenPrisoner(screen));
        addToPool(new QuestForgiveBetrayer(screen));
        addToPool(new QuestCleanseCursedShrine(screen));
        addToPool(new QuestFeedHungryBeast(screen));
        addToPool(new QuestGuideTheLostSoul(screen));
        addToPool(new QuestLieToTheLost(screen));
        addToPool(new QuestSlayTheHelpLess(screen));
    }

    private void addToPool(Quest quest) {
        if (quest == null) return;

        QuestType type;
        try {
            type = quest.getType();
        } catch (RuntimeException ignored) {
            type = null;
        }

        if (type == QuestType.MAIN) {
            mainQuestChain.add(quest);
        } else if (type == QuestType.GUILD) {
            guildQuests.add(quest);
        } else {
            sideQuests.add(quest);
        }
    }

    public void addActiveQuest(Quest quest) {
        if (quest == null || character == null) return;

        if (!activeQuests.contains(quest)) {
            activeQuests.add(quest);
        }

        if (!character.getActiveQuests().contains(quest)) {
            character.addActiveQuest(quest);
        }
    }

    public boolean removeActiveQuest(Quest quest) {
        if (quest == null || character == null) return false;

        boolean removed = activeQuests.remove(quest);
        if (removed) {
            character.removeActiveQuest(quest);
        }
        return removed;
    }

    public List<Quest> getActiveQuests() {
        return new ArrayList<>(activeQuests);
    }

    /**
     * Returns available quests across all pools (side, guild, main), excluding
     * completed and already-active quests.
     */
    public List<Quest> getAvailableQuests() {
        return getAvailableQuests(null);
    }

    /**
     * Returns available quests for the given type.
     * If type is null, returns across all pools.
     */
    public List<Quest> getAvailableQuests(QuestType type) {
        Set<Quest> candidates = new LinkedHashSet<>();

        if (type == null) {
            candidates.addAll(sideQuests);
            candidates.addAll(guildQuests);
            candidates.addAll(mainQuestChain);
        } else {
            candidates.addAll(poolFor(type));
        }

        List<Quest> available = new ArrayList<>();
        for (Quest q : candidates) {
            if (q == null) continue;
            if (q.isCompleted()) continue;
            if (activeQuests.contains(q)) continue;
            available.add(q);
        }
        return available;
    }

    public List<Quest> getSideQuests() {
        return new ArrayList<>(sideQuests);
    }

    public List<Quest> getGuildQuests() {
        return new ArrayList<>(guildQuests);
    }

    public List<Quest> getMainQuestChain() {
        return new ArrayList<>(mainQuestChain);
    }

    public Quest getRandomQuest() {
        return getRandomQuest(null);
    }

    public Quest getRandomQuest(QuestType type) {
        List<Quest> pool = poolFor(type);
        if (pool.isEmpty()) return null;
        return pool.get(RandomFactory.gameplayInt(pool.size()));
    }

    private List<Quest> poolFor(QuestType type) {
        if (type == QuestType.MAIN) return mainQuestChain;
        if (type == QuestType.GUILD) return guildQuests;
        return sideQuests;
    }

    /**
     * Routes a game event to quests.
     *
     * This calls `Quest.onEncounter(...)` on both pooled and active quests and
     * optionally adds quests to active when an event changes them.
     */
    public void onEncounter(EncounterEvent event) {
        if (event == null) return;

        Set<Quest> targets = new LinkedHashSet<>();
        targets.addAll(sideQuests);
        targets.addAll(guildQuests);
        targets.addAll(mainQuestChain);
        targets.addAll(activeQuests);

        for (Quest quest : targets) {
            if (quest == null) continue;

            boolean changed;
            try {
                changed = quest.onEncounter(event, this);
            } catch (RuntimeException ignored) {
                changed = false;
            }

            if (changed && character != null && !quest.isCompleted()) {
                addActiveQuest(quest);
            }
        }
    }

    public void displayActiveQuests() {
        for (Quest quest : activeQuests) {
            if (quest == null) continue;
            System.out.println("Quest: " + quest.getName());
            System.out.println("Description: " + quest.getDescription());
        }
    }

    public void displayQuestDetails(Quest quest, MainGameScreen mainGameScreen) {
        if (mainGameScreen == null || quest == null) return;

        uiSafely(() -> MainGameScreen.appendToMessageTextPane(
                "New Quest: " + quest.getName() + "\n" + quest.getDescription() + "\n"
        ));

        if (quest instanceof JPanel panel) {
            uiSafely(() -> MainGameScreen.replaceWithAnyPanel(panel));
            return;
        }

        uiSafely(() -> MainGameScreen.replaceWithAnyPanel(new JPanel()));
    }

    private void uiSafely(UiAction action) {
        try {
            action.run();
        } catch (IOException | InterruptedException | ParseException | RuntimeException ignored) {
            // keep quest flow running
        }
    }

    @FunctionalInterface
    private interface UiAction {
        void run() throws IOException, InterruptedException, ParseException;
    }
}
