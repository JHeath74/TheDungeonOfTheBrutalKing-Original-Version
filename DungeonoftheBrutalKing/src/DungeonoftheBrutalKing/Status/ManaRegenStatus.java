
package DungeonoftheBrutalKing.Status;

import DungeonoftheBrutalKing.Character;

import java.lang.reflect.Method;

public final class ManaRegenStatus extends Status {
    private final int regenAmount;

    public ManaRegenStatus(int durationMinutes, int regenAmount) {
        super("Mana Regeneration", durationMinutes, StatusPolarity.POSITIVE, StatusType.MANA_REGEN_STATUS);
        this.regenAmount = Math.max(0, regenAmount);
    }

    @Override
    public void applyEffect(Character target) {
        if (target == null || regenAmount == 0) return;
        adjustManaRegenBonus(target, regenAmount);
    }

    @Override
    public void removeEffect(Character target) {
        if (target == null || regenAmount == 0) return;
        adjustManaRegenBonus(target, -regenAmount);
    }

    @Override
    public String getDescription() {
        return "Increases mana regeneration by " + regenAmount + " per turn.";
    }

    private static void adjustManaRegenBonus(Character target, int delta) {
        Integer current = tryGetInt(target, "getManaRegenBonus");
        if (current == null) current = tryGetInt(target, "getManaRegen");

        if (current == null) return; // Character has no supported mana regen stat API.

        boolean set =
                trySetInt(target, "setManaRegenBonus", current + delta) ||
                trySetInt(target, "setManaRegen", current + delta);

        if (!set) {
            // No-op if setter does not exist.
        }
    }

    private static Integer tryGetInt(Object target, String getterName) {
        try {
            Method m = target.getClass().getMethod(getterName);
            Object v = m.invoke(target);
            if (v instanceof Integer) return (Integer) v;
        } catch (Exception ignored) {
        }
        return null;
    }

    private static boolean trySetInt(Object target, String setterName, int value) {
        try {
            Method m = target.getClass().getMethod(setterName, int.class);
            m.invoke(target, value);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}
