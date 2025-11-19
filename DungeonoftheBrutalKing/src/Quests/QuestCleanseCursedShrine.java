package Quests;

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
import SharedData.GameSettings;

public class QuestCleanseCursedShrine extends JPanel implements Quest {

    private static final long serialVersionUID = 1L;
    private static final int ALIGNMENT_DELTA = 3;
    private boolean completed = false;
    
    public QuestCleanseCursedShrine(MainGameScreen mainGameScreen) {
        setLayout(new BorderLayout());

        JLabel descLabel = new JLabel(
            "<html><center><b>Wounded Adventurer</b><br>"
            + "You encounter a wounded adventurer lying on the dungeon floor. He begs for help, offering nothing in return.</center></html>",
            JLabel.CENTER
        );
        add(descLabel, BorderLayout.NORTH);

        String imagePath = GameSettings.getQuestImagesPath() + "WoundedAdventurer.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        JLabel imageLabel = new JLabel(imageIcon);
        add(imageLabel, BorderLayout.CENTER);

        JPanel choicePanel = new JPanel(new GridLayout(0, 1, 10, 10));
        JButton helpButton = new JButton("Help the adventurer to safety");
        JButton ignoreButton = new JButton("Ignore him and continue onward");

        choicePanel.add(helpButton);
        choicePanel.add(ignoreButton);

        add(choicePanel, BorderLayout.SOUTH);

        helpButton.addActionListener(e -> {
            int current = Character.getInstance().getAlignment();
            Character.getInstance().setAlignment(current + ALIGNMENT_DELTA);
            try {
                MainGameScreen.getInstance().setMessageTextPane(
                    "Compassion and selflessness strengthen your moral standing."
                );
            } catch (IOException | InterruptedException | ParseException e1) {
                e1.printStackTrace();
            }
            helpButton.setEnabled(false);
            ignoreButton.setEnabled(false);
        });

        ignoreButton.addActionListener(e -> {
            int current = Character.getInstance().getAlignment();
            Character.getInstance().setAlignment(current - ALIGNMENT_DELTA);
            try {
                MainGameScreen.getInstance().setMessageTextPane(
                    "Choosing survival over mercy hardens your character toward darkness."
                );
            } catch (IOException | InterruptedException | ParseException e1) {
                e1.printStackTrace();
            }
            helpButton.setEnabled(false);
            ignoreButton.setEnabled(false);
        });
    }


@Override
public String getDescription() {
    return "Wounded Adventurer: Help or ignore a wounded adventurer in the dungeon.";
}



@Override
public boolean isCompleted() {
    return completed;
}

@Override
public void completeQuest() {
    completed = true;
}

@Override
public String serialize() {
    return "QuestCleanseCursedShrine:" + (completed ? "completed" : "not_completed");
}

}
