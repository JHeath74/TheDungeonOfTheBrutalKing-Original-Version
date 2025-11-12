
// src/Quests/Quest1.java
package Quests;

import Alignment.Alignment;
import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.GameSettings;

import javax.swing.*;
import java.awt.*;

public class Quest1 implements Quest {
    private final String name = "Rescue the Forgotten Prisoner";
    private final String prisonerName = "George";
    private final String description = "Free a starving NPC named " + prisonerName + ", locked in a hidden cell. No reward, just gratitude.";
    private boolean completed;
    private final int rewardGold = 0;
    private final Alignment alignment;
    private final String conversation = "Thank you, stranger! I thought I would never see the light of day again. I was imprisoned here for refusing to betray my friends.";
    private final String imprisonmentReason = "Imprisoned for refusing to betray his friends.";
    private final QuestType questType = QuestType.RESCUE;
    private final EncounterType encounterType = EncounterType.STATIC_PERSON;
    private final String descriptionForEncounter = "A frail and desperate prisoner named " + prisonerName + " is locked in a hidden cell, pleading for help.";
    private final String encounterTarget = prisonerName;

    public Quest1(Alignment alignment) {
        this.completed = false;
        this.alignment = alignment;
    }

    public String getPrisonerName() { return prisonerName; }
    public String getImprisonmentReason() { return imprisonmentReason; }
    public Alignment getAlignment() { return alignment; }
    public QuestType getQuestType() { return questType; }
    @Override public String getName() { return name; }
    @Override public String getDescription() { return description; }
    @Override public boolean isCompleted() { return completed; }
    @Override public void completeQuest() { this.completed = true; }
    public void giveReward(Charecter character) {}
    public int getRewardGold() { return rewardGold; }
    public String getConversation() { return conversation; }
    public EncounterType getEncounterType() { return encounterType; }
    public String getEncounterTarget() { return encounterTarget; }
    public String getDescriptionForEncounter() { return descriptionForEncounter; }

    public void releasePrisoner(Charecter player) {
        player.setAlignment(3);
        this.completed = true;
    }

    public void ignorePrisoner(Charecter player) {
        player.setAlignment(-3);
        this.completed = false;
    }

    public JPanel createEncounterPanel(Charecter player, MainGameScreen mainGameScreen) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel imageLabel = new JLabel(new ImageIcon(GameSettings.QuestImagesPath + "/Prisoner.png"));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(imageLabel, BorderLayout.NORTH);

        JLabel descLabel = new JLabel(getDescriptionForEncounter());
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(descLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton freeButton = new JButton("Free");
        JButton ignoreButton = new JButton("Ignore");

        freeButton.addActionListener(_ -> {
            releasePrisoner(player);
            MainGameScreen.replaceWithAnyPanel(new JPanel());
        });
        ignoreButton.addActionListener(_ -> {
            ignorePrisoner(player);
            MainGameScreen.replaceWithAnyPanel(new JPanel());
        });

        buttonPanel.add(freeButton);
        buttonPanel.add(ignoreButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    @Override
    public String serialize() {
        return name + "|" +
               description + "|" +
               completed + "|" +
               rewardGold + "|" +
               (alignment != null ? alignment.name() : "null") + "|" +
               (conversation != null ? conversation : "null") + "|" +
               (questType != null ? questType.name() : "null") + "|" +
               (encounterType != null ? encounterType.name() : "null") + "|" +
               (encounterTarget != null ? encounterTarget : "null") + "|" +
               imprisonmentReason;
    }
}
