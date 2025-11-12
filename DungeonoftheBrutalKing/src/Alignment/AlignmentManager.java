
// src/Game/AlignmentManager.java
package Alignment;

public class AlignmentManager {
    private int alignmentScore = 0;

    public void addAlignment(int value) {
        alignmentScore += value;
    }

    public Alignment getAlignment() {
        return alignmentScore >= 0 ? Alignment.POSITIVE : Alignment.NEGATIVE;
    }

    public int getScore() {
        return alignmentScore;
    }
}
