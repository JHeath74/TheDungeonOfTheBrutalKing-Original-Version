package DungeonoftheBrutalKing.Guild.SilverwardSentinels.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Status.PurityWardStatus;
import DungeonoftheBrutalKing.Status.Status;

/**
 * BlessingOfPurity: Paladin prayer that removes one negative status from the target
 * and grants a short Purity Ward that prevents new negative statuses while active.
 */
public final class BlessingOfPurity implements Spell {

    private static final String NAME = "Blessing of Purity";
    private static final int REQUIRED_MP = 8;
    private static final int WARD_DURATION_MINUTES = 3;

    @Override
    public String getName() { return NAME; }

    @Override
    public boolean isGuildSpell() { return true; }

    @Override
    public Guild getSpellGuild() { return Guild.SILVERWARD_SENTINELS; }

    @Override
    public int getRequiredMagicPoints() { return REQUIRED_MP; }

    @Override
    public String getDescription() { return "Removes a single negative status from the target and grants a Purity Ward that blocks new negative effects for a short time."; }

    private void applyBlessing(Charecter caster, Charecter target) {
        if (caster == null) {
            System.out.println("No caster specified for " + NAME + ". Casting aborted.");
            return;
        }

        // Guild check: caster must belong to Silverward Sentinels
        Guild casterGuild = caster.getGuild();
        if (casterGuild == null || casterGuild != getSpellGuild()) {
            try { System.out.println(caster.getName() + " is not a member of " + getSpellGuild() + " and cannot cast " + NAME + "."); } catch (Exception ignored) { }
            return;
        }

        // MP check on caster
        if (caster.getMagicPoints() < REQUIRED_MP) {
            System.out.println(caster.getName() + " lacks the magic points to cast " + NAME + "!");
            return;
        }

        // Deduct MP
        caster.setMagicPoints(Math.max(0, caster.getMagicPoints() - REQUIRED_MP));

        if (target == null) target = caster;

        // Remove one negative status if present.
        try {
            java.util.List<Status> statuses = target.getStatuses();
            if (statuses != null) {
                Status toRemove = null;
                for (Status s : statuses) {
                    if (s != null && s.isNegative()) { toRemove = s; break; }
                }
                if (toRemove != null) {
                    String name = toRemove.getName();
                    // Prefer StatusManager removal API if available
                    try {
                        if (target.getStatusManager() != null) {
                            target.getStatusManager().removeStatusByName(name, target);
                        }
                    } catch (Exception ignored) { }
                    // Also attempt to remove via Charecter helper
                    try { target.removeStatusEffect(toRemove.getType()); } catch (Exception ignored) { }

                    System.out.println(target.getName() + " is cleansed of " + name + " by " + NAME + ".");
                } else {
                    System.out.println(target.getName() + " has no negative statuses to remove.");
                }
            }
        } catch (Exception ignored) { }

        // Apply Purity Ward to prevent new negative statuses
        try {
            PurityWardStatus ward = new PurityWardStatus(WARD_DURATION_MINUTES);
            if (target.getStatusManager() != null) {
                target.getStatusManager().addStatus(ward, target);
                ward.applyEffect(target);
            } else {
                // best-effort: add to character local statuses list
                target.addStatus(ward);
                ward.applyEffect(target);
            }
            System.out.println(target.getName() + " is granted a Purity Ward for " + WARD_DURATION_MINUTES + " minutes.");
        } catch (Exception ignored) { }
    }

    @Override
    public void cast(Charecter caster) { applyBlessing(caster, caster); }

    @Override
    public void cast(Charecter caster, Charecter target) { applyBlessing(caster, target != null ? target : caster); }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        if (allCharacters != null) {
            for (Charecter ch : allCharacters) applyBlessing(caster, ch);
        }
    }

    @Override public void cast() {}
    @Override public void cast(int toonWisdom) {}
    @Override public void castWithIntelligence(int toonIntelligence) {}
    @Override public void cast(int toonWisdom, int toonIntelligence) {}
    @Override public void castWithStrength(Charecter enemy, double d) {}
    @Override public void cast(Charecter caster, Enemies target) {}
}