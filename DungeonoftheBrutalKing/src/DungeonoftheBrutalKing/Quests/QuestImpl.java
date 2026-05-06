
// File: src/DungeonoftheBrutalKing/Quests/QuestImpl.java
package DungeonoftheBrutalKing.Quests;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Default implementation of the Quest interface.
 */
public class QuestImpl implements Quest {
    private static final String SEP = "\\|";
    private static final String JOIN = "|";

    private final String id;

    private String name;
    private String description;
    private boolean completed;

    // Track/category fields: SIDE / GUILD / MAIN
    private QuestType category;

    // Optional granular tags: RESCUE / COMBAT / etc.
    private EnumSet<QuestTag> tags;

    /**
     * Constructor for backward compatibility – generates an id from the name,
     * defaults to SIDE with no tags.
     */
    public QuestImpl(String name, String description, boolean completed) {
        this(sanitizeId(name), name, description, completed, QuestType.SIDE, EnumSet.noneOf(QuestTag.class));
    }

    /**
     * Constructor with category and tags (backward compatible id generation).
     */
    public QuestImpl(String name, String description, boolean completed,
                     QuestType category, Set<QuestTag> tags) {
        this(sanitizeId(name), name, description, completed, category, tags);
    }

    /**
     * New constructor with explicit id.
     */
    public QuestImpl(String id, String name, String description, boolean completed,
                     QuestType category, Set<QuestTag> tags) {
        this.id = (id == null || id.isBlank()) ? sanitizeId(name) : id.trim();
        this.name = name;
        this.description = description;
        this.completed = completed;
        this.category = (category != null ? category : QuestType.SIDE);
        this.tags = (tags == null || tags.isEmpty())
                ? EnumSet.noneOf(QuestTag.class)
                : EnumSet.copyOf(tags);
    }

    private static String sanitizeId(String name) {
        if (name == null || name.isBlank()) return "quest_unknown";
        String s = name.trim().toLowerCase();
        s = s.replaceAll("[^a-z0-9]+", "_");
        s = s.replaceAll("^_+|_+$", "");
        return s.isBlank() ? "quest_unknown" : ("quest_" + s);
    }

    @Override
    public String getId() {
        return id;
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
    public QuestType getType() {
        return category;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public void completeQuest() {
        this.completed = true;
    }

    public QuestType getCategory() {
        return category;
    }

    public Set<QuestTag> getTags() {
        return EnumSet.copyOf(tags);
    }

    @Override
    public String serialize() {
        // Format v2: id|name|description|completed|type|tag1,tag2,tag3
        String tagsCsv = tags.isEmpty()
                ? ""
                : tags.stream().map(Enum::name).collect(Collectors.joining(","));
        return String.join(JOIN,
                safe(id),
                safe(name),
                safe(description),
                Boolean.toString(completed),
                (category != null ? category.name() : QuestType.SIDE.name()),
                tagsCsv
        );
    }

    private static String safe(String s) {
        return (s == null) ? "" : s;
    }

    /**
     * Deserializes a QuestImpl from a string.
     *
     * Supports:
     * \- legacy v0: name|description|completed
     * \- legacy v1: id|name|description|completed
     * \- current v2: id|name|description|completed|type|tagCsv
     */
    public static QuestImpl deserialize(String data) {
        if (data == null) return new QuestImpl("quest_unknown", "", "", false, QuestType.SIDE, EnumSet.noneOf(QuestTag.class));

        String[] parts = data.split(SEP, -1);

        // v2: id|name|description|completed|type|tagCsv
        if (parts.length >= 6) {
            String id = parts[0];
            String name = parts[1];
            String description = parts[2];
            boolean completed = Boolean.parseBoolean(parts[3]);

            QuestType type = parseQuestType(parts[4]);
            EnumSet<QuestTag> tags = parseTags(parts[5]);

            return new QuestImpl(id, name, description, completed, type, tags);
        }

        // v1: id|name|description|completed
        if (parts.length >= 4) {
            String id = parts[0];
            String name = parts[1];
            String description = parts[2];
            boolean completed = Boolean.parseBoolean(parts[3]);

            return new QuestImpl(id, name, description, completed, QuestType.SIDE, EnumSet.noneOf(QuestTag.class));
        }

        // v0: name|description|completed
        String[] legacy = data.split(SEP, 3);
        String name = legacy.length > 0 ? legacy[0] : "";
        String description = legacy.length > 1 ? legacy[1] : "";
        boolean completed = legacy.length > 2 && Boolean.parseBoolean(legacy[2]);

        return new QuestImpl(name, description, completed);
    }

    private static QuestType parseQuestType(String raw) {
        if (raw == null || raw.isBlank()) return QuestType.SIDE;
        try {
            return QuestType.valueOf(raw.trim());
        } catch (RuntimeException ignored) {
            return QuestType.SIDE;
        }
    }

    private static EnumSet<QuestTag> parseTags(String csv) {
        if (csv == null || csv.isBlank()) return EnumSet.noneOf(QuestTag.class);

        EnumSet<QuestTag> set = EnumSet.noneOf(QuestTag.class);
        for (String token : csv.split(",", -1)) {
            if (token == null || token.isBlank()) continue;
            try {
                set.add(QuestTag.valueOf(token.trim()));
            } catch (RuntimeException ignored) {
                // ignore unknown tags for forward compatibility
            }
        }
        return set;
    }
}
