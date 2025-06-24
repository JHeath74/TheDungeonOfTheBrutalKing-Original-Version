
package Quests;

import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;

public class Quest1 implements Quest {
    private String name;
    private String description;
    private boolean completed;
    private int rewardGold;
    private LocationType locationType;

    private int dungeonLevel; // The dungeon level where the quest appears
    private int locationX;    // X-coordinate of the quest location
    private int locationY;    // Y-coordinate of the quest location

    Charecter myChar = new Charecter();

    public Quest1(String name, String description, int i, String conversation, LocationType locationType) {
        this.name = name;
        this.description = description;
        this.rewardGold = calculateGoldReward();
        this.locationType = LocationType.STATIC;
        this.completed = false;
        assignRandomLocation(); // Call the method to assign a random location
    }

    private int calculateGoldReward() {
        Random random = new Random();
        int goldMultiplier = random.nextInt(41) + 10; // Random value between 10 and 50
        int charLevel = Integer.parseInt(myChar.CharInfo.get(2));
        return goldMultiplier * charLevel;
    }

    public void giveExperienceReward(Charecter character) {
        int charLevel = Integer.parseInt(character.CharInfo.get(2)); // Get character level
        int experienceMultiplier = 100; // Define a base multiplier for experience
        int experienceReward = charLevel * experienceMultiplier; // Calculate experience reward

        int currentExperience = Integer.parseInt(character.CharInfo.get(11)); // Get current experience
        character.updateExperience(currentExperience + experienceReward); // Update experience in CharInfo index 11
    }

    public void checkQuestCompletion(Charecter character) {
        int charLevel = Integer.parseInt(character.CharInfo.get(2)); // Get character level
        if (charLevel >= 10) { // Example condition for quest completion
            this.completed = true;
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

    public LocationType getLocationType() {
        return locationType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public void completeQuest() {
        this.completed = true;
    }

    public void giveReward(Charecter character) {
        int currentGold = Integer.parseInt(character.CharInfo.get(12)); // Get current gold
        character.updateGold(currentGold + rewardGold); // Update gold in CharInfo index 12
    }

    private void assignRandomLocation() {
        Random random = new Random();
        this.dungeonLevel = random.nextInt(4) + 1; // Randomly choose a level between 1 and 4
        this.locationX = random.nextInt(100);     // Random X-coordinate (adjust range as needed)
        this.locationY = random.nextInt(100);     // Random Y-coordinate (adjust range as needed)
    }

    public int getDungeonLevel() {
        return dungeonLevel;
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }
}
