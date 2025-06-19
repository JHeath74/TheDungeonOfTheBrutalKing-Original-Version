
// Java
package Quests;

import java.util.Random;

import DungeonoftheBrutalKing.Charecter;

public class Quest1 implements Quest {
    private String name;
    private String description;
    private boolean completed;
    private int rewardGold;
    private String conversation;
    private LocationType locationType; // Use enum for location type
    
    Charecter myChar = new Charecter();

    public Quest1(String name, String description, int i, String conversation, LocationType locationType) {
        this.name = name;
        this.description = description;
        this.rewardGold = calculateGoldReward();
        this.conversation = conversation;
        this.locationType = LocationType.STATIC;
        this.completed = false;
    }

    private int calculateGoldReward() {
        Random random = new Random();
        int goldMultiplier = random.nextInt(41) + 10; // Random value between 10 and 50
        int CharLevel = Integer.parseInt(myChar.CharInfo.get(2));
        return goldMultiplier * CharLevel;
    }
    
    public void giveExperienceReward(DungeonoftheBrutalKing.Charecter character) {
        int charLevel = Integer.parseInt(character.CharInfo.get(2)); // Get character level
        int experienceMultiplier = 100; // Define a base multiplier for experience
        int experienceReward = charLevel * experienceMultiplier; // Calculate experience reward

        int currentExperience = Integer.parseInt(character.CharInfo.get(11)); // Get current experience
        character.updateExperience(currentExperience + experienceReward); // Update experience in CharInfo index 11
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

    public void giveReward(DungeonoftheBrutalKing.Charecter character) {
        int currentGold = Integer.parseInt(character.CharInfo.get(12)); // Get current gold
        character.updateGold(currentGold + rewardGold); // Update gold in CharInfo index 12
    }
    



}
