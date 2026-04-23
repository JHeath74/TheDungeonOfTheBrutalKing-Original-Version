package DungeonoftheBrutalKing.Guild.SilverwardSentinels.Spells;

import java.util.Iterator;
import java.util.List;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Status.Status;
import DungeonoftheBrutalKing.Status.StatusPolarity;
import DungeonoftheBrutalKing.Status.StatusType;

/**
 * Sanctified Purge - Silverward Sentinels / Paladin dispel.
 * Removes negative status effects from a single ally (or self),
 * only if the caster belongs to the Silverward Sentinels guild.
 */
public final class SanctifiedPurge implements Spell {

    private static final String NAME = "AureateCleansing";

    // MP cost for this dispel
    private static final int REQUIRED_MP = 4;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isGuildSpell() {
        return true;
    }

    @Override
    public Guild getSpellGuild() {
        // adjust constant to whatever you use for Silverward Sentinels
        return Guild.SILVERWARD_SENTINELS;
    }

    @Override
    public int getRequiredMagicPoints() {
        return REQUIRED_MP;
    }

    @Override
    public String getDescription() {
        return "A holy Silverward Sentinel rite that removes harmful status effects "
             + "from a single ally, provided the caster is of the same guild.";
    }

    /**
     * Core cleansing logic with guild check.
     */
    private void applyCleansing(Character caster, Character target) {
        if (target == null) {
            return;
        }

        // guild check: only members of this spell's guild may cast it
        if (caster != null) {
            Guild casterGuild = caster.getGuild(); // adjust getter name if different
            if (casterGuild == null || casterGuild != getSpellGuild()) {
                // not the right guild -> do nothing
                return;
            }
        }

        // remove negative / debuff statuses from target
        List<Status> statuses = target.getStatuses();
        if (statuses == null || statuses.isEmpty()) {
            return;
        }

        // First, determine which StatusType values should be removed
        // based on current statuses and polarity, then call
        // target.removeStatusEffect(type) for each.
        // This lets the Charecter / StatusManager handle all cleanup.
        for (Status status : List.copyOf(statuses)) {
            if (status == null) {
                continue;
            }
            if (!isNegativeStatus(status)) {
                continue;
            }
            StatusType type = status.getType();
            if (type == null) {
                continue;
            }
            target.removeStatusEffect(type);
        }

        // Finally, clean the local list to ensure no stale entries remain.
        Iterator<Status> it = statuses.iterator();
        while (it.hasNext()) {
            Status s = it.next();
            if (isNegativeStatus(s)) {
                it.remove();
            }
        }
    }

    /**
     * Helper predicate using StatusPolarity.
     * Treats only NEGATIVE polarity as removable by this spell.
     */
    private boolean isNegativeStatus(Status status) {
        if (status == null) {
            return false;
        }

        StatusPolarity polarity = status.getPolarity();
        return polarity == StatusPolarity.POSITIVE;
    }

    // --- Spell interface implementations ---

    @Override
    public void cast() {
        // no-op; engine likely uses other overloads
    }

    @Override
    public void cast(int toonWisdom) {
        // not used: no global caster/target in this overload
    }

    @Override
    public void castWithIntelligence(int toonIntelligence) {
        // not used for this spell
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        // not used for this spell
    }

    @Override
    public void cast(Character caster) {
        // self-cleanse
        applyCleansing(caster, caster);
    }

    @Override
    public void cast(Character caster, Character target) {
        // cleanse target (ally or self)
        applyCleansing(caster, target);
    }

    @Override
    public void cast(Character caster, java.util.List<Character> allCharacters) {
        // simple behavior: cleanse caster only
        applyCleansing(caster, caster);
    }

    @Override
    public void castWithStrength(Character enemy, double d) {
        // not meaningful for a dispel; no-op
    }

    @Override
    public void cast(Character caster, Enemies target) {
        // dispel does not affect enemies here; no-op
    }
}
