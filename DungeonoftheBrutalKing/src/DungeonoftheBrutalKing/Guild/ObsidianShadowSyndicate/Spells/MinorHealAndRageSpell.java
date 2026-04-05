package DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Status.DamageUpStatus;

public final class MinorHealAndRageSpell implements Spell {

    private static final String NAME = "MinorHealAndRage";
    private static final double HEAL_PERCENT = 0.10;

    // Duration of the DamageUpStatus in "minutes" (the unit used by Status)
    private static final int DAMAGE_UP_DURATION_MINUTES = 3;

    // Flat damage bonus applied by DamageUpStatus
    private static final int DAMAGE_UP_BONUS = 5;

    @Override
    public String getName() {
        return NAME;
    }

    // internal helper that returns healed amount
    private int doHealAndRage(Charecter caster) {
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

        // Create DamageUpStatus using the provided constructor
        DamageUpStatus damageUp =
                new DamageUpStatus(DAMAGE_UP_DURATION_MINUTES, DAMAGE_UP_BONUS);

        caster.addStatus(damageUp);

        return healed;
    }

    @Override
    public void cast(Charecter caster) {
        doHealAndRage(caster);
    }

    @Override
    public String getDescription() {
        return "Heals the caster for about 10% of their maximum health and grants Damage Up.";
    }

    @Override
    public boolean isGuildSpell() {
        return true;
    }

    @Override
    public Guild getSpellGuild() {
        return Guild.OBSIDIAN_SHADOW_SYNDICATE; // adjust to your actual enum value
    }

    @Override
    public int getRequiredMagicPoints() {
        return 3; // adjust cost as needed
    }

    @Override
    public void cast(int toonWisdom) {
        // optional: scale heal by wisdom
    }

    @Override
    public void castWithIntelligence(int toonIntelligence) {
        // optional: scale heal by intelligence
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        // optional: combined stats logic
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        // apply effect to caster only for now
        doHealAndRage(caster);
    }

    @Override
    public void cast() {
        // no-op or hook into a default caster if your engine supports it
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        // apply to target instead of caster
        doHealAndRage(target);
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) {
        // not used for this spell
    }

    @Override
    public void cast(Charecter caster, Enemies target) {
        // not used for this spell
    }
}
