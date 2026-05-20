
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

public class QuestFeedHungryBeast extends JPanel implements Quest {

    private static final long serialVersionUID = 1L;
    private static final int ALIGNMENT_DELTA = 3;
    private static final String ID = "quest_feed_hungry_beast";
    private static final String NAME = "Feed the Hungry Beast";
    private static final QuestType TYPE = QuestType.SIDE;

    private QuestStatus status = QuestStatus.NOT_STARTED;
    private JPanel originalPanel;

    private final MainGameScreen mainGameScreen;

    public QuestFeedHungryBeast(MainGameScreen mainGameScreen) {
        this.mainGameScreen = mainGameScreen;
        setLayout(new BorderLayout());

        if (this.mainGameScreen != null) {
            originalPanel = this.mainGameScreen.getGameImagesAndCombatPanel();
            MainGameScreen.replaceWithAnyPanel(this);
        }

        JLabel descLabel = new JLabel(
            "<html><center><b>Feed the Hungry Beast</b><br>"
                + "A monstrous creature is starving but not hostile. Feed it instead of killing it.</center></html>",
            JLabel.CENTER
        );
        add(descLabel, BorderLayout.NORTH);

        String imagePath = GameSettings.getQuestImagesPath() + "HungryBeast.png";
        JLabel imageLabel = new JLabel(new ImageIcon(imagePath), JLabel.CENTER);
        add(imageLabel, BorderLayout.CENTER);

        JPanel choicePanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton feedButton = new JButton("Feed the beast (use 1 Food)");
        JButton attackButton = new JButton("Attack the beast");
        choicePanel.add(feedButton);
        choicePanel.add(attackButton);
        add(choicePanel, BorderLayout.SOUTH);

        feedButton.addActionListener(_ -> {
            int food = Character.getInstance().getFood();
            if (food > 0) {
                Character.getInstance().setFood(food - 1);
                MainGameScreen.appendToMessageTextPane("\nYou feed the beast. It calms down and lets you pass.\n");
                finishQuest();
            } else {
                MainGameScreen.appendToMessageTextPane("\nYou have no food to offer.\n");
            }
            feedButton.setEnabled(false);
            attackButton.setEnabled(false);
        });

        attackButton.addActionListener(_ -> {
            int current = Character.getInstance().getAlignment();
            Character.getInstance().setAlignment(current - ALIGNMENT_DELTA);
            MainGameScreen.appendToMessageTextPane("\nYou attack the beast.\n");
            finishQuest();
            feedButton.setEnabled(false);
            attackButton.setEnabled(false);
        });
    }

    private void finishQuest() {
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
        return "Feed the Hungry Beast: Feed or attack a starving, non-hostile creature.";
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
    public void completeQuest() throws IOException, InterruptedException, ParseException {
        complete();
    }

	@Override
	public void onEncounter(EncounterEvent event) {
		// TODO Auto-generated method stub
		
	}
}
