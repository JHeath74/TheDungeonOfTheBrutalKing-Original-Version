
// src/Guild/ObsidianShadowSyndicate/Spells/MinorHealAndRageSpell.java
package Guild.ObsidianShadowSyndicate.Spells;

import DungeonoftheBrutalKing.Charecter;
import Status.DamageUpStatus;

public final class MinorHealAndRageSpell {

    private static final String NAME = "MinorHealAndRage";
    private static final double HEAL_PERCENT = 0.10;

    private static final int DAMAGE_UP_DURATION_TURNS = 3;

    public String getName() {
        return NAME;
    }

    public int cast(Charecter caster) {
        if (caster == null) return 0;

        int maxHp = Math.max(0, caster.getMaxHealth());
        int currentHp = Math.max(0, caster.getHitPoints());

        int amount = (int) Math.round(maxHp * HEAL_PERCENT);
        amount = Math.max(1, amount);

        int healed = 0;
        if (currentHp < maxHp) {
            healed = Math.min(amount, maxHp - currentHp);
            caster.setHitPoints(currentHp + healed);
        }

        // Apply DamageUpStatus after healing (use the constructor that exists).
        DamageUpStatus damageUp = new DamageUpStatus(durationMinutes, bonusDamage);

        // If your Status base class exposes a duration setter, use it here.
        // Uncomment the correct line that matches your API:
        // damageUp.setDuration(DAMAGE_UP_DURATION_TURNS);
        // damageUp.setDurationTurns(DAMAGE_UP_DURATION_TURNS);

        caster.addStatus(damageUp);

        return healed;
    }

    public String getDescription() {
        return "Heals the caster for about 10\\% of their maximum health and grants Damage Up\\.";
    }
}
