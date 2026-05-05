package DungeonoftheBrutalKing.Quests;

import java.util.EnumSet;
import java.util.Set;

/**
 * Default implementation of the Quest interface.
 */
public class QuestImpl implements Quest {
    private String name;
    private String description;
    private boolean completed;

    // Categorization fields
    private QuestType category;          // STANDARD / GUILD / MAIN
    private EnumSet<QuestType> tags;     // COMBAT, RESCUE, ESCORT, etc.

    /**
     * Constructor for backward compatibility – defaults to STANDARD with no tags.
     */
    public QuestImpl(String name, String description, boolean completed) {
        this(name, description, completed, QuestType.STANDARD, EnumSet.noneOf(QuestType.class));
    }

    /**
     * Constructor with category and tags.
     */
    public QuestImpl(String name, String description, boolean completed,
                     QuestType category, Set<QuestType> tags) {
        this.name = name;
        this.description = description;
        this.completed = completed;
        this.category = (category != null ? category : QuestType.STANDARD);
        this.tags = (tags == null || tags.isEmpty())
                ? EnumSet.noneOf(QuestType.class)
                : EnumSet.copyOf(tags);
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return name;
    }

    /** {@inheritDoc} */
    @Override
    public String getDescription() {
        return description;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isCompleted() {
        return completed;
    }

    /** {@inheritDoc} */
    @Override
    public void completeQuest() {
        this.completed = true;
    }

    /**
     * @return the main category of the quest
     */
    public QuestType getCategory() {
        return category;
    }

    /**
     * @return the tags associated with the quest
     */
    public Set<QuestType> getTags() {
        return EnumSet.copyOf(tags);
    }

    /** {@inheritDoc} */
    @Override
    public String serialize() {
        // Base format: name|description|completed
        return name + "|" + description + "|" + completed;
    }

    /**
     * Deserializes a QuestImpl from a string.
     */
    public static QuestImpl deserialize(String data) {
        String[] parts = data.split("\\|", 3);
        String name = parts[0];
        String description = parts[1];
        boolean completed = Boolean.parseBoolean(parts[2]);
        return new QuestImpl(name, description, completed);
    }

    /**
     * Returns the main category as the quest type.
     */
    @Override
    public QuestType getType() {
        return category;
    }
}
