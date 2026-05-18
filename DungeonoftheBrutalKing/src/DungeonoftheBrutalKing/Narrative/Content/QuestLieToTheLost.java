
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

public class QuestLieToTheLost extends JPanel implements Quest {

    private static final long serialVersionUID = 1L;
    private static final int ALIGNMENT_DELTA = 3;
    private static final String ID = "quest_lie_to_the_lost";
    private static final String NAME = "Lie to the Lost";
    private static final QuestType TYPE = QuestType.SIDE;

    private final MainGameScreen mainGameScreen;
    private JPanel originalPanel;
    private QuestStatus status = QuestStatus.NOT_STARTED;

    public QuestLieToTheLost(MainGameScreen mainGameScreen) {
        this.mainGameScreen = mainGameScreen;
        setLayout(new BorderLayout());

        if (this.mainGameScreen != null) {
            originalPanel = this.mainGameScreen.getGameImagesAndCombatPanel();
            MainGameScreen.replaceWithAnyPanel(this);
        }

        JLabel descLabel = new JLabel(
            "<html><center><b>Lie to the Lost</b><br>"
                + "A lost spirit or NPC seeks your guidance. Will you mislead them for your own gain, or help them find peace?</center></html>",
            JLabel.CENTER
        );
        add(descLabel, BorderLayout.NORTH);

        String imagePath = GameSettings.getQuestImagesPath() + "LostSoul.png";
        JLabel imageLabel = new JLabel(new ImageIcon(imagePath), JLabel.CENTER);
        add(imageLabel, BorderLayout.CENTER);

        JPanel choicePanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton lieButton = new JButton("Lie and mislead the lost");
        JButton helpButton = new JButton("Help the lost find peace");
        choicePanel.add(lieButton);
        choicePanel.add(helpButton);
        add(choicePanel, BorderLayout.SOUTH);

        lieButton.addActionListener(_ -> {
            applyAlignmentDelta(-ALIGNMENT_DELTA);
            finishChoice(lieButton, helpButton,
                "\nYou lie to the lost, sending them astray for your own benefit. The spirit suffers, and your alignment decreases.\n");
        });

        helpButton.addActionListener(_ -> {
            applyAlignmentDelta(+ALIGNMENT_DELTA);
            finishChoice(lieButton, helpButton,
                "\nYou help the lost find peace. Your compassion increases your alignment.\n");
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
        return "Lie to the Lost: Mislead a spirit or NPC for selfish reasons, causing them harm.";
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
