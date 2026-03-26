package Quests;

import java.util.EnumSet;
import java.util.Set;

public class QuestImpl implements Quest {
    // Existing fields
    private String name;
    private String description;
    private boolean completed;

    // New fields for categorisation
    private QuestType category;          // STANDARD / GUILD / MAIN
    private EnumSet<QuestType> tags;     // COMBAT, RESCUE, ESCORT, etc.

    // Existing constructor kept for backward compatibility – defaults to STANDARD with no tags
    public QuestImpl(String name, String description, boolean completed) {
        this(name, description, completed, QuestType.STANDARD, EnumSet.noneOf(QuestType.class));
    }

    // New constructor with category and tags
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public void completeQuest() {
        this.completed = true;
    }

    // New accessors used by QuestManager / UI
    public QuestType getCategory() {
        return category;
    }

    public Set<QuestType> getTags() {
        return EnumSet.copyOf(tags);
    }

    // Existing simple serialization remains supported for old data
    @Override
    public String serialize() {
        // Base format: name|description|completed
        return name + "|" + description + "|" + completed;
    }

    public static QuestImpl deserialize(String data) {
        String[] parts = data.split("\\|", 3);
        String name = parts[0];
        String description = parts[1];
        boolean completed = Boolean.parseBoolean(parts[2]);
        return new QuestImpl(name, description, completed);
    }
}