
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

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import Quests.Quests.QuestForgiveBetrayer;
import Quests.Quests.QuestRescuetheForgottenPrisoner;

public class QuestManager {
    private final List<Quest> availableQuests;
    private final List<Quest> activeQuests;
    private final Character character;

    public QuestManager(Character character) throws IOException, InterruptedException, ParseException {
        this.availableQuests = new ArrayList<>();
        this.activeQuests = character.getActiveQuests();
        this.character = character;
        initializeQuests();
    }

    private void initializeQuests() throws IOException, InterruptedException, ParseException {
        availableQuests.add(new QuestRescuetheForgottenPrisoner(MainGameScreen.getInstance()));
        availableQuests.add(new QuestForgiveBetrayer(MainGameScreen.getInstance()));
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

    public List<Quest> getAvailableQuests() {
        return new ArrayList<>(availableQuests);
    }

    public Quest getRandomQuest() {
        if (availableQuests.isEmpty()) return null;
        Random rand = new Random();
        return availableQuests.get(rand.nextInt(availableQuests.size()));
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
