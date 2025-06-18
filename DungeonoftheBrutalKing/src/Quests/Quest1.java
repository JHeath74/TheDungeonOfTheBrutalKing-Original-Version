
// Java
package Quests;

import java.util.Random;

public class Quest1 implements Quest {
    private String name;
    private String description;
    private boolean completed;
    private int rewardGold;
    private String conversation;
    private LocationType locationType; // Use enum for location type

    public Quest1(String name, String description, int dungeonLevel, String conversation, LocationType locationType) {
        this.name = name;
        this.description = description;
        this.rewardGold = calculateGoldReward(dungeonLevel);
        this.conversation = conversation;
        this.locationType = LocationType.STATIC;
        this.completed = false;
    }

    private int calculateGoldReward(int dungeonLevel) {
        Random random = new Random();
        int goldMultiplier = random.nextInt(41) + 10; // Random value between 10 and 50
        return goldMultiplier * dungeonLevel;
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

    public void giveReward(DungeonoftheBrutalKing.Charecter character, int weaponSlot) {
        int currentGold = Integer.parseInt(character.CharInfo.get(12)); // Get current gold
        character.updateGold(currentGold + rewardGold); // Update gold in CharInfo index 12
    }
}
