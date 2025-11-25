package Quests.Quests;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import Quests.Quest;
import SharedData.GameSettings;

public class QuestGuideTheLostSoul extends JPanel implements Quest {

    private static final long serialVersionUID = 1L;
    private static final int ALIGNMENT_DELTA = 3;
    private boolean completed = false;
    private JPanel originalPanel;

    public QuestGuideTheLostSoul() throws IOException, InterruptedException, ParseException {
        setLayout(new BorderLayout());

        // Store the original panel and replace with quest panel
        originalPanel = MainGameScreen.getInstance().getGameImagesAndCombatPanel();
        MainGameScreen.replaceWithAnyPanel(this);

        JLabel descLabel = new JLabel(
            "<html><center><b>Guide the Lost Soul</b><br>"
            + "A confused spirit lingers, unable to find peace. Will you help it find its way to the afterlife?</center></html>",
            JLabel.CENTER
        );
        add(descLabel, BorderLayout.NORTH);

        String imagePath = GameSettings.getQuestImagesPath() + "LostSoul.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        JLabel imageLabel = new JLabel(imageIcon);
        add(imageLabel, BorderLayout.CENTER);

        JPanel choicePanel = new JPanel(new GridLayout(0, 1, 10, 10));
        JButton helpButton = new JButton("Listen and offer guidance");
        JButton ignoreButton = new JButton("Dismiss the spirit");

        choicePanel.add(helpButton);
        choicePanel.add(ignoreButton);

        add(choicePanel, BorderLayout.SOUTH);

        helpButton.addActionListener(e -> {
            int current = Character.getInstance().getAlignment();
            Character.getInstance().setAlignment(current + ALIGNMENT_DELTA);
            try {
                MainGameScreen.getInstance().setMessageTextPane(
                    "You listen to the lost soul's story and offer comforting words. With your guidance, the spirit finds peace and moves on. Your compassion increases your alignment."
                );
            } catch (IOException | InterruptedException | ParseException e1) {
                e1.printStackTrace();
            }
            completeQuest();
            helpButton.setEnabled(false);
            ignoreButton.setEnabled(false);
        });

        ignoreButton.addActionListener(e -> {
            int current = Character.getInstance().getAlignment();
            Character.getInstance().setAlignment(current - ALIGNMENT_DELTA);
            try {
                MainGameScreen.getInstance().setMessageTextPane(
                    "You turn away from the lost soul. The spirit wails in despair and fades. Your indifference decreases your alignment."
                );
            } catch (IOException | InterruptedException | ParseException e1) {
                e1.printStackTrace();
            }
            completeQuest();
            helpButton.setEnabled(false);
            ignoreButton.setEnabled(false);
        });
    }

    @Override
    public String getDescription() {
        return "Guide the Lost Soul: Help a confused spirit find its way to the afterlife through dialogue and clues.";
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public void completeQuest() {
        completed = true;
        MainGameScreen.replaceWithAnyPanel(originalPanel);
    }

    @Override
    public String serialize() {
        return "QuestGuideTheLostSoul:" + (completed ? "completed" : "not_completed");
    }
}
