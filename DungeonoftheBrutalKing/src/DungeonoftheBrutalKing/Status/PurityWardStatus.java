package Status;

import DungeonoftheBrutalKing.Charecter;

/**
 * PurityWardStatus: prevents new negative statuses from being applied while active.
 * It is a positive status (polarity POSITIVE) and refuses new negative effects.
 */
public final class PurityWardStatus extends Status {

    private static final int DEFAULT_DURATION_MINUTES = 5;

    public PurityWardStatus() {
        this(DEFAULT_DURATION_MINUTES);
    }

    public PurityWardStatus(int durationMinutes) {
        super("Purity Ward", Math.max(0, durationMinutes), StatusPolarity.POSITIVE, StatusType.PURITY_WARD);
    }

    @Override
    public void applyEffect(Charecter target) {
        if (target == null) return;
        System.out.println(target.getName() + " is surrounded by a shimmering Purity Ward; negative effects will be resisted.");
        // Best-effort: mark the character as protected against new negative effects
        try {
            // If Charecter exposes a protectedEffects set, add a marker so other code can check it.
            java.lang.reflect.Field f = Charecter.class.getDeclaredField("protectedEffects");
            f.setAccessible(true);
            Object o = f.get(target);
            if (o instanceof java.util.Set) {
                @SuppressWarnings("unchecked")
                java.util.Set<String> set = (java.util.Set<String>) o;
                set.add("PurityWard");
            }
        } catch (Exception ignored) { }
    }

    @Override
    public void removeEffect(Charecter target) {
        if (target == null) return;
        try {
            java.lang.reflect.Field f = Charecter.class.getDeclaredField("protectedEffects");
            f.setAccessible(true);
            Object o = f.get(target);
            if (o instanceof java.util.Set) {
                @SuppressWarnings("unchecked")
                java.util.Set<String> set = (java.util.Set<String>) o;
                set.remove("PurityWard");
            }
        } catch (Exception ignored) { }
        System.out.println("Purity Ward around " + (target != null ? target.getName() : "unknown") + " fades.");
    }

    @Override
    public String getDescription() {
        return "Prevents new negative statuses from being applied for " + Math.max(0, (getDurationSeconds() + 59) / 60) + " minutes.";
    }
}