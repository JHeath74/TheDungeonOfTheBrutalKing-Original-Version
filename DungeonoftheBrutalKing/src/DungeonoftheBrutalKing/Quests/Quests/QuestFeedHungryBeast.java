
// File: `src/DungeonoftheBrutalKing/Quests/Quests/QuestFeedHungryBeast.java`
package DungeonoftheBrutalKing.Quests.Quests;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.Quests.Quest;
import DungeonoftheBrutalKing.Quests.QuestType;
import DungeonoftheBrutalKing.SharedData.GameSettings;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.text.ParseException;
import java.util.EnumSet;
import java.util.Set;

public class QuestFeedHungryBeast extends JPanel implements Quest {

    private static final long serialVersionUID = 1L;
    private static final int ALIGNMENT_DELTA = 3;

    private boolean completed = false;
    private JPanel originalPanel;

    // Quest metadata
    private static final String ID = "quest_feed_hungry_beast";
    private final String name = "Feed the Hungry Beast";

    // Pick a QuestType that exists in your enum.
    // Tries common names first, otherwise falls back to the first enum constant.
    private static final QuestType CATEGORY = resolveQuestType("SIDE_QUEST", "QUEST", "MAIN_QUEST", "MISC");

    // Keep tag set empty unless you have tag-like constants in the same enum.
    private final EnumSet<QuestType> tags = EnumSet.noneOf(QuestType.class);

    public QuestFeedHungryBeast(MainGameScreen mainGameScreen) throws IOException, InterruptedException, ParseException {
        setLayout(new BorderLayout());

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
            int food = Character.getInstance().getFood();
            if (food > 0) {
                Character.getInstance().setFood(food - 1);
                try {
                    completeQuest();
                    MainGameScreen.appendToMessageTextPane("\nYou feed the beast. It calms down and lets you pass.\n");
                } catch (IOException | InterruptedException | ParseException ex) {
                    ex.printStackTrace();
                }
            } else {
                MainGameScreen.appendToMessageTextPane("\nYou have no food to offer.\n");
            }
            feedButton.setEnabled(false);
            attackButton.setEnabled(false);
        });

        attackButton.addActionListener(e -> {
            int current = Character.getInstance().getAlignment();
            Character.getInstance().setAlignment(current - ALIGNMENT_DELTA);
            try {
                completeQuest();
                MainGameScreen.appendToMessageTextPane("\nYou attack the beast.\n");
            } catch (IOException | InterruptedException | ParseException ex) {
                ex.printStackTrace();
            }
            feedButton.setEnabled(false);
            attackButton.setEnabled(false);
        });
    }

    private static QuestType resolveQuestType(String... preferredNames) {
        for (String n : preferredNames) {
            if (n == null || n.isBlank()) continue;
            try {
                return QuestType.valueOf(n);
            } catch (IllegalArgumentException ignored) {
                // try next
            }
        }
        QuestType[] values = QuestType.values();
        if (values.length == 0) throw new IllegalStateException("QuestType enum has no values");
        return values[0];
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Feed the Hungry Beast: Feed or attack a starving, non-hostile creature.";
    }

    public QuestType getCategory() {
        return CATEGORY;
    }

    public Set<QuestType> getTags() {
        return EnumSet.copyOf(tags);
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public void completeQuest() throws IOException, InterruptedException, ParseException {
        completed = true;
        MainGameScreen.replaceWithAnyPanel(originalPanel);
    }

    @Override
    public String serialize() {
        return "QuestFeedHungryBeast:" + (completed ? "completed" : "not_completed");
    }

    @Override
    public QuestType getType() {
        return CATEGORY;
    }
}
