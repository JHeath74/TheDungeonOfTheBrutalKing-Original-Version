
package Quests;

public class QuestImpl implements Quest {
    private String name;
    private String description;
    private boolean completed;

    public QuestImpl(String name, String description, boolean completed) {
        this.name = name;
        this.description = description;
        this.completed = completed;
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

    @Override
    public String serialize() {
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
