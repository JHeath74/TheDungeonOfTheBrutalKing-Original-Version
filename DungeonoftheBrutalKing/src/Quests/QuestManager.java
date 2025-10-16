
// src/Quests/QuestManager.java
package Quests;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.QuestLocationType;

public class QuestManager {
    private List<Quest> availableQuests;
    private List<Quest> activeQuests;
    private Charecter character;

    public QuestManager(Charecter character) {
        this.availableQuests = new ArrayList<>();
        this.activeQuests = character.getActiveQuests();
        this.character = character;
        initializeQuests();
    }

    private void initializeQuests() {
        for (int i = 1; i <= 50; i++) {
            availableQuests.add(new Quest1("Quest " + i, "Description for Quest " + i, i, "Default conversation", QuestLocationType.STATIC));
        }
    }

    public void addActiveQuest(Quest quest) {
        if (!activeQuests.contains(quest)) {
            activeQuests.add(quest);
            if (!character.getActiveQuests().contains(quest)) {
                character.addActiveQuest(quest);
            }
            System.out.println("Quest added: " + quest.getName());
        } else {
            System.out.println("Quest is already active: " + quest.getName());
        }
    }

    public boolean removeActiveQuest(Quest quest) {
        if (activeQuests.contains(quest)) {
            activeQuests.remove(quest);
            if (character.getActiveQuests().contains(quest)) {
                character.removeActiveQuest(quest);
            }
            System.out.println("Quest removed: " + quest.getName());
            return true;
        }
        System.out.println("Quest not found: " + quest.getName());
        return false;
    }

    public void syncActiveQuests() {
        activeQuests.clear();
        activeQuests.addAll(character.getActiveQuests());

        character.getActiveQuests().clear();
        character.getActiveQuests().addAll(activeQuests);
    }

    public List<Quest> getActiveQuests() {
        syncActiveQuests();
        return activeQuests;
    }

    public void displayActiveQuests() {
        syncActiveQuests();
        System.out.println("Active Quests:");
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
