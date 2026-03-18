
// src/Spell/GreaterHealSpell.java
package Guild.ObsidianShadowSyndicate.Spells;

import DungeonoftheBrutalKing.Charecter;

public final class GreaterHealSpell {

    private static final String NAME = "GreaterHeal";
    private static final double HEAL_PERCENT = 0.20;

    public String getName() {
        return NAME;
    }

    public int cast(Charecter caster) {
        if (caster == null) return 0;

        int maxHp = Math.max(0, caster.getMaxHealth());
        int currentHp = Math.max(0, caster.getHitPoints());
        if (currentHp >= maxHp) return 0;

        int amount = (int) Math.round(maxHp * HEAL_PERCENT);
        amount = Math.max(1, amount);

        int healed = Math.min(amount, maxHp - currentHp);
        caster.setHitPoints(currentHp + healed);
        return healed;
    }

    public String getDescription() {
        return "Heals the caster for about 20\\% of their maximum health\\.";
    }
}
