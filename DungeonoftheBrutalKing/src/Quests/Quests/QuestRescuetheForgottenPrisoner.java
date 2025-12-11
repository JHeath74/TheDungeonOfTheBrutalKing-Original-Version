
package Quests.Quests;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;
import Quests.EncounterType;
import Quests.Quest;
import Quests.QuestType;
import SharedData.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;

public class QuestRescuetheForgottenPrisoner implements Quest {
    private final String name = "Rescue the Forgotten Prisoner";
    private final String prisonerName = "George";
    private final String description = "Free a starving NPC named " + prisonerName + ", locked in a hidden cell. No reward, just gratitude.";
    private boolean completed;
    private final int rewardGold = 0;
    private final String conversation = "Thank you, stranger! I thought I would never see the light of day again. I was imprisoned here for refusing to betray my friends.";
    private final String imprisonmentReason = "Imprisoned for refusing to betray his friends.";
    private final QuestType questType = QuestType.RESCUE;
    private final EncounterType encounterType = EncounterType.STATIC_PERSON;
    private final String descriptionForEncounter = "A frail and desperate prisoner named " + prisonerName + " is locked in a hidden cell, pleading for help.";
    private final String encounterTarget = prisonerName;
    private final MainGameScreen mainGameScreen;

    public QuestRescuetheForgottenPrisoner(MainGameScreen mainGameScreen) {
        this.completed = false;
        this.mainGameScreen = mainGameScreen;
    }

    public String getPrisonerName() { return prisonerName; }
    public String getImprisonmentReason() { return imprisonmentReason; }
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

    public void releasePrisoner(Charecter player) throws IOException, InterruptedException, ParseException {
        player.setAlignment(3);
        this.completed = true;
        mainGameScreen.setMessageTextPane(conversation);
    }

    public void ignorePrisoner(Charecter player) throws IOException, InterruptedException, ParseException {
        player.setAlignment(-3);
        this.completed = false;
        mainGameScreen.setMessageTextPane("You ignore the prisoner. He looks at you with despair.");
    }

    public JPanel createEncounterPanel(Charecter player) {
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
            try {
                releasePrisoner(player);
            } catch (IOException | InterruptedException | ParseException e) {
                e.printStackTrace();
            }
            MainGameScreen.replaceWithAnyPanel(new JPanel());
        });
        ignoreButton.addActionListener(_ -> {
            try {
                ignorePrisoner(player);
            } catch (IOException | InterruptedException | ParseException e) {
                e.printStackTrace();
            }
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
               (conversation != null ? conversation : "null") + "|" +
               (questType != null ? questType.name() : "null") + "|" +
               (encounterType != null ? encounterType.name() : "null") + "|" +
               (encounterTarget != null ? encounterTarget : "null") + "|" +
               imprisonmentReason;
    }
}
