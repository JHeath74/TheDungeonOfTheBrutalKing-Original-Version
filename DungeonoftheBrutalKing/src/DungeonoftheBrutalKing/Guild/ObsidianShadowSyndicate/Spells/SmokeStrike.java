package Guild.ObsidianShadowSyndicate.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;
import Status.EvasionUpStatus;
import Status.Status;

/**
 * SmokeStrike: a light damage strike that also grants a short-lived evasion buff to the caster.
 */
public final class SmokeStrike implements Spell {

    private static final String NAME = "Smoke Strike";
    private static final String DESCRIPTION =
            "A swift strike under cover of smoke that slightly harms the target and boosts the caster's evasion.";
    private static final int REQUIRED_MAGIC_POINTS = 6; // adjust as desired
    private static final double DAMAGE_PERCENT = 0.08;

    private static final int EVASION_UP_DURATION_MINUTES = 2;
    private static final int EVASION_UP_BONUS = 15; // e.g. +15% evasion

    // Guild allowed to cast this spell (same pattern as ArcaneMend)
    private static final Guild SPELL_GUILD = Guild.OBSIDIAN_SHADOW_SYNDICATE;

    public SmokeStrike() { }

    // --- Spell meta-data ---

    @Override
    public boolean isGuildSpell() {
        return SPELL_GUILD != Guild.NON_GUILD;
    }

    @Override
    public Guild getSpellGuild() {
        return SPELL_GUILD;
    }

    @Override
    public int getRequiredMagicPoints() {
        return REQUIRED_MAGIC_POINTS;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    // --- Core behaviour ---

    // Single\-target: caster vs target Charecter
    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster == null) caster = Charecter.getInstance();
        if (caster == null || target == null) return;

        // Optional: enforce guild by comparing the caster's guild (if you track it)
        // This assumes Charecter has getGuild() returning SharedData.Guild.
        try {
            Guild casterGuild = caster.getGuild();
            if (isGuildSpell() && casterGuild != SPELL_GUILD) {
                System.out.println(caster.getName() + " cannot cast " + NAME + " (wrong guild).");
                return;
            }
        } catch (Exception ignored) {
            // If Charecter has no getGuild(), skip hard enforcement.
        }

        if (caster.getMagicPoints() < REQUIRED_MAGIC_POINTS) {
            System.out.println(caster.getName() + " does not have enough magic points to cast " + NAME + "!");
            return;
        }

        caster.setMagicPoints(caster.getMagicPoints() - REQUIRED_MAGIC_POINTS);

        int casterMaxHp = Math.max(0, caster.getMaxHealth());
        int targetHp = Math.max(0, target.getHitPoints());

        int damage = (int) Math.round(casterMaxHp * DAMAGE_PERCENT);
        damage = Math.max(1, damage);

        int dealt = Math.min(damage, targetHp);
        target.setHitPoints(targetHp - dealt);

        System.out.println(caster.getName() + " strikes from the shadows with " + NAME +
                ", dealing " + dealt + " damage to " + target.getName() + ".");

        // Apply evasion buff to caster
        Status evasionUp = new EvasionUpStatus(EVASION_UP_DURATION_MINUTES, EVASION_UP_BONUS);
        try {
            if (caster.getStatusManager() != null) {
                caster.getStatusManager().addStatus(evasionUp, caster);
            } else {
                evasionUp.applyEffect(caster);
            }
            System.out.println(caster.getName() + " becomes harder to hit (+" +
                    EVASION_UP_BONUS + " evasion for " + EVASION_UP_DURATION_MINUTES + " minutes).");
        } catch (Exception ignored) { }
    }

    // --- Other Spell interface overloads (mirroring ArcaneMend for compatibility) ---

    @Override
    public void cast(Charecter caster) {
        // If only caster is provided, treat caster as both attacker and target (self\-buff, no damage),
        // or you can choose not to deal damage. Here we just buff.
        if (caster == null) caster = Charecter.getInstance();
        if (caster == null) return;

        // Reuse logic by targeting self but skipping damage:
        cast(caster, caster);
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        // If target list is provided, choose first enemy or first non\-caster as target; fallback to self
        if (caster == null && allCharacters != null && !allCharacters.isEmpty()) {
            caster = allCharacters.get(0);
        }
        if (allCharacters != null && allCharacters.size() > 1) {
            Charecter target = allCharacters.get(1);
            cast(caster, target);
        } else {
            cast(caster, caster);
        }
    }

    @Override
    public void cast() {
        cast(Charecter.getInstance(), Charecter.getInstance());
    }

    @Override
    public void cast(int toonWisdom) {
        cast();
    }

    @Override
    public void castWithIntelligence(int toonIntelligence) {
        cast();
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        cast();
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) {
        if (enemy == null) {
            cast();
        } else {
            cast(Charecter.getInstance(), enemy);
        }
    }

    @Override
    public void cast(Charecter caster, Enemies target) {
        // If Enemies\-based API is used, just treat it as a buff to caster
        cast(caster);
    }
}
