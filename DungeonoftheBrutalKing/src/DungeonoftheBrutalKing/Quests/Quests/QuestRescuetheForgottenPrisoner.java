
// File: `src/DungeonoftheBrutalKing/Quests/Quests/QuestRescuetheForgottenPrisoner.java`
package DungeonoftheBrutalKing.Quests.Quests;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.Quests.EncounterType;
import DungeonoftheBrutalKing.Quests.Quest;
import DungeonoftheBrutalKing.Quests.QuestType;
import DungeonoftheBrutalKing.SharedData.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.EnumSet;
import java.util.Set;

public class QuestRescuetheForgottenPrisoner implements Quest {

    private static final String ID = "quest_rescue_the_forgotten_prisoner";
    private static final int ALIGNMENT_DELTA = 3;

    private final String name = "Rescue the Forgotten Prisoner";
    private final String prisonerName = "George";
    private final String description = "Free a starving NPC named " + prisonerName + ", locked in a hidden cell. No reward, just gratitude.";
    private boolean completed;
    private final int rewardGold = 0;

    private final String conversation =
            "Thank you, stranger! I thought I would never see the light of day again. " +
            "I was imprisoned here for refusing to betray my friends.";
    private final String imprisonmentReason = "Imprisoned for refusing to betray his friends.";

    // Prefer RESCUE, otherwise fall back safely so the file compiles with any enum layout.
    private final QuestType questType = resolveQuestType("RESCUE", "SIDE_QUEST", "QUEST", "MAIN_QUEST", "MISC");
    private final QuestType category = resolveQuestType("SIDE_QUEST", "QUEST", "MAIN_QUEST", "MISC");

    // Only include tags that exist in QuestType; missing ones are skipped.
    private final EnumSet<QuestType> tags = resolveQuestTags("RESCUE", "NEGOTIATION", "CHOICE", "DIALOGUE");

    private final EncounterType encounterType = EncounterType.STATIC_PERSON;
    private final String descriptionForEncounter =
            "A frail and desperate prisoner named " + prisonerName + " is locked in a hidden cell, pleading for help.";
    private final String encounterTarget = prisonerName;

    private final MainGameScreen mainGameScreen;
    private JPanel originalPanel;

    public QuestRescuetheForgottenPrisoner(MainGameScreen mainGameScreen) {
        this.completed = false;
        this.mainGameScreen = mainGameScreen;
    }

    private static QuestType resolveQuestType(String... preferredNames) {
        for (String n : preferredNames) {
            if (n == null || n.isBlank()) continue;
            try {
                return QuestType.valueOf(n);
            } catch (IllegalArgumentException ignored) {
                // try next
            }
        }
        QuestType[] values = QuestType.values();
        if (values.length == 0) throw new IllegalStateException("QuestType enum has no values");
        return values[0];
    }

    private static EnumSet<QuestType> resolveQuestTags(String... names) {
        EnumSet<QuestType> set = EnumSet.noneOf(QuestType.class);
        if (names == null) return set;
        for (String n : names) {
            if (n == null || n.isBlank()) continue;
            try {
                set.add(QuestType.valueOf(n));
            } catch (IllegalArgumentException ignored) {
                // skip missing tag
            }
        }
        return set;
    }

    @Override
    public String getId() {
        return ID;
    }

    public String getPrisonerName() {
        return prisonerName;
    }

    public String getImprisonmentReason() {
        return imprisonmentReason;
    }

    public QuestType getQuestType() {
        return questType;
    }

    public QuestType getCategory() {
        return category;
    }

    public Set<QuestType> getTags() {
        return EnumSet.copyOf(tags);
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
        if (originalPanel != null) {
            MainGameScreen.replaceWithAnyPanel(originalPanel);
        }
    }

    public void giveReward(Character character) {
        // no reward
    }

    public int getRewardGold() {
        return rewardGold;
    }

    public String getConversation() {
        return conversation;
    }

    public EncounterType getEncounterType() {
        return encounterType;
    }

    public String getEncounterTarget() {
        return encounterTarget;
    }

    public String getDescriptionForEncounter() {
        return descriptionForEncounter;
    }

    public void releasePrisoner(Character player) throws IOException, InterruptedException, ParseException {
        int current = Character.getInstance().getAlignment();
        Character.getInstance().setAlignment(current + ALIGNMENT_DELTA);

        this.completed = true;
        MainGameScreen.appendToMessageTextPane("\n" + conversation + "\n");
        completeQuest();
    }

    public void ignorePrisoner(Character player) throws IOException, InterruptedException, ParseException {
        int current = Character.getInstance().getAlignment();
        Character.getInstance().setAlignment(current - ALIGNMENT_DELTA);

        this.completed = false;
        MainGameScreen.appendToMessageTextPane("\nYou ignore the prisoner. He looks at you with despair.\n");

        if (originalPanel != null) {
            MainGameScreen.replaceWithAnyPanel(originalPanel);
        }
    }

    public JPanel createEncounterPanel(Character player) {
        if (originalPanel == null) {
            originalPanel = MainGameScreen.getInstance().getGameImagesAndCombatPanel();
        }

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel imageLabel = new JLabel(new ImageIcon(GameSettings.getQuestImagesPath() + "Prisoner.png"));
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
            freeButton.setEnabled(false);
            ignoreButton.setEnabled(false);
        });

        ignoreButton.addActionListener(_ -> {
            try {
                ignorePrisoner(player);
            } catch (IOException | InterruptedException | ParseException e) {
                e.printStackTrace();
            }
            freeButton.setEnabled(false);
            ignoreButton.setEnabled(false);
        });

        buttonPanel.add(freeButton);
        buttonPanel.add(ignoreButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        MainGameScreen.replaceWithAnyPanel(mainPanel);
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

    @Override
    public QuestType getType() {
        return category;
    }
}
