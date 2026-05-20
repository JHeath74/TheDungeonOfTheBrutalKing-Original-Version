
// File: src/DungeonoftheBrutalKing/Narrative/Impl/QuestImpl.java
package DungeonoftheBrutalKing.Narrative.Impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import DungeonoftheBrutalKing.Narrative.Api.Quest;
import DungeonoftheBrutalKing.Narrative.Api.QuestStatus;
import DungeonoftheBrutalKing.Narrative.Api.QuestTag;
import DungeonoftheBrutalKing.Narrative.Api.QuestType;
import DungeonoftheBrutalKing.Narrative.Encounters.EncounterEvent;

public class QuestImpl implements Quest {
    private static final String SEP = "\\|";
    private static final String JOIN = "|";

    private final String id;
    private String name;
    private String description;
    private boolean completed;
    private boolean active;
    private QuestStatus status;
    private QuestType category;
    private EnumSet<QuestTag> tags;

    public QuestImpl(String name, String description, boolean completed) {
        this(sanitizeId(name), name, description, completed, QuestType.SIDE, EnumSet.noneOf(QuestTag.class));
    }

    public QuestImpl(String name, String description, boolean completed,
                     QuestType category, Set<QuestTag> tags) {
        this(sanitizeId(name), name, description, completed, category, tags);
    }

    public QuestImpl(String id, String name, String description, boolean completed,
                     QuestType category, Set<QuestTag> tags) {
        this.id = (id == null || id.isBlank()) ? sanitizeId(name) : id.trim();
        this.name = name;
        this.description = description;
        this.completed = completed;
        this.active = false;
        this.status = QuestStatus.INACTIVE;
        this.category = (category != null ? category : QuestType.SIDE);
        this.tags = (tags == null || tags.isEmpty())
                ? EnumSet.noneOf(QuestTag.class)
                : EnumSet.copyOf(tags);
    }

    private static String sanitizeId(String name) {
        if (name == null || name.isBlank()) return "quest_unknown";
        String s = name.trim().toLowerCase().replaceAll("[^a-z0-9]+", "_").replaceAll("^_+|_+$", "");
        return s.isBlank() ? "quest_unknown" : ("quest_" + s);
    }

    @Override public String getId() { return id; }
    @Override public String getName() { return name; }
    @Override public String getTitle() { return name; }
    @Override public String getDescription() { return description; }
    @Override public QuestType getType() { return category; }
    @Override public QuestStatus getStatus() { return status; }
    @Override public boolean isCompleted() { return completed; }
    @Override public boolean isActive() { return active; }

    public QuestType getCategory() { return category; }
    public Set<QuestTag> getTags() { return EnumSet.copyOf(tags); }

    @Override
    public void start() {
        this.active = true;
        this.status = QuestStatus.ACTIVE;
    }

    @Override
    public void complete() throws IOException, InterruptedException, ParseException {
        this.active = false;
        this.completed = true;
        this.status = QuestStatus.COMPLETED;
    }

    @Override
    public void fail() {
        this.active = false;
        this.status = QuestStatus.FAILED;
    }

    @Override
    public void completeQuest() {
        this.completed = true;
        this.status = QuestStatus.COMPLETED;
    }

    @Override
    public String serialize() {
        String tagsCsv = tags.isEmpty()
                ? ""
                : tags.stream().map(Enum::name).collect(Collectors.joining(","));
        return String.join(JOIN,
                safe(id), safe(name), safe(description),
                Boolean.toString(completed),
                (category != null ? category.name() : QuestType.SIDE.name()),
                tagsCsv);
    }

    private static String safe(String s) { return (s == null) ? "" : s; }

    public static QuestImpl deserialize(String data) {
        if (data == null) return new QuestImpl("quest_unknown", "", "", false, QuestType.SIDE, EnumSet.noneOf(QuestTag.class));
        String[] parts = data.split(SEP, -1);

        if (parts.length >= 6)
            return new QuestImpl(parts[0], parts[1], parts[2], Boolean.parseBoolean(parts[3]),
                    parseQuestType(parts[4]), parseTags(parts[5]));

        if (parts.length >= 4)
            return new QuestImpl(parts[0], parts[1], parts[2], Boolean.parseBoolean(parts[3]),
                    QuestType.SIDE, EnumSet.noneOf(QuestTag.class));

        String[] legacy = data.split(SEP, 3);
        return new QuestImpl(
                legacy.length > 0 ? legacy[0] : "",
                legacy.length > 1 ? legacy[1] : "",
                legacy.length > 2 && Boolean.parseBoolean(legacy[2]));
    }

    private static QuestType parseQuestType(String raw) {
        if (raw == null || raw.isBlank()) return QuestType.SIDE;
        try { return QuestType.valueOf(raw.trim()); } catch (RuntimeException e) { return QuestType.SIDE; }
    }

    private static EnumSet<QuestTag> parseTags(String csv) {
        EnumSet<QuestTag> set = EnumSet.noneOf(QuestTag.class);
        if (csv == null || csv.isBlank()) return set;
        for (String token : csv.split(",", -1)) {
            try { set.add(QuestTag.valueOf(token.trim())); } catch (RuntimeException ignored) {}
        }
        return set;
    }

	@Override
	public void onEncounter(EncounterEvent event) {
		// TODO Auto-generated method stub
		
	}
}
