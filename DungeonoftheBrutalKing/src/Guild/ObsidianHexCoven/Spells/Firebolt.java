// src/Guild/ObsidianHexCoven/Spells/Firebolt.java
package Guild.ObsidianHexCoven.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;
import Status.FireStatus;
import SharedData.RandomFactory;

/**
 * Firebolt: a quick bolt of flame that deals fire damage and may apply Burned (FireStatus).
 */
public class Firebolt implements Spell {

    private static final int MINIMUM_INTELLIGENCE = 8;
    private static final int REQUIRED_MAGIC_POINTS = 6;
    private static final Guild SPELL_GUILD = Guild.OBSIDIAN_HEX_COVEN;

    // Chance to apply Burned status on hit.
    private static final double FIRE_STATUS_CHANCE = 0.25;

    @Override
    public String getName() {
        return "Firebolt";
    }

    @Override
    public void cast() {
        cast(MINIMUM_INTELLIGENCE);
    }

    @Override
    public void cast(int attackerWisdom) {
        // For intelligence-based mages we interpret this parameter as the relevant stat.
        if (attackerWisdom < MINIMUM_INTELLIGENCE) {
            System.out.println("You lack the necessary Intelligence to cast Firebolt!");
            return;
        }

        int damage = rollDamage(attackerWisdom);
        System.out.println("Firebolt deals " + damage + " fire damage!");
    }

    @Override
    public void cast(Charecter caster) {
        if (caster == null) {
            cast();
            return;
        }
        cast(Math.max(0, caster.getIntelligence()));
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster == null) {
            cast();
            return;
        }
        if (target == null) {
            cast(caster);
            return;
        }

        int intelligence = Math.max(0, caster.getIntelligence());
        if (intelligence < MINIMUM_INTELLIGENCE) {
            System.out.println("You lack the necessary Intelligence to cast Firebolt!");
            return;
        }

        int damage = rollDamage(intelligence);
        target.setHitPoints(Math.max(0, target.getHitPoints() - damage));
        System.out.println("Firebolt hits " + safeName(target) + " for " + damage + " fire damage!");

        maybeApplyFireStatus(target);
    }

    @Override
    public void cast(Charecter caster, List<Charecter> targets) {
        if (targets == null || targets.isEmpty()) {
            cast(caster);
            return;
        }
        for (Charecter target : targets) {
            cast(caster, target);
        }
    }

    @Override
    public void castWithIntelligence(int intelligence) {
        if (intelligence < MINIMUM_INTELLIGENCE) {
            System.out.println("You lack the necessary Intelligence to cast Firebolt!");
            return;
        }
        int damage = rollDamage(intelligence);
        System.out.println("Firebolt (Intelligence) deals " + damage + " fire damage!");
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        int base = (Math.max(0, toonWisdom) + Math.max(0, toonIntelligence)) / 2;
        if (base < MINIMUM_INTELLIGENCE) {
            System.out.println("You lack the necessary mental acuity to cast Firebolt!");
            return;
        }
        int damage = rollDamage(base);
        System.out.println("Firebolt (Wisdom & Intelligence) deals " + damage + " fire damage!");
    }

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
    public String getDescription() {
        return "A quick bolt of flame that deals fire damage and may set the target ablaze.";
    }

    @Override
    public void castWithStrength(Charecter enemy, double strengthScalar) {
        int base = (enemy != null) ? Math.max(0, enemy.getStrength()) : 0;
        int scaled = (int) Math.round(base * Math.max(0.0, strengthScalar));
        int damage = rollDamage(scaled);
        System.out.println("Firebolt (Strength) deals " + damage + " fire damage!");
    }

    @Override
    public void cast(Charecter caster, Enemies target) {
        // If Enemies has its own status system, wire it here (API unknown).
        cast(caster);
    }

    private static int rollDamage(int stat) {
        int bound = Math.max(1, stat);
        return RandomFactory.gameplayInt(bound) + 1;
    }

    private static void maybeApplyFireStatus(Charecter target) {
        if (target == null) return;

        if (RandomFactory.gameplayDouble() <= FIRE_STATUS_CHANCE) {
            try {
                target.addStatus(new FireStatus());
                System.out.println(safeName(target) + " is Burning!");
            } catch (Exception ignored) {
                System.out.println("Could not apply Burning status (status system not available on target)." );
            }
        }
    }

    private static String safeName(Charecter c) {
        try {
            String name = c.getName();
            return (name == null || name.isBlank()) ? "target" : name;
        } catch (Exception ignored) {
            return "target";
        }
    }
}
