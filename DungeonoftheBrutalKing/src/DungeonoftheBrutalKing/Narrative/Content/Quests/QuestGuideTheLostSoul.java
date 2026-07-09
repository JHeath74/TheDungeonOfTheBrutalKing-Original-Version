
package DungeonoftheBrutalKing.Narrative.Content.Quests;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.Narrative.Api.Quest;
import DungeonoftheBrutalKing.Narrative.Api.QuestStatus;
import DungeonoftheBrutalKing.Narrative.Api.QuestType;
import DungeonoftheBrutalKing.Narrative.Encounters.EncounterEvent;
import DungeonoftheBrutalKing.SharedData.GameSettings;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.text.ParseException;

public class QuestGuideTheLostSoul extends JPanel implements Quest {

    private static final long serialVersionUID = 1L;
    private static final int ALIGNMENT_DELTA = 3;
    private static final String ID = "quest_guide_the_lost_soul";
    private static final String NAME = "Guide the Lost Soul";
    private static final QuestType TYPE = QuestType.SIDE;

    private final MainGameScreen mainGameScreen;
    private JPanel originalPanel;
    private QuestStatus status = QuestStatus.NOT_STARTED;

    public QuestGuideTheLostSoul(MainGameScreen mainGameScreen) {
        this.mainGameScreen = mainGameScreen;
        setLayout(new BorderLayout());

        if (this.mainGameScreen != null) {
            originalPanel = this.mainGameScreen.getGameImagesAndCombatPanel();
            MainGameScreen.replaceWithAnyPanel(this);
        }

        JLabel descLabel = new JLabel(
            "<html><center><b>Guide the Lost Soul</b><br>"
                + "A confused spirit lingers, unable to find peace. Will you help it find its way to the afterlife?</center></html>",
            JLabel.CENTER
        );
        add(descLabel, BorderLayout.NORTH);

        String imagePath = GameSettings.getQuestImagesPath() + "LostSoul.png";
        JLabel imageLabel = new JLabel(new ImageIcon(imagePath), JLabel.CENTER);
        add(imageLabel, BorderLayout.CENTER);

        JPanel choicePanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton helpButton = new JButton("Listen and offer guidance");
        JButton ignoreButton = new JButton("Dismiss the spirit");
        choicePanel.add(helpButton);
        choicePanel.add(ignoreButton);
        add(choicePanel, BorderLayout.SOUTH);

        helpButton.addActionListener(_ -> {
            applyAlignmentDelta(+ALIGNMENT_DELTA);
            finishChoice(helpButton, ignoreButton,
                "\nYou listen to the lost soul's story and offer comforting words. With your guidance, the spirit finds peace and moves on.\n");
        });

        ignoreButton.addActionListener(_ -> {
            applyAlignmentDelta(-ALIGNMENT_DELTA);
            finishChoice(helpButton, ignoreButton,
                "\nYou turn away from the lost soul. The spirit wails in despair and fades.\n");
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
        return "Guide the Lost Soul: Help a confused spirit find its way to the afterlife through dialogue and clues.";
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

	@Override
	public void onEncounter(EncounterEvent event) {
		// TODO Auto-generated method stub
		
	}
}
