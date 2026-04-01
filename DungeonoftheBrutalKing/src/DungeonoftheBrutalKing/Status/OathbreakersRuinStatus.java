package DungeonoftheBrutalKing.Status;

public class OathbreakersRuinStatus extends Status {

    private final int debuffPercent;

    public static final int DEFAULT_DEBUFF_PERCENT = 20;

    public OathbreakersRuinStatus(String name, int durationMinutes, int debuffPercent) {
        // Reuse your existing Status constructor:
        // Status(String name, int durationMinutes, StatusPolarity positive, StatusType type)
        super(
            name,
            durationMinutes,
            StatusPolarity.NEGATIVE,
            StatusType.OATHBREAKERS_RUIN
        );
        this.debuffPercent = debuffPercent;
    }

    public OathbreakersRuinStatus(int durationMinutes) {
        this("Oathbreaker's Ruin", durationMinutes, DEFAULT_DEBUFF_PERCENT);
    }

    public int getDebuffPercent() {
        return debuffPercent;
    }
}
