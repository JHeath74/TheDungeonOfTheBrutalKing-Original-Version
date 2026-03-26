
package Status;

import DungeonoftheBrutalKing.Charecter;

/**
 * Judgement Brand status:
 * \- Negative mark that makes the target take increased damage.
 * \- Applied by the Judgement Brand spell.
 */
public class JudgementBrandStatus extends Status {

    // Example: target takes +25% damage from all sources
    public static final int DEFAULT_DAMAGE_INCREASE_PERCENT = 25;

    // Stored intensity of the brand (percent damage increase)
    private final int damageIncreasePercent;

    public JudgementBrandStatus(int durationMinutes, int value, Charecter source) {
        // Matches Status(String name, int durationMinutes, StatusPolarity positive, StatusType type)
        super(
            "Judgement Brand",
            durationMinutes,
            StatusPolarity.NEGATIVE,
            StatusType.JUDGEMENT_BRAND
        );
        this.damageIncreasePercent = value;
        // If Status has a way to store the source, set it there separately (not shown in your ctor)
        // e.g. setSource(source); \- adapt to your actual API.
    }

    public JudgementBrandStatus(int durationMinutes, Charecter source) {
        this(durationMinutes, DEFAULT_DAMAGE_INCREASE_PERCENT, source);
    }

    /**
     * Returns the damage multiplier based on this brand.
     * For example, 25 \-> 1.25x damage.
     */
    public double getDamageMultiplier() {
        return 1.0 + (damageIncreasePercent / 100.0);
    }

    public int getDamageIncreasePercent() {
        return damageIncreasePercent;
    }
}
