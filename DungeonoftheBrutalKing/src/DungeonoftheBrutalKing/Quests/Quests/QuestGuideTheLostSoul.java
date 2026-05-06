
// File: `src/DungeonoftheBrutalKing/Quests/Quests/QuestGuideTheLostSoul.java`
package DungeonoftheBrutalKing.Quests.Quests;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.text.ParseException;
import java.util.EnumSet;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.Quests.Quest;
import DungeonoftheBrutalKing.Quests.QuestType;
import DungeonoftheBrutalKing.SharedData.GameSettings;

public class QuestGuideTheLostSoul extends JPanel implements Quest {

    private static final long serialVersionUID = 1L;
    private static final int ALIGNMENT_DELTA = 3;

    private boolean completed = false;
    private JPanel originalPanel;

    // Quest metadata
    private static final String ID = "quest_guide_the_lost_soul";
    private final String name = "Guide the Lost Soul";

    // Resolve to an enum value that actually exists to keep compilation stable.
    private static final QuestType CATEGORY = resolveQuestType("SIDE_QUEST", "QUEST", "MAIN_QUEST", "MISC");

    // Only include tags if those enum constants actually exist; otherwise keep empty.
    private static final EnumSet<QuestType> TAGS = resolveQuestTags("NEGOTIATION", "DISCOVERY", "DIALOGUE", "EXPLORATION");

    public QuestGuideTheLostSoul(MainGameScreen mainGameScreen) throws IOException, InterruptedException, ParseException {
        setLayout(new BorderLayout());

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
                MainGameScreen.appendToMessageTextPane(
                    "\nYou listen to the lost soul's story and offer comforting words. With your guidance, the spirit finds peace and moves on. Your compassion increases your alignment.\n"
                );
                completeQuest();
            } catch (IOException | InterruptedException | ParseException ex) {
                ex.printStackTrace();
            }
            helpButton.setEnabled(false);
            ignoreButton.setEnabled(false);
        });

        ignoreButton.addActionListener(e -> {
            int current = Character.getInstance().getAlignment();
            Character.getInstance().setAlignment(current - ALIGNMENT_DELTA);
            try {
                MainGameScreen.appendToMessageTextPane(
                    "\nYou turn away from the lost soul. The spirit wails in despair and fades. Your indifference decreases your alignment.\n"
                );
                completeQuest();
            } catch (IOException | InterruptedException | ParseException ex) {
                ex.printStackTrace();
            }
            helpButton.setEnabled(false);
            ignoreButton.setEnabled(false);
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

    private static EnumSet<QuestType> resolveQuestTags(String... names) {
        EnumSet<QuestType> set = EnumSet.noneOf(QuestType.class);
        if (names == null) return set;
        for (String n : names) {
            if (n == null || n.isBlank()) continue;
            try {
                set.add(QuestType.valueOf(n));
            } catch (IllegalArgumentException ignored) {
                // skip missing tag
            }
        }
        return set;
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
        return "Guide the Lost Soul: Help a confused spirit find its way to the afterlife through dialogue and clues.";
    }

    public QuestType getCategory() {
        return CATEGORY;
    }

    public Set<QuestType> getTags() {
        return EnumSet.copyOf(TAGS);
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
        return "QuestGuideTheLostSoul:" + (completed ? "completed" : "not_completed");
    }

    @Override
    public QuestType getType() {
        return CATEGORY;
    }
}
