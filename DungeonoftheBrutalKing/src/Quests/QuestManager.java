// src/Quests/QuestManager.java
package Quests;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;
import Quests.Quests.QuestForgiveBetrayer;
import Quests.Quests.QuestRescuetheForgottenPrisoner;

public class QuestManager {
    // Separated quest collections
    private final List<Quest> standardQuests;
    private final List<Quest> guildQuests;
    private final List<Quest> mainQuestChain;
    private final List<Quest> activeQuests;
    private final Charecter character;

    public QuestManager(Charecter character) throws IOException, InterruptedException, ParseException {
        this.standardQuests = new ArrayList<>();
        this.guildQuests = new ArrayList<>();
        this.mainQuestChain = new ArrayList<>();
        this.activeQuests = character.getActiveQuests();
        this.character = character;
        initializeQuests();
    }

    private void initializeQuests() throws IOException, InterruptedException, ParseException {
        // Create quest instances (templates) and categorize them by type
        Quest q1 = new QuestRescuetheForgottenPrisoner(MainGameScreen.getInstance());
        Quest q2 = new QuestForgiveBetrayer(MainGameScreen.getInstance());

        // Attempt to categorize based on quest type; fall back to standard if unknown
        try {
            if (q1.getType() == QuestType.MAIN) mainQuestChain.add(q1);
            else if (q1.getType() == QuestType.GUILD) guildQuests.add(q1);
            else standardQuests.add(q1);
        } catch (Exception e) {
            standardQuests.add(q1);
        }

        try {
            if (q2.getType() == QuestType.MAIN) mainQuestChain.add(q2);
            else if (q2.getType() == QuestType.GUILD) guildQuests.add(q2);
            else standardQuests.add(q2);
        } catch (Exception e) {
            standardQuests.add(q2);
        }
    }

    public void addActiveQuest(Quest quest) {
        if (!activeQuests.contains(quest)) {
            activeQuests.add(quest);
            if (!character.getActiveQuests().contains(quest)) {
                character.addActiveQuest(quest);
            }
        }
    }

    public boolean removeActiveQuest(Quest quest) {
        boolean removed = activeQuests.remove(quest);
        if (removed) {
            character.removeActiveQuest(quest);
        }
        return removed;
    }

    public void syncActiveQuests() {
        activeQuests.clear();
        activeQuests.addAll(character.getActiveQuests());
        character.getActiveQuests().clear();
        character.getActiveQuests().addAll(activeQuests);
    }

    public List<Quest> getActiveQuests() {
        syncActiveQuests();
        return new ArrayList<>(activeQuests);
    }

    // Backwards-compatible: return standard (ambient) quests as available quests
    public List<Quest> getAvailableQuests() {
        return new ArrayList<>(standardQuests);
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

    /**
     * Return a random quest from the standard or other lists based on type.
     * If type is null or no quests of that type exist, falls back to standard.
     */
    public Quest getRandomQuest() {
        return getRandomQuest(null);
    }

    public Quest getRandomQuest(QuestType type) {
        List<Quest> pool = standardQuests;
        if (type == QuestType.MAIN) pool = mainQuestChain;
        else if (type == QuestType.GUILD) pool = guildQuests;
        if (pool == null || pool.isEmpty()) return null;
        Random rand = new Random();
        return pool.get(rand.nextInt(pool.size()));
    }

    // Removed getQuestsByLocation() since QuestLocationType and getLocationType() are gone

    public void displayActiveQuests() {
        syncActiveQuests();
        for (Quest quest : activeQuests) {
            System.out.println("Quest: " + quest.getName());
            System.out.println("Description: " + quest.getDescription());
        }
    }

    public void displayQuestDetails(Quest quest, MainGameScreen mainGameScreen) {
        MainGameScreen.appendToMessageTextPane("New Quest: " + quest.getName() + "\n" + quest.getDescription() + "\n");
        ImageIcon questImage = new ImageIcon("path/to/quest/image.png");
        JLabel imageLabel = new JLabel(questImage);
        JPanel panel = new JPanel();
        panel.add(imageLabel);
        MainGameScreen.replaceWithAnyPanel(panel);
    }
}