package Status;

import DungeonoftheBrutalKing.Charecter;

/**
 * IceBarrierStatus: grants a temporary defensive barrier that absorbs a portion
 * of incoming damage and applies a slowing effect to melee attackers that strike
 * the bearer. When the barrier expires or is removed it shatters, releasing a
 * burst of cold described in messages (and optionally integrating with an
 * enemy API to freeze weaker foes).
 */
public class IceBarrierStatus extends Status {
    private static final int DEFAULT_DURATION_MINUTES = 2; // tuned shorter
    private static final int DEFAULT_DEFENSE_BONUS = 10; // tuned
    private static final double DEFAULT_ABSORB_PERCENT = 0.40; // 40% damage absorbed (tuned)
    private static final int DEFAULT_SLOW_DURATION = 3; // turns (tuned)
    private static final int DEFAULT_FREEZE_CHANCE_PERCENT = 45; // chance to freeze weak foes on shatter (tuned)

    private final int defenseBonus;
    private final double absorbPercent;
    private final int slowDuration;
    private final int freezeChancePercent;

    private Integer originalDefense = null;

    public IceBarrierStatus() {
        this(DEFAULT_DURATION_MINUTES, DEFAULT_DEFENSE_BONUS, DEFAULT_ABSORB_PERCENT, DEFAULT_SLOW_DURATION, DEFAULT_FREEZE_CHANCE_PERCENT);
    }

    public IceBarrierStatus(int durationMinutes, int defenseBonus, double absorbPercent, int slowDuration, int freezeChancePercent) {
        super("Ice Barrier", Math.max(0, durationMinutes), true, StatusType.ICE_STATUS);
        this.defenseBonus = Math.max(0, defenseBonus);
        this.absorbPercent = Math.max(0.0, Math.min(1.0, absorbPercent));
        this.slowDuration = Math.max(0, slowDuration);
        this.freezeChancePercent = Math.max(0, Math.min(100, freezeChancePercent));
    }

    // Expose important tuning values so combat code can react when a bearer is struck
    public int getSlowDuration() { return slowDuration; }
    public double getAbsorbPercent() { return absorbPercent; }
    public int getFreezeChancePercent() { return freezeChancePercent; }
    public int getDefenseBonus() { return defenseBonus; }

    @Override
    public void applyEffect(Charecter character) {
        if (character == null) return;
        try {
            originalDefense = character.getDefense();
            character.setDefense(originalDefense + defenseBonus);
            System.out.println(character.getName() + " is surrounded by a shimmering barrier of enchanted ice (" + defenseBonus + " defense, absorbs " + (int)(absorbPercent*100) + "% damage). ");
        } catch (Exception ignored) { }
    }

    @Override
    public void expireEffect(Charecter character) {
        // On expiry the barrier shatters releasing a burst of cold.
        shatterBurst(character);
        restoreDefense(character);
    }

    @Override
    public void removeEffect(Charecter character) {
        // If removed prematurely, also shatter and restore.
        shatterBurst(character);
        restoreDefense(character);
    }

    private void restoreDefense(Charecter character) {
        if (character == null || originalDefense == null) return;
        try {
            character.setDefense(originalDefense);
            originalDefense = null;
        } catch (Exception ignored) { }
    }

    private void shatterBurst(Charecter character) {
        if (character == null) return;
        try {
            System.out.println("The Ice Barrier around " + character.getName() + " shatters, releasing a burst of freezing air!");

            // Best-effort: if there is a Combat or nearby-enemy API we could freeze weaker foes here.
            // Try to integrate with a common Enemies or Combat class if present (best-effort reflection).
            try {
                Class<?> combatClass = Class.forName("DungeonoftheBrutalKing.Combat");
                try {
                    java.lang.reflect.Method getNearby = combatClass.getMethod("getNearbyEnemies", Charecter.class);
                    Object result = getNearby.invoke(null, character);
                    if (result instanceof java.util.List<?> list) {
                        for (Object o : list) {
                            if (o instanceof DungeonoftheBrutalKing.Enemies) {
                                // cannot reliably apply to Enemies without knowing API; skip
                            }
                            if (o instanceof DungeonoftheBrutalKing.Charecter) {
                                Charecter ch = (Charecter) o;
                                // simple heuristic: freeze if their hitpoints are lower than a threshold
                                int threshold = Math.max(1, ch.getMaxHitPoints() / 4);
                                if (ch.getHitPoints() <= threshold) {
                                    ch.addStatus(new ImmobilizedStatus(Math.max(1, slowDuration)));
                                    System.out.println(ch.getName() + " is frozen in place by the cold burst!");
                                }
                            }
                        }
                    }
                } catch (NoSuchMethodException ignored) { }
            } catch (ClassNotFoundException ignored) { }

        } catch (Exception ignored) { }
    }

    @Override
    public double damageTakenMultiplier() {
        // Reduce damage taken by the absorb percent (e.g., 0.7 if absorbPercent=0.30)
        return Math.max(0.0, 1.0 - absorbPercent);
    }

    @Override
    public String getDescription() {
        return "A shimmering barrier of enchanted ice that absorbs a portion of incoming damage and slows melee attackers; shatters into a freezing burst when it ends.";
    }
}