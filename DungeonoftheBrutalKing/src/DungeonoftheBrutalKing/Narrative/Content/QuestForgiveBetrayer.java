
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

public class QuestForgiveBetrayer extends JPanel implements Quest {

    private static final long serialVersionUID = 1L;
    private static final int ALIGNMENT_DELTA = 3;
    private static final String ID = "quest_forgive_betrayer";
    private static final String NAME = "Forgive the Betrayer";
    private static final QuestType TYPE = QuestType.SIDE;

    private final MainGameScreen mainGameScreen;
    private JPanel originalPanel;
    private QuestStatus status = QuestStatus.NOT_STARTED;

    public QuestForgiveBetrayer(MainGameScreen mainGameScreen) {
        this.mainGameScreen = mainGameScreen;
        setLayout(new BorderLayout());

        if (this.mainGameScreen != null) {
            originalPanel = this.mainGameScreen.getGameImagesAndCombatPanel();
            MainGameScreen.replaceWithAnyPanel(this);
        }

        JLabel descLabel = new JLabel(
            "<html><center><b>Forgive the Betrayer</b><br>"
                + "You confront an enemy who once betrayed you. What will you do?</center></html>",
            JLabel.CENTER
        );
        add(descLabel, BorderLayout.NORTH);

        String imagePath = GameSettings.getQuestImagesPath() + "Betrayer.png";
        JLabel imageLabel = new JLabel(new ImageIcon(imagePath), JLabel.CENTER);
        add(imageLabel, BorderLayout.CENTER);

        JPanel choicePanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton releaseButton = new JButton("Release");
        JButton killButton = new JButton("Kill");
        choicePanel.add(releaseButton);
        choicePanel.add(killButton);
        add(choicePanel, BorderLayout.SOUTH);

        releaseButton.addActionListener(_ -> {
            applyAlignmentDelta(+ALIGNMENT_DELTA);
            finishChoice(releaseButton, killButton,
                "You release the betrayer. Mercy may bring future rewards.");
        });

        killButton.addActionListener(_ -> {
            applyAlignmentDelta(-ALIGNMENT_DELTA);
            finishChoice(releaseButton, killButton,
                "You kill the betrayer. Justice is served, but at a cost.");
        });
    }

    private void applyAlignmentDelta(int delta) {
        int current = Character.getInstance().getAlignment();
        Character.getInstance().setAlignment(current + delta);
    }

    private void finishChoice(JButton releaseButton, JButton killButton, String message) {
        releaseButton.setEnabled(false);
        killButton.setEnabled(false);
        try {
            if (mainGameScreen != null) {
                mainGameScreen.setMessageTextPane(message);
            }
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
        return "Forgive the Betrayer: Confront the one who betrayed you and choose their fate.";
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
