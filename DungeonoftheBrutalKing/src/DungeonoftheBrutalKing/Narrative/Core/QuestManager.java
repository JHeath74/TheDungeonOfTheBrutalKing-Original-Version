
// src/DungeonoftheBrutalKing/Quests/core/QuestManager.java
package DungeonoftheBrutalKing.Narrative.Core;

import DungeonoftheBrutalKing.Narrative.Api.*;
import DungeonoftheBrutalKing.Narrative.Triggers.TriggerManager;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public final class QuestManager {

    private final Map<String, Quest> quests = new LinkedHashMap<>();
    private final TriggerManager triggerManager;

    public QuestManager(TriggerManager triggerManager) {
        this.triggerManager = Objects.requireNonNull(triggerManager);
    }

    public void register(Quest quest) {
        quests.put(quest.getId(), quest);
    }

    public Optional<Quest> getQuest(String id) {
        return Optional.ofNullable(quests.get(id));
    }

    public void startQuest(String id) {
        getQuest(id).ifPresent(Quest::start);
    }

    public void completeQuest(String id) {
        getQuest(id).ifPresent(t -> {
			try {
				t.complete();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
    }

    public void failQuest(String id) {
        getQuest(id).ifPresent(Quest::fail);
    }

    public List<Quest> getActiveQuests() {
        List<Quest> active = new ArrayList<>();
        for (Quest q : quests.values()) {
            if (q.isActive()) active.add(q);
        }
        return Collections.unmodifiableList(active);
    }

    public TriggerManager getTriggerManager() {
        return triggerManager;
    }
}
