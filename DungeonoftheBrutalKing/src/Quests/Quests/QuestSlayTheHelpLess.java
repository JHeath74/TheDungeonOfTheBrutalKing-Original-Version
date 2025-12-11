package Quests.Quests;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;
import Quests.Quest;
import SharedData.GameSettings;

public class QuestSlayTheHelpLess extends JPanel implements Quest {

    private static final long serialVersionUID = 1L;
    private static final int ALIGNMENT_DELTA = 3;
    private boolean completed = false;
    private JPanel originalPanel;

    public QuestSlayTheHelpLess() throws IOException, InterruptedException, ParseException {
        setLayout(new BorderLayout());

        // Store the original panel and replace with quest panel
        originalPanel = MainGameScreen.getInstance().getGameImagesAndCombatPanel();
        MainGameScreen.replaceWithAnyPanel(this);

        JLabel descLabel = new JLabel(
            "<html><center><b>Slay the Helpless</b><br>"
            + "A defenseless creature or NPC stands before you. Will you kill it for loot or convenience, or spare its life?</center></html>",
            JLabel.CENTER
        );
        add(descLabel, BorderLayout.NORTH);

        String imagePath = GameSettings.getQuestImagesPath() + "Helpless.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        JLabel imageLabel = new JLabel(imageIcon);
        add(imageLabel, BorderLayout.CENTER);

        JPanel choicePanel = new JPanel(new GridLayout(0, 1, 10, 10));
        JButton slayButton = new JButton("Slay the helpless for loot");
        JButton spareButton = new JButton("Spare the helpless");

        choicePanel.add(slayButton);
        choicePanel.add(spareButton);

        add(choicePanel, BorderLayout.SOUTH);

        slayButton.addActionListener(e -> {
            int current = Charecter.getInstance().getAlignment();
            Charecter.getInstance().setAlignment(current - ALIGNMENT_DELTA);
            try {
                MainGameScreen.getInstance().setMessageTextPane(
                    "You kill the defenseless creature. Its blood stains your hands, and your alignment decreases."
                );
            } catch (IOException | InterruptedException | ParseException e1) {
                e1.printStackTrace();
            }
            completeQuest();
            slayButton.setEnabled(false);
            spareButton.setEnabled(false);
        });

        spareButton.addActionListener(e -> {
            int current = Charecter.getInstance().getAlignment();
            Charecter.getInstance().setAlignment(current + ALIGNMENT_DELTA);
            try {
                MainGameScreen.getInstance().setMessageTextPane(
                    "You spare the helpless creature. Mercy fills your heart, and your alignment increases."
                );
            } catch (IOException | InterruptedException | ParseException e1) {
                e1.printStackTrace();
            }
            completeQuest();
            slayButton.setEnabled(false);
            spareButton.setEnabled(false);
        });
    }

    @Override
    public String getDescription() {
        return "Slay the Helpless: Kill a defenseless NPC or creature for loot or convenience.";
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
        return "QuestSlayTheHelpLess:" + (completed ? "completed" : "not_completed");
    }
}
