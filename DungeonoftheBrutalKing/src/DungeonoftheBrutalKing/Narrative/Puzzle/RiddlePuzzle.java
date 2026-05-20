
package DungeonoftheBrutalKing.Narrative.Puzzle;

import java.util.Objects;

/** A simple riddle where the player must type the correct answer. */
public class RiddlePuzzle extends Puzzle {

    private final String answer;

    public RiddlePuzzle(String id, String name, String riddle, String hint, String answer) {
        super(id, name, riddle, hint);
        this.answer = Objects.requireNonNull(answer).trim().toLowerCase();
    }

    @Override
    protected boolean checkAnswer(String input) {
        return input != null && input.trim().equalsIgnoreCase(answer);
    }
}
