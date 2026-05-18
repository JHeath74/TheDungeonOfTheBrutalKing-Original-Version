
// src/DungeonoftheBrutalKing/Narrative/api/QuestHooks.java
package DungeonoftheBrutalKing.Narrative.api;

/**
 * Lifecycle callbacks attached to a quest.
 * Assign via QuestImpl.setHooks() or in content quest constructors.
 */
public final class QuestHooks {

    private final Runnable onStart;
    private final Runnable onComplete;
    private final Runnable onFail;

    public QuestHooks(Runnable onStart, Runnable onComplete, Runnable onFail) {
        this.onStart    = onStart    != null ? onStart    : () -> {};
        this.onComplete = onComplete != null ? onComplete : () -> {};
        this.onFail     = onFail     != null ? onFail     : () -> {};
    }

    public void fireOnStart()    { onStart.run(); }
    public void fireOnComplete() { onComplete.run(); }
    public void fireOnFail()     { onFail.run(); }

    /** Convenience builder */
    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private Runnable onStart, onComplete, onFail;
        public Builder onStart(Runnable r)    { this.onStart = r;    return this; }
        public Builder onComplete(Runnable r) { this.onComplete = r; return this; }
        public Builder onFail(Runnable r)     { this.onFail = r;     return this; }
        public QuestHooks build() { return new QuestHooks(onStart, onComplete, onFail); }
    }
}
