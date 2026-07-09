
package DungeonoftheBrutalKing.Narrative.Puzzle;

import DungeonoftheBrutalKing.Narrative.Api.NarrativeEntity;
import DungeonoftheBrutalKing.Narrative.Encounters.EncounterEvent;
import DungeonoftheBrutalKing.Narrative.Encounters.EncounterType;

import java.util.Objects;

public abstract class Puzzle implements NarrativeEntity {

    private final String id;
    private final String name;
    private final String description;
    private final String hint;
    private PuzzleState state = PuzzleState.UNSOLVED;

    protected Puzzle(String id, String name, String description, String hint) {
        this.id          = Objects.requireNonNull(id);
        this.name        = Objects.requireNonNull(name);
        this.description = description != null ? description : "";
        this.hint        = hint != null ? hint : "";
    }

    /**
     * Subclasses implement the actual answer-checking logic.
     * @param answer player's input
     * @return true if the puzzle is solved
     */
    protected abstract boolean checkAnswer(String answer);

    /** Called by the game when the player submits an answer. */
    public final EncounterEvent attempt(String answer) {
        if (state != PuzzleState.UNSOLVED) return null;
        if (checkAnswer(answer)) {
            state = PuzzleState.SOLVED;
            return EncounterEvent.of(EncounterType.PUZZLE_SOLVED, id);
        } else {
            state = PuzzleState.FAILED;
            return EncounterEvent.of(EncounterType.PUZZLE_FAILED, id);
        }
    }

    public void reset()  { state = PuzzleState.UNSOLVED; }
    public void lock()   { state = PuzzleState.LOCKED; }
    public void unlock() { if (state == PuzzleState.LOCKED) state = PuzzleState.UNSOLVED; }

    @Override public String getId()          { return id; }
    @Override public String getName()        { return name; }
    @Override public String getDescription() { return description; }

    public String getHint()        { return hint; }
    public PuzzleState getState()  { return state; }
    public boolean isSolved()      { return state == PuzzleState.SOLVED; }
}
