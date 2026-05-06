
package DungeonoftheBrutalKing.Quests.Quests;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.Quests.Quest;
import DungeonoftheBrutalKing.Quests.QuestType;
import DungeonoftheBrutalKing.SharedData.GameSettings;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.text.ParseException;

public class QuestCleanseCursedShrine extends JPanel implements Quest {

    private static final long serialVersionUID = 1L;
    private static final int ALIGNMENT_DELTA = 3;
    private static final String ID = "quest_cleanse_cursed_shrine";

    private final MainGameScreen mainGameScreen;
    private JPanel originalPanel;
    private boolean completed = false;

    private final String name = "Cleanse the Cursed Shrine";
    private final QuestType type = QuestType.SIDE;

    public QuestCleanseCursedShrine(MainGameScreen mainGameScreen) {
        this.mainGameScreen = mainGameScreen;
        setLayout(new BorderLayout());

        if (this.mainGameScreen != null) {
            originalPanel = this.mainGameScreen.getGameImagesAndCombatPanel();
            this.mainGameScreen.replaceWithAnyPanel(this);
        }

        JLabel descLabel = new JLabel(
            "<html><center><b>Cleanse the Cursed Shrine</b><br>"
                + "A shrine is corrupted by dark magic. Will you risk yourself to cleanse it?</center></html>",
            JLabel.CENTER
        );
        add(descLabel, BorderLayout.NORTH);

        String imagePath = GameSettings.getQuestImagesPath() + "CursedShrine.png";
        JLabel imageLabel = new JLabel(new ImageIcon(imagePath), JLabel.CENTER);
        add(imageLabel, BorderLayout.CENTER);

        JPanel choicePanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton cleanseButton = new JButton("Cleanse the shrine");
        JButton leaveButton = new JButton("Leave it be");
        choicePanel.add(cleanseButton);
        choicePanel.add(leaveButton);
        add(choicePanel, BorderLayout.SOUTH);

        cleanseButton.addActionListener(_ -> {
            applyAlignmentDelta(+ALIGNMENT_DELTA);
            finishChoice(
                cleanseButton,
                leaveButton,
                "You bravely cleanse the shrine, dispelling the darkness. Your soul feels lighter."
            );
        });

        leaveButton.addActionListener(_ -> {
            applyAlignmentDelta(-ALIGNMENT_DELTA);
            finishChoice(
                cleanseButton,
                leaveButton,
                "You leave the shrine to its fate. The darkness lingers, and so does your guilt."
            );
        });
    }

    private void applyAlignmentDelta(int delta) {
        int current = Character.getInstance().getAlignment();
        Character.getInstance().setAlignment(current + delta);
    }

    private void finishChoice(JButton cleanseButton, JButton leaveButton, String message) {
        cleanseButton.setEnabled(false);
        leaveButton.setEnabled(false);
        try {
            if (mainGameScreen != null) {
                mainGameScreen.setMessageTextPane(message);
            }
            completeQuest();
        } catch (IOException | InterruptedException | ParseException ignored) {
            // keep quest flow working even if UI is unavailable
        }
    }

    @Override
    public void completeQuest() throws IOException, InterruptedException, ParseException {
        completed = true;
        if (mainGameScreen != null && originalPanel != null) {
            mainGameScreen.replaceWithAnyPanel(originalPanel);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Cleanse the Cursed Shrine: Risk yourself to purify a shrine corrupted by dark magic.";
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public String serialize() {
        return "QuestCleanseCursedShrine:" + (completed ? "completed" : "not_completed");
    }

    @Override
    public QuestType getType() {
        return type;
    }

    @Override
    public String getId() {
        return ID;
    }
}
