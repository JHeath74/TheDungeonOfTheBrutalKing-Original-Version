
package DungeonoftheBrutalKing.Narrative.Core;

import DungeonoftheBrutalKing.Narrative.Api.Quest;
import DungeonoftheBrutalKing.Narrative.Api.QuestStatus;
import DungeonoftheBrutalKing.Narrative.Encounters.EncounterEvent;

import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;

public abstract class BaseQuest implements Quest {

    private final String id;
    private final String title;
    private final String description;
    private QuestStatus status = QuestStatus.NOT_STARTED;

    protected BaseQuest(String id, String title, String description) {
        this.id          = Objects.requireNonNull(id);
        this.title       = Objects.requireNonNull(title);
        this.description = description != null ? description : "";
    }

    @Override public String getId()          { return id; }
    @Override public String getTitle()       { return title; }
    @Override public String getDescription() { return description; }
    @Override public QuestStatus getStatus() { return status; }

    @Override
    public void start() {
        if (status == QuestStatus.NOT_STARTED) {
            status = QuestStatus.ACTIVE;
            onStart();
        }
    }

    @Override
    public void complete() throws IOException, InterruptedException, ParseException {
        if (status == QuestStatus.ACTIVE) {
            status = QuestStatus.COMPLETED;
            onComplete();
        }
    }

    @Override
    public void fail() {
        if (status == QuestStatus.ACTIVE) {
            status = QuestStatus.FAILED;
            onFail();
        }
    }

    /** Called when the quest becomes active. Override to add logic. */
    protected void onStart() {}

    /** Called when the quest completes. Override to add logic. */
    protected void onComplete() throws IOException, InterruptedException, ParseException {}

    /** Called when the quest fails. Override to add logic. */
    protected void onFail() {}

    /** Override to react to encounter events while this quest is active. */
    @Override
    public void onEncounter(EncounterEvent event) {}
}
