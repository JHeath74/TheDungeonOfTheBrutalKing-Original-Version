
// Java
package Quests;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The QuestManager class is responsible for managing quests in the game.
 * It maintains a list of available quests and active quests, and provides
 * methods to initialize, generate, and display quests.
 */
public class QuestManager {
    private List<Quest> availableQuests; // List of all available quests in the game
    private List<Quest> activeQuests;    // List of currently active quests
    private Charecter character;         // Reference to the Charecter class

    /**
     * Constructor for QuestManager.
     * Initializes the available and active quests lists and populates the available quests.
     *
     * @param character The Charecter instance to sync active quests with.
     */
    public QuestManager(Charecter character) {
        this.availableQuests = new ArrayList<>();
        this.activeQuests = character.getActiveQuests(); // Sync with Charecter activeQuests
        this.character = character;
        initializeQuests(); // Populate the available quests list
    }

    /**
     * Initializes the available quests list with predefined quests.
     * This method adds up to 50 quests to the availableQuests list.
     */
    private void initializeQuests() {
        for (int i = 1; i <= 50; i++) {
            availableQuests.add(new Quest1("Quest " + i, "Description for Quest " + i, i, "Default conversation", LocationType.STATIC));
        }
    }

    /**
     * Adds a quest to the active quests list and syncs with the Charecter class.
     *
     * @param quest The quest to be added.
     */
    public void addActiveQuest(Quest quest) {
        if (!activeQuests.contains(quest)) {
            activeQuests.add(quest);
            if (!character.getActiveQuests().contains(quest)) {
                character.addActiveQuest(quest); // Sync with Charecter
            }
            System.out.println("Quest added: " + quest.getName());
        } else {
            System.out.println("Quest is already active: " + quest.getName());
        }
    }

    /**
     * Removes a quest from the active quests list and syncs with the Charecter class.
     *
     * @param quest The quest to be removed.
     * @return True if the quest was removed, false if it was not found.
     */
    public boolean removeActiveQuest(Quest quest) {
        if (activeQuests.contains(quest)) {
            activeQuests.remove(quest);
            if (character.getActiveQuests().contains(quest)) {
                character.removeActiveQuest(quest); // Sync with Charecter
            }
            System.out.println("Quest removed: " + quest.getName());
            return true;
        }
        System.out.println("Quest not found: " + quest.getName());
        return false;
    }

    /**
     * Synchronizes the active quests list with the Charecter class.
     * Ensures both lists contain the same quests.
     */
    public void syncActiveQuests() {
        activeQuests.clear();
        activeQuests.addAll(character.getActiveQuests());

        character.getActiveQuests().clear();
        character.getActiveQuests().addAll(activeQuests);
    }

    /**
     * Retrieves the list of active quests.
     *
     * @return A list of currently active quests.
     */
    public List<Quest> getActiveQuests() {
        syncActiveQuests(); // Ensure both lists are synchronized
        return activeQuests;
    }

    /**
     * Displays all active quests.
     */
    public void displayActiveQuests() {
        syncActiveQuests(); // Ensure both lists are synchronized
        System.out.println("Active Quests:");
        for (Quest quest : activeQuests) {
            System.out.println("Quest: " + quest.getName());
            System.out.println("Description: " + quest.getDescription());
        }
    }

    public void displayQuestDetails(Quest quest, MainGameScreen mainGameScreen) {
        // Display the quest description in the MessageTextPane
        MainGameScreen.appendToMessageTextPane("New Quest: " + quest.getName() + "\n" + quest.getDescription() + "\n");

        // Display the quest image in the GameImagesAndCombatPanel
        ImageIcon questImage = new ImageIcon("path/to/quest/image.png"); // Replace with the actual image path
        JLabel imageLabel = new JLabel(questImage);
        JPanel panel = new JPanel();
        panel.add(imageLabel);
        mainGameScreen.replaceWithAnyPanel(panel);
    }
}
