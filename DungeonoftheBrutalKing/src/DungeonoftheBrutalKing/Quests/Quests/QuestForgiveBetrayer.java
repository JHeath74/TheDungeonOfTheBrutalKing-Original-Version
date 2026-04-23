
// `src/DungeonoftheBrutalKing/Quests/Quests/QuestForgiveBetrayer.java`
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
import java.awt.Container;
import java.awt.GridLayout;
import java.io.IOException;
import java.text.ParseException;

public class QuestForgiveBetrayer extends JPanel implements Quest {
    private static final long serialVersionUID = 1L;

    private static final int ALIGNMENT_DELTA = 3;

    private final MainGameScreen mainGameScreen;

    private boolean completed = false;

    private final String name = "Forgive the Betrayer";
    private final QuestType type = QuestType.STANDARD;

    public QuestForgiveBetrayer(MainGameScreen mainGameScreen) {
        this.mainGameScreen = mainGameScreen;

        setLayout(new BorderLayout());

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
                "You release the betrayer. Mercy may bring future rewards."
            );
        });

        killButton.addActionListener(_ -> {
            applyAlignmentDelta(-ALIGNMENT_DELTA);
            finishChoice(releaseButton, killButton,
                "You kill the betrayer. Justice is served, but at a cost."
            );
        });
    }

    private void applyAlignmentDelta(int delta) {
        int current = Character.getInstance().getAlignment();
        Character.getInstance().setAlignment(current + delta);
    }

    private void finishChoice(JButton releaseButton, JButton killButton, String message) {
        releaseButton.setEnabled(false);
        killButton.setEnabled(false);
        completed = true;

        uiSafely(() -> mainGameScreen.setMessageTextPane(message));
        closeQuestPanel();
    }

    private void closeQuestPanel() {
        setVisible(false);

        Container parent = getParent();
        if (parent != null) {
            parent.remove(this);
            parent.revalidate();
            parent.repaint();
        }
    }

    private void uiSafely(UiAction action) {
        try {
            action.run();
        } catch (IOException | InterruptedException | ParseException | RuntimeException ignored) {
            // Keep quest flow working even if UI is unavailable.
        }
    }

    @FunctionalInterface
    private interface UiAction {
        void run() throws IOException, InterruptedException, ParseException;
    }

    @Override
    public String getName() {
        return name;
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

    @Override
    public QuestType getType() {
        return type;
    }
}
