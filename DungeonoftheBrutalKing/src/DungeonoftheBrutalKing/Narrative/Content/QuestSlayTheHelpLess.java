
package DungeonoftheBrutalKing.Narrative.Content;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.Narrative.Api.Quest;
import DungeonoftheBrutalKing.Narrative.Api.QuestStatus;
import DungeonoftheBrutalKing.Narrative.Api.QuestType;
import DungeonoftheBrutalKing.SharedData.GameSettings;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.text.ParseException;

public class QuestSlayTheHelpLess extends JPanel implements Quest {

    private static final long serialVersionUID = 1L;
    private static final int ALIGNMENT_DELTA = 3;
    private static final String ID = "quest_slay_the_helpless";
    private static final String NAME = "Slay the Helpless";
    private static final QuestType TYPE = QuestType.SIDE;

    private final MainGameScreen mainGameScreen;
    private JPanel originalPanel;
    private QuestStatus status = QuestStatus.NOT_STARTED;

    public QuestSlayTheHelpLess(MainGameScreen mainGameScreen) {
        this.mainGameScreen = mainGameScreen;
        setLayout(new BorderLayout());

        if (this.mainGameScreen != null) {
            originalPanel = this.mainGameScreen.getGameImagesAndCombatPanel();
            MainGameScreen.replaceWithAnyPanel(this);
        }

        JLabel descLabel = new JLabel(
            "<html><center><b>Slay the Helpless</b><br>"
                + "A defenseless creature or NPC stands before you. Will you kill it for loot or convenience, or spare its life?</center></html>",
            JLabel.CENTER
        );
        add(descLabel, BorderLayout.NORTH);

        String imagePath = GameSettings.getQuestImagesPath() + "Helpless.png";
        JLabel imageLabel = new JLabel(new ImageIcon(imagePath), JLabel.CENTER);
        add(imageLabel, BorderLayout.CENTER);

        JPanel choicePanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton slayButton = new JButton("Slay the helpless for loot");
        JButton spareButton = new JButton("Spare the helpless");
        choicePanel.add(slayButton);
        choicePanel.add(spareButton);
        add(choicePanel, BorderLayout.SOUTH);

        slayButton.addActionListener(_ -> {
            applyAlignmentDelta(-ALIGNMENT_DELTA);
            finishChoice(slayButton, spareButton,
                "\nYou kill the defenseless creature. Its blood stains your hands, and your alignment decreases.\n");
        });

        spareButton.addActionListener(_ -> {
            applyAlignmentDelta(+ALIGNMENT_DELTA);
            finishChoice(slayButton, spareButton,
                "\nYou spare the helpless creature. Mercy fills your heart, and your alignment increases.\n");
        });
    }

    private void applyAlignmentDelta(int delta) {
        int current = Character.getInstance().getAlignment();
        Character.getInstance().setAlignment(current + delta);
    }

    private void finishChoice(JButton btn1, JButton btn2, String message) {
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        MainGameScreen.appendToMessageTextPane(message);
        try {
            complete();
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
        if (mainGameScreen != null && originalPanel != null) {
            MainGameScreen.replaceWithAnyPanel(originalPanel);
        }
    }

    @Override
    public String getId() { return ID; }

    @Override
    public String getName() { return NAME; }

    @Override
    public String getTitle() { return NAME; }

    @Override
    public String getDescription() {
        return "Slay the Helpless: Kill a defenseless NPC or creature for loot or convenience.";
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
