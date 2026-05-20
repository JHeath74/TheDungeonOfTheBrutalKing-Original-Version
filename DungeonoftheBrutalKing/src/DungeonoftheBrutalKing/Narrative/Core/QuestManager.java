
package DungeonoftheBrutalKing.Narrative.Core;

import DungeonoftheBrutalKing.Narrative.Api.Quest;
import DungeonoftheBrutalKing.Narrative.Encounters.EncounterEvent;
import DungeonoftheBrutalKing.Narrative.Npc.Npc;
import DungeonoftheBrutalKing.Narrative.Puzzle.Puzzle;
import DungeonoftheBrutalKing.Narrative.Triggers.TriggerManager;

import java.util.*;

public final class QuestManager {

    private final Map<String, Quest>   quests  = new LinkedHashMap<>();
    private final Map<String, Npc>     npcs    = new LinkedHashMap<>();
    private final Map<String, Puzzle>  puzzles = new LinkedHashMap<>();
    private final TriggerManager       triggerManager;

    public QuestManager() {
        this.triggerManager = new TriggerManager();
    }

    public QuestManager(TriggerManager triggerManager) {
        this.triggerManager = Objects.requireNonNull(triggerManager);
    }

    // ── Quests ──────────────────────────────────────────────────────────────

    public void registerQuest(Quest quest) {
        if (quest != null) quests.put(quest.getId(), quest);
    }

    public Optional<Quest> getQuest(String id) {
        return id == null ? Optional.empty() : Optional.ofNullable(quests.get(id));
    }

    public void startQuest(String id) {
        getQuest(id).ifPresent(Quest::start);
    }

    public void completeQuest(String id) {
        getQuest(id).ifPresent(q -> {
            try { q.complete(); } catch (Exception e) { e.printStackTrace(); }
        });
    }

    public void failQuest(String id) {
        getQuest(id).ifPresent(Quest::fail);
    }

    public boolean hasQuest(String id) {
        return id != null && quests.containsKey(id);
    }

    public List<Quest> getActiveQuests() {
        List<Quest> active = new ArrayList<>();
        for (Quest q : quests.values()) { if (q.isActive()) active.add(q); }
        return Collections.unmodifiableList(active);
    }

    public List<Quest> getAllQuests() {
        return Collections.unmodifiableList(new ArrayList<>(quests.values()));
    }

    // ── NPCs ────────────────────────────────────────────────────────────────

    public void registerNpc(Npc npc) {
        if (npc != null) npcs.put(npc.getId(), npc);
    }

    public Optional<Npc> getNpc(String id) {
        return id == null ? Optional.empty() : Optional.ofNullable(npcs.get(id));
    }

    public Collection<Npc> getAllNpcs() {
        return Collections.unmodifiableCollection(npcs.values());
    }

    // ── Puzzles ─────────────────────────────────────────────────────────────

    public void registerPuzzle(Puzzle puzzle) {
        if (puzzle != null) puzzles.put(puzzle.getId(), puzzle);
    }

    public Optional<Puzzle> getPuzzle(String id) {
        return id == null ? Optional.empty() : Optional.ofNullable(puzzles.get(id));
    }

    public Collection<Puzzle> getAllPuzzles() {
        return Collections.unmodifiableCollection(puzzles.values());
    }

    // ── Encounters ───────────────────────────────────────────────────────────

    public void onEncounter(EncounterEvent event) {
        if (event == null) return;
        triggerManager.fire(event);
        for (Quest q : getActiveQuests()) q.onEncounter(event);
    }

    // ── Flags / Triggers ────────────────────────────────────────────────────

    public TriggerManager getTriggerManager() { return triggerManager; }

    public void setFlag(String flag)   { triggerManager.setFlag(flag); }
    public void clearFlag(String flag) { triggerManager.clearFlag(flag); }
    public boolean hasFlag(String flag){ return triggerManager.hasFlag(flag); }

    // ── Display ─────────────────────────────────────────────────────────────

    public void displayActiveQuests(java.util.function.Consumer<String> output) {
        List<Quest> active = getActiveQuests();
        if (active.isEmpty()) {
            output.accept("  No active quests.\n");
        } else {
            for (Quest q : active) {
                output.accept("  [QUEST] " + q.getTitle() + " - " + q.getDescription() + "\n");
            }
        }
    }
}
