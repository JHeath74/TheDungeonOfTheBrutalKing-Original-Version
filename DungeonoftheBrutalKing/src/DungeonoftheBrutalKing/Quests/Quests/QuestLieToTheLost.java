
// File: `src/DungeonoftheBrutalKing/Quests/Quests/QuestLieToTheLost.java`
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

public class QuestLieToTheLost extends JPanel implements Quest {

    private static final long serialVersionUID = 1L;
    private static final int ALIGNMENT_DELTA = 3;

    private boolean completed = false;
    private JPanel originalPanel;

    // Quest metadata
    private static final String ID = "quest_lie_to_the_lost";
    private final String name = "Lie to the Lost";

    // Resolve to an enum value that actually exists to keep compilation stable.
    private static final QuestType CATEGORY = resolveQuestType("SIDE_QUEST", "QUEST", "MAIN_QUEST", "MISC");

    // Only include tags if those enum constants actually exist; otherwise keep empty.
    private static final EnumSet<QuestType> TAGS = resolveQuestTags("NEGOTIATION", "DIALOGUE", "DECEPTION", "CHOICE");

    public QuestLieToTheLost(MainGameScreen mainGameScreen) throws IOException, InterruptedException, ParseException {
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
            int current = Character.getInstance().getAlignment();
            Character.getInstance().setAlignment(current - ALIGNMENT_DELTA);
            try {
                MainGameScreen.appendToMessageTextPane(
                    "\nYou lie to the lost, sending them astray for your own benefit. The spirit suffers, and your alignment decreases.\n"
                );
                completeQuest();
            } catch (IOException | InterruptedException | ParseException ex) {
                ex.printStackTrace();
            }
            lieButton.setEnabled(false);
            helpButton.setEnabled(false);
        });

        helpButton.addActionListener(e -> {
            int current = Character.getInstance().getAlignment();
            Character.getInstance().setAlignment(current + ALIGNMENT_DELTA);
            try {
                MainGameScreen.appendToMessageTextPane(
                    "\nYou help the lost find peace. Your compassion increases your alignment.\n"
                );
                completeQuest();
            } catch (IOException | InterruptedException | ParseException ex) {
                ex.printStackTrace();
            }
            lieButton.setEnabled(false);
            helpButton.setEnabled(false);
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
        return "Lie to the Lost: Mislead a spirit or NPC for selfish reasons, causing them harm.";
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
        return "QuestLieToTheLost:" + (completed ? "completed" : "not_completed");
    }

    @Override
    public QuestType getType() {
        return CATEGORY;
    }
}
