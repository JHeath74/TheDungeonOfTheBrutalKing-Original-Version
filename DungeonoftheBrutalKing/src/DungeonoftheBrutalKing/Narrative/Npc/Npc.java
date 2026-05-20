
package DungeonoftheBrutalKing.Narrative.Npc;

import DungeonoftheBrutalKing.Narrative.Api.NarrativeEntity;
import DungeonoftheBrutalKing.Narrative.Encounters.EncounterEvent;
import DungeonoftheBrutalKing.Narrative.Encounters.EncounterType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Npc implements NarrativeEntity {

    private final String id;
    private final String name;
    private final String description;
    private final NpcRole role;
    private final List<DialogLine> dialog;
    private int dialogIndex = 0;
    private boolean interacted = false;

    public Npc(String id, String name, String description, NpcRole role) {
        this.id          = Objects.requireNonNull(id);
        this.name        = Objects.requireNonNull(name);
        this.description = description != null ? description : "";
        this.role        = role != null ? role : NpcRole.NEUTRAL;
        this.dialog      = new ArrayList<>();
    }

    public void addDialog(DialogLine line) {
        if (line != null) dialog.add(line);
    }

    public void addDialog(String speaker, String text) {
        dialog.add(new DialogLine(speaker, text));
    }

    /** Returns next dialog line, cycling through all lines. */
    public DialogLine interact() {
        interacted = true;
        if (dialog.isEmpty()) return new DialogLine(name, "...");
        DialogLine line = dialog.get(dialogIndex);
        dialogIndex = (dialogIndex + 1) % dialog.size();
        return line;
    }

    public EncounterEvent toEncounterEvent() {
        return EncounterEvent.of(EncounterType.NPC_INTERACT, id);
    }

    @Override public String getId()          { return id; }
    @Override public String getName()        { return name; }
    @Override public String getDescription() { return description; }

    public NpcRole getRole()           { return role; }
    public boolean hasInteracted()     { return interacted; }
    public List<DialogLine> getDialog(){ return Collections.unmodifiableList(dialog); }
}
