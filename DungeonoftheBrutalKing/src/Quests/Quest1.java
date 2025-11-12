
// src/Quests/Quest1.java
package Quests;

import java.util.Random;
import Alignment.Alignment;
import DungeonoftheBrutalKing.Charecter;

public class Quest1 implements Quest {
    private String name;
    private String description;
    private boolean completed;
    private int rewardGold;
    private Alignment alignment;
    private String conversation;
    private EncounterType encounterType;
    private String encounterTarget;
    Charecter myChar = new Charecter();

    public Quest1(String name, String description, int i, String conversation, Alignment alignment) {
        this.name = name;
        this.description = description;
        this.rewardGold = calculateGoldReward();
        this.completed = false;
        this.alignment = alignment;
        this.conversation = conversation;
        this.encounterType = getRandomEncounterType();
        this.encounterTarget = null;
    }

    private EncounterType getRandomEncounterType() {
        EncounterType[] types = EncounterType.values();
        Random random = new Random();
        return types[random.nextInt(types.length)];
    }

    private int calculateGoldReward() {
        if (myChar.getCharInfo() == null || myChar.getCharInfo().size() <= 2) {
            throw new IllegalStateException();
        }
        Random random = new Random();
        int goldMultiplier = random.nextInt(41) + 10;

int charLevel = myChar.getLevel();

        return goldMultiplier * charLevel;
    }

    public Alignment getAlignment() {
        return alignment;
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
        int currentGold = character.getGold();
        character.setGold(currentGold + rewardGold);
    }

    public int getRewardGold() {
        return rewardGold;
    }


public String getConversation() {
    return this.conversation;
}


    public EncounterType getEncounterType() {
        return encounterType;
    }

    public String getEncounterTarget() {
        return encounterTarget;
    }


@Override
public String serialize() {
    return name + "|" +
           description + "|" +
           completed + "|" +
           rewardGold + "|" +
           (alignment != null ? alignment.name() : "null") + "|" +
           (conversation != null ? conversation : "null") + "|" +
           (encounterType != null ? encounterType.name() : "null") + "|" +
           (encounterTarget != null ? encounterTarget : "null");
}

}
