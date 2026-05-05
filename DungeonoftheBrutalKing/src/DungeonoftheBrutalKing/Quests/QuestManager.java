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
import java.util.List;

public class QuestManager {
    private final List<Quest> standardQuests;
    private final List<Quest> guildQuests;
    private final List<Quest> mainQuestChain;

    private final List<Quest> activeQuests;
    private final Character character;

    public QuestManager(Character character) throws IOException, InterruptedException, ParseException {
        this.standardQuests = new ArrayList<>();
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
            standardQuests.add(quest);
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

    public List<Quest> getAvailableQuests() {
        return getStandardQuests();
    }

    public List<Quest> getStandardQuests() {
        return new ArrayList<>(standardQuests);
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
        return standardQuests;
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
            // Keep quest flow running even if UI calls fail.
        }
    }

    @FunctionalInterface
    private interface UiAction {
        void run() throws IOException, InterruptedException, ParseException;
    }
}
