
package DungeonoftheBrutalKing.Narrative.Npc;

import java.util.Objects;

public final class DialogLine {
    private final String speaker;
    private final String text;
    private final String triggerFlag; // optional flag set when this line plays

    public DialogLine(String speaker, String text) {
        this(speaker, text, null);
    }

    public DialogLine(String speaker, String text, String triggerFlag) {
        this.speaker     = Objects.requireNonNull(speaker);
        this.text        = Objects.requireNonNull(text);
        this.triggerFlag = triggerFlag;
    }

    public String getSpeaker()     { return speaker; }
    public String getText()        { return text; }
    public String getTriggerFlag() { return triggerFlag; }
    public boolean hasTrigger()    { return triggerFlag != null && !triggerFlag.isEmpty(); }
}
