
// File: src/Quests/QuestForgiveBetrayer.java
package Quests;

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
import SharedData.GameSettings;

public class QuestForgiveBetrayer extends JPanel implements Quest {
    private static final long serialVersionUID = 1L;
    private static final int ALIGNMENT_DELTA = 3;
    private boolean completed = false;

    public QuestForgiveBetrayer(MainGameScreen mainGameScreen) {
        setLayout(new BorderLayout());

        JLabel descLabel = new JLabel(
            "<html><center><b>Forgive the Betrayer</b><br>"
            + "You confront an enemy who once betrayed you. What will you do?</center></html>",
            JLabel.CENTER
        );
        add(descLabel, BorderLayout.NORTH);

        String imagePath = GameSettings.getQuestImagesPath() + "Betrayer.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        JLabel imageLabel = new JLabel(imageIcon, JLabel.CENTER);
        add(imageLabel, BorderLayout.CENTER);

        JPanel choicePanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton releaseButton = new JButton("Release");
        JButton killButton = new JButton("Kill");
        choicePanel.add(releaseButton);
        choicePanel.add(killButton);
        add(choicePanel, BorderLayout.SOUTH);

        releaseButton.addActionListener(_ -> {
            int current = Charecter.getInstance().getAlignment();
            Charecter.getInstance().setAlignment(current + ALIGNMENT_DELTA);
            try {
                MainGameScreen.getInstance().setMessageTextPane(
                    "You release the betrayer. Mercy may bring future rewards."
                );
            } catch (IOException | InterruptedException | ParseException e1) {
                e1.printStackTrace();
            }
            releaseButton.setEnabled(false);
            killButton.setEnabled(false);
            completed = true;
            try {
                MainGameScreen.getInstance().restoreOriginalPanel();
            } catch (IOException | InterruptedException | ParseException e1) {
                e1.printStackTrace();
            }
        });

        killButton.addActionListener(_ -> {
            int current = Charecter.getInstance().getAlignment();
            Charecter.getInstance().setAlignment(current - ALIGNMENT_DELTA);
            try {
                MainGameScreen.getInstance().setMessageTextPane(
                    "You kill the betrayer. Justice is served, but at a cost."
                );
            } catch (IOException | InterruptedException | ParseException e1) {
                e1.printStackTrace();
            }
            releaseButton.setEnabled(false);
            killButton.setEnabled(false);
            completed = true;
            try {
                MainGameScreen.getInstance().restoreOriginalPanel();
            } catch (IOException | InterruptedException | ParseException e1) {
                e1.printStackTrace();
            }
        });
    }

    @Override
    public String getDescription() {
        return "Forgive the Betrayer: Confront the one who betrayed you and choose their fate.";
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
        return "QuestForgiveBetrayer:" + (completed ? "completed" : "not_completed");
    }
}
