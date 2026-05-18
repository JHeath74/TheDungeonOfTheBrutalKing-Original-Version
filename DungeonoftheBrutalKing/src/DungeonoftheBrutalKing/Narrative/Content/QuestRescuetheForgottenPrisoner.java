
package DungeonoftheBrutalKing.Narrative.Content;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.Narrative.Api.Quest;
import DungeonoftheBrutalKing.Narrative.Api.QuestStatus;
import DungeonoftheBrutalKing.Narrative.Api.QuestType;
import DungeonoftheBrutalKing.SharedData.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;

public class QuestRescuetheForgottenPrisoner extends JPanel implements Quest {

    private static final long serialVersionUID = 1L;
    private static final String ID = "quest_rescue_the_forgotten_prisoner";
    private static final String NAME = "Rescue the Forgotten Prisoner";
    private static final int ALIGNMENT_DELTA = 3;
    private static final QuestType TYPE = QuestType.SIDE;

    private static final String PRISONER_NAME = "George";
    private static final String CONVERSATION =
            "Thank you, stranger! I thought I would never see the light of day again. " +
            "I was imprisoned here for refusing to betray my friends.";

    private final MainGameScreen mainGameScreen;
    private JPanel originalPanel;
    private QuestStatus status = QuestStatus.NOT_STARTED;

    public QuestRescuetheForgottenPrisoner(MainGameScreen mainGameScreen) {
        this.mainGameScreen = mainGameScreen;
        setLayout(new BorderLayout());

        if (this.mainGameScreen != null) {
            originalPanel = this.mainGameScreen.getGameImagesAndCombatPanel();
        }

        JLabel imageLabel = new JLabel(new ImageIcon(GameSettings.getQuestImagesPath() + "Prisoner.png"));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(imageLabel, BorderLayout.NORTH);

        JLabel descLabel = new JLabel(
            "<html><center>A frail and desperate prisoner named " + PRISONER_NAME +
            " is locked in a hidden cell, pleading for help.</center></html>",
            JLabel.CENTER
        );
        add(descLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton freeButton = new JButton("Free");
        JButton ignoreButton = new JButton("Ignore");
        buttonPanel.add(freeButton);
        buttonPanel.add(ignoreButton);
        add(buttonPanel, BorderLayout.SOUTH);

        freeButton.addActionListener(_ -> {
            applyAlignmentDelta(+ALIGNMENT_DELTA);
            finishChoice(freeButton, ignoreButton, "\n" + CONVERSATION + "\n", true);
        });

        ignoreButton.addActionListener(_ -> {
            applyAlignmentDelta(-ALIGNMENT_DELTA);
            finishChoice(freeButton, ignoreButton,
                "\nYou ignore the prisoner. He looks at you with despair.\n", false);
        });
    }

    private void applyAlignmentDelta(int delta) {
        int current = Character.getInstance().getAlignment();
        Character.getInstance().setAlignment(current + delta);
    }

    private void finishChoice(JButton btn1, JButton btn2, String message, boolean succeeded) {
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        MainGameScreen.appendToMessageTextPane(message);
        try {
            if (succeeded) {
                complete();
            } else {
                fail();
                if (mainGameScreen != null && originalPanel != null) {
                    MainGameScreen.replaceWithAnyPanel(originalPanel);
                }
            }
        } catch (IOException | InterruptedException | ParseException ignored) {
        }
    }

    @Override
    public void start() {
        if (status == QuestStatus.NOT_STARTED) {
            status = QuestStatus.ACTIVE;
        }
    }

    @Override
    public void complete() throws IOException, InterruptedException, ParseException {
        status = QuestStatus.COMPLETED;
        if (mainGameScreen != null && originalPanel != null) {
            MainGameScreen.replaceWithAnyPanel(originalPanel);
        }
    }

    @Override
    public void completeQuest() throws IOException, InterruptedException, ParseException {
        complete();
    }

    @Override
    public void fail() {
        status = QuestStatus.FAILED;
    }

    @Override
    public String getId() { return ID; }

    @Override
    public String getName() { return NAME; }

    @Override
    public String getTitle() { return NAME; }

    @Override
    public String getDescription() {
        return "Rescue the Forgotten Prisoner: Free a starving NPC named " + PRISONER_NAME +
               ", locked in a hidden cell. No reward, just gratitude.";
    }

    @Override
    public QuestType getType() { return TYPE; }

    @Override
    public QuestStatus getStatus() { return status; }

    @Override
    public boolean isCompleted() { return status == QuestStatus.COMPLETED; }

    @Override
    public boolean isActive() { return status == QuestStatus.ACTIVE; }

    @Override
    public String serialize() {
        return ID + ":" + status.name();
    }
}
