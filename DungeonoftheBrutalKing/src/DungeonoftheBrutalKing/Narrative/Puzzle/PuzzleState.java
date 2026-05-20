
package DungeonoftheBrutalKing.Narrative.Puzzle;

public enum PuzzleState {
    UNSOLVED,
    SOLVED,
    FAILED,
    LOCKED   // requires prerequisite before it can be attempted
}
