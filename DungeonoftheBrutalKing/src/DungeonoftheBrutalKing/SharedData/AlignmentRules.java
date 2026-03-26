package SharedData;

public final class AlignmentRules {
    private AlignmentRules() {}

    public static final int MIN = -1000;
    public static final int MAX = 1000;

    // Only GOOD or EVIL: 0 and above is GOOD
    public static boolean isGood(int alignmentValue) {
        return alignmentValue >= 0;
    }

    public static boolean isEvil(int alignmentValue) {
        return alignmentValue < 0;
    }

    public static int clamp(int alignmentValue) {
        if (alignmentValue < MIN) return MIN;
        if (alignmentValue > MAX) return MAX;
        return alignmentValue;
    }

    public static int applyDelta(int currentAlignment, int delta) {
        return clamp(currentAlignment + delta);
    }
}
