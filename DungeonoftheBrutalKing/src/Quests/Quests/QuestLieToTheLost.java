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

public class QuestLieToTheLost extends JPanel implements Quest {

    private static final long serialVersionUID = 1L;
    private static final int ALIGNMENT_DELTA = 3;
    private boolean completed = false;
    private JPanel originalPanel;

    public QuestLieToTheLost() throws IOException, InterruptedException, ParseException {
        setLayout(new BorderLayout());

        // Store the original panel and replace with quest panel
        originalPanel = MainGameScreen.getInstance().getGameImagesAndCombatPanel();
        MainGameScreen.replaceWithAnyPanel(this);

        JLabel descLabel = new JLabel(
            "<html><center><b>Lie to the Lost</b><br>"
            + "A lost spirit or NPC seeks your guidance. Will you mislead them for your own gain, or help them find peace?</center></html>",
            JLabel.CENTER
        );
        add(descLabel, BorderLayout.NORTH);

        String imagePath = GameSettings.getQuestImagesPath() + "LostSoul.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        JLabel imageLabel = new JLabel(imageIcon);
        add(imageLabel, BorderLayout.CENTER);

        JPanel choicePanel = new JPanel(new GridLayout(0, 1, 10, 10));
        JButton lieButton = new JButton("Lie and mislead the lost");
        JButton helpButton = new JButton("Help the lost find peace");

        choicePanel.add(lieButton);
        choicePanel.add(helpButton);

        add(choicePanel, BorderLayout.SOUTH);

        lieButton.addActionListener(e -> {
            int current = Charecter.getInstance().getAlignment();
            Charecter.getInstance().setAlignment(current - ALIGNMENT_DELTA);
            try {
                MainGameScreen.getInstance().setMessageTextPane(
                    "You lie to the lost, sending them astray for your own benefit. The spirit suffers, and your alignment decreases."
                );
            } catch (IOException | InterruptedException | ParseException e1) {
                e1.printStackTrace();
            }
            completeQuest();
            lieButton.setEnabled(false);
            helpButton.setEnabled(false);
        });

        helpButton.addActionListener(e -> {
            int current = Charecter.getInstance().getAlignment();
            Charecter.getInstance().setAlignment(current + ALIGNMENT_DELTA);
            try {
                MainGameScreen.getInstance().setMessageTextPane(
                    "You help the lost find peace. Your compassion increases your alignment."
                );
            } catch (IOException | InterruptedException | ParseException e1) {
                e1.printStackTrace();
            }
            completeQuest();
            lieButton.setEnabled(false);
            helpButton.setEnabled(false);
        });
    }

    @Override
    public String getDescription() {
        return "Lie to the Lost: Mislead a spirit or NPC for selfish reasons, causing them harm.";
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
        return "QuestLieToTheLost:" + (completed ? "completed" : "not_completed");
    }
}
