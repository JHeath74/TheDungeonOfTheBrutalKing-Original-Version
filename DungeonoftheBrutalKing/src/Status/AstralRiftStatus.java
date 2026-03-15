package Status;

import java.lang.reflect.Field;

import DungeonoftheBrutalKing.Charecter;

/**
 * AstralRiftStatus: applied by the Astral Rift spell. Deals periodic arcane damage
 * and temporarily reduces the target's spell resistance bonus so they become
 * more vulnerable to follow-up magic.
 */
public class AstralRiftStatus extends Status {
    private static final int DEFAULT_DURATION_MINUTES = 2; // short-lived
    private static final int DEFAULT_ARCANE_DAMAGE = 6; // per application

    private final int damagePerTick;
    private final int resistanceReduction;
    private Integer originalResistance = null;

    public AstralRiftStatus() {
        this(DEFAULT_DURATION_MINUTES, DEFAULT_ARCANE_DAMAGE, 5);
    }

    public AstralRiftStatus(int durationMinutes, int damagePerTick, int resistanceReduction) {
        super("Astral Rift", Math.max(0, durationMinutes), true, StatusType.VOID_ECHO_STATUS); // negative effect, reuse VOID_ECHO_STATUS as a close type
        this.damagePerTick = Math.max(0, damagePerTick);
        this.resistanceReduction = resistanceReduction;
    }

    @Override
    public void applyEffect(Charecter character) {
        if (character == null) return;

        // Apply periodic arcane damage immediately (this project often deals damage in applyEffect)
        try {
            character.takeDamage(damagePerTick);
        } catch (Exception ignored) { }

        // On first application, reduce spell resistance if possible and remember original value
        if (originalResistance == null) {
            // Try getter/setter first
            try {
                java.lang.reflect.Method getter = character.getClass().getMethod("getSpellResistanceBonus");
                java.lang.reflect.Method setter = character.getClass().getMethod("setSpellResistanceBonus", int.class);
                Object val = getter.invoke(character);
                int cur = (val instanceof Number) ? ((Number) val).intValue() : 0;
                originalResistance = cur;
                int newVal = Math.max(0, cur - resistanceReduction);
                setter.invoke(character, newVal);
                return;
            } catch (Exception ignored) { }

            // Fallback: access private field directly
            try {
                Field f = character.getClass().getDeclaredField("spellResistanceBonus");
                f.setAccessible(true);
                int cur = f.getInt(character);
                originalResistance = cur;
                int newVal = Math.max(0, cur - resistanceReduction);
                f.setInt(character, newVal);
            } catch (Exception ignored) { }
        }
    }

    @Override
    public void expireEffect(Charecter character) {
        // restore when expired
        restoreResistance(character);
    }

    @Override
    public void removeEffect(Charecter character) {
        // restore when explicitly removed
        restoreResistance(character);
    }

    private void restoreResistance(Charecter character) {
        if (character == null || originalResistance == null) return;
        try {
            java.lang.reflect.Method setter = character.getClass().getMethod("setSpellResistanceBonus", int.class);
            setter.invoke(character, originalResistance.intValue());
            originalResistance = null;
            return;
        } catch (Exception ignored) { }

        try {
            Field f = character.getClass().getDeclaredField("spellResistanceBonus");
            f.setAccessible(true);
            f.setInt(character, originalResistance.intValue());
            originalResistance = null;
        } catch (Exception ignored) { }
    }

    @Override
    public String getDescription() {
        return "Smeared by arcane gravity: takes arcane damage over time and has reduced spell resistance.";
    }
}
