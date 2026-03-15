// src/Spells/ArcaneMend.java
package Guild.ObsidianHexCoven.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;
import Status.DefenseUpStatus;
import Status.Status;

/**
 * ArcaneMend: a minor self-heal that also grants a short-lived defense buff to the caster.
 */
public class ArcaneMend implements Spell {
    private static final String NAME = "Arcane Mend";
    private static final String DESCRIPTION = "A small restorative weave that heals the caster and grants a brief defensive ward.";
    private static final int REQUIRED_MAGIC_POINTS = 8;
    private static final int DURATION = 4; // turns
    private static final int DEFENSE_BONUS = 6; // modest defense bonus
    private static final Guild SPELL_GUILD = Guild.OBSIDIAN_HEX_COVEN; // generic mage spell

    public ArcaneMend() { }

    @Override
    public boolean isGuildSpell() { return SPELL_GUILD != Guild.NON_GUILD; }

    @Override
    public Guild getSpellGuild() { return SPELL_GUILD; }

    @Override
    public int getRequiredMagicPoints() { return REQUIRED_MAGIC_POINTS; }

    @Override
    public String getName() { return NAME; }

    @Override
    public String getDescription() { return DESCRIPTION; }

    // Core behaviour: cast on caster (self-targeting)
    @Override
    public void cast(Charecter caster) {
        if (caster == null) caster = Charecter.getInstance();
        if (caster == null) {
            System.out.println("No caster available for Arcane Mend.");
            return;
        }

        if (caster.getMagicPoints() < REQUIRED_MAGIC_POINTS) {
            System.out.println(caster.getName() + " does not have enough magic points to cast " + NAME + "!");
            return;
        }

        caster.setMagicPoints(caster.getMagicPoints() - REQUIRED_MAGIC_POINTS);

        // Minor heal: scale with intelligence and wisdom but keep small
        int intel = Math.max(0, caster.getIntelligence());
        int wis = Math.max(0, caster.getWisdom());
        int heal = 3 + (intel / 4) + (wis / 6); // small predictable heal
        caster.restoreHitPoints(heal);
        System.out.println(caster.getName() + " casts " + NAME + " and restores " + heal + " HP.");

        // Apply DefenseUpStatus to caster via StatusManager if available
        if (caster.getStatusManager() != null) {
            Status def = new DefenseUpStatus(DURATION, DEFENSE_BONUS);
            try {
                caster.getStatusManager().addStatus(def, caster);
                System.out.println(caster.getName() + " is granted a defensive ward (+" + DEFENSE_BONUS + " defense for " + DURATION + " turns).");
            } catch (Exception e) {
                // Best-effort: if StatusManager API differs, try to apply directly on character
                try {
                    def.applyEffect(caster);
                    System.out.println(caster.getName() + " gains a fragile defensive ward.");
                } catch (Exception ignored) { }
            }
        } else {
            // Fallback: try to apply directly
            try {
                Status def = new DefenseUpStatus(DURATION, DEFENSE_BONUS);
                def.applyEffect(caster);
                System.out.println(caster.getName() + " gains a fragile defensive ward (StatusManager not available). ");
            } catch (Exception ignored) { }
        }
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        // Self-only spell: if a target is supplied, cast on caster instead (keeps backwards compatibility)
        cast(caster != null ? caster : target);
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        // Cast only affects caster; if null, use first in list
        if (caster == null && allCharacters != null && !allCharacters.isEmpty()) {
            caster = allCharacters.get(0);
        }
        cast(caster);
    }

    @Override
    public void cast() { cast(Charecter.getInstance()); }

    @Override
    public void cast(int toonWisdom) { cast(Charecter.getInstance()); }

    @Override
    public void castWithIntelligence(int toonIntelligence) { cast(Charecter.getInstance()); }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) { cast(Charecter.getInstance()); }

    @Override
    public void castWithStrength(Charecter enemy, double d) { /* Not applicable */ }

    @Override
    public void cast(Charecter caster, Enemies target) { cast(caster); }

    // Backwards compatibility: other Spell methods already implemented above.
}