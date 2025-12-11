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

public class QuestFeedHungryBeast extends JPanel implements Quest {

    private static final long serialVersionUID = 1L;
    private static final int ALIGNMENT_DELTA = 3;
    private boolean completed = false;
    private JPanel originalPanel;

    public QuestFeedHungryBeast() throws IOException, InterruptedException, ParseException {
        setLayout(new BorderLayout());

        // Store the original panel and replace with quest panel
        originalPanel = MainGameScreen.getInstance().getGameImagesAndCombatPanel();
        MainGameScreen.replaceWithAnyPanel(this);

        JLabel descLabel = new JLabel(
            "<html><center><b>Feed the Hungry Beast</b><br>"
            + "A monstrous creature is starving but not hostile. Feed it instead of killing it.</center></html>",
            JLabel.CENTER
        );
        add(descLabel, BorderLayout.NORTH);

        String imagePath = GameSettings.getQuestImagesPath() + "HungryBeast.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        JLabel imageLabel = new JLabel(imageIcon);
        add(imageLabel, BorderLayout.CENTER);

        JPanel choicePanel = new JPanel(new GridLayout(0, 1, 10, 10));
        JButton feedButton = new JButton("Feed the beast (use 1 Food)");
        JButton attackButton = new JButton("Attack the beast");

        choicePanel.add(feedButton);
        choicePanel.add(attackButton);

        add(choicePanel, BorderLayout.SOUTH);

        feedButton.addActionListener(e -> {
            int food = Charecter.getInstance().getFood();
            if (food > 0) {
                Charecter.getInstance().setFood(food - 1);
                int current = Charecter.getInstance().getAlignment();
                Charecter.getInstance().setAlignment(current + ALIGNMENT_DELTA);
                try {
                    MainGameScreen.getInstance().setMessageTextPane(
                        "You feed the beast. It devours the food gratefully. Your compassion increases your alignment."
                    );
                } catch (IOException | InterruptedException | ParseException e1) {
                    e1.printStackTrace();
                }
                completeQuest();
            } else {
                int current = Charecter.getInstance().getAlignment();
                Charecter.getInstance().setAlignment(current - ALIGNMENT_DELTA);
                try {
                    MainGameScreen.getInstance().setMessageTextPane(
                        "You have no food. Forced to defend yourself, you attack and kill the beast. Your alignment decreases."
                    );
                } catch (IOException | InterruptedException | ParseException e1) {
                    e1.printStackTrace();
                }
                completeQuest();
            }
            feedButton.setEnabled(false);
            attackButton.setEnabled(false);
        });

        attackButton.addActionListener(e -> {
            int current = Charecter.getInstance().getAlignment();
            Charecter.getInstance().setAlignment(current - ALIGNMENT_DELTA);
            try {
                MainGameScreen.getInstance().setMessageTextPane(
                    "You attack and kill the beast. Your alignment decreases."
                );
            } catch (IOException | InterruptedException | ParseException e1) {
                e1.printStackTrace();
            }
            completeQuest();
            feedButton.setEnabled(false);
            attackButton.setEnabled(false);
        });
    }

    @Override
    public String getDescription() {
        return "Feed the Hungry Beast: Feed or attack a starving, non-hostile creature.";
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public void completeQuest() {
        completed = true;
        // Restore the original panel
        MainGameScreen.replaceWithAnyPanel(originalPanel);
    }

    @Override
    public String serialize() {
        return "QuestFeedHungryBeast:" + (completed ? "completed" : "not_completed");
    }
}
