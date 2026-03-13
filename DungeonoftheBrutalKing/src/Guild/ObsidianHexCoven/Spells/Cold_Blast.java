
package Guild.ObsidianHexCoven.Spells;

import java.util.List;
import java.util.Random;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;
import Status.IceStatus;

public class Cold_Blast implements Spell {

    private static final int MINIMUM_WISDOM = 10;
    private static final int REQUIRED_MAGIC_POINTS = 8;
    private static final Guild SPELL_GUILD = Guild.NON_GUILD;

    private static final Random RNG = new Random();

    // 25% chance to apply Frozen (IceStatus) on hit.
    private static final double ICE_STATUS_CHANCE = 0.25;

    @Override
    public String getName() {
        return "Cold\\_Blast";
    }

    @Override
    public void cast() {
        // Default: minimum viable cast.
        cast(MINIMUM_WISDOM);
    }

    @Override
    public void cast(int attackerWisdom) {
        if (attackerWisdom < MINIMUM_WISDOM) {
            System.out.println("You lack the necessary Wisdom to cast Cold Blast!");
            return;
        }

        int damage = rollDamage(attackerWisdom);
        System.out.println("Cold Blast deals " + damage + " cold damage!");
    }

    @Override
    public void cast(Charecter caster) {
        if (caster == null) {
            cast();
            return;
        }
        cast(Math.max(0, caster.getWisdom()));
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

        int wisdom = Math.max(0, caster.getWisdom());
        if (wisdom < MINIMUM_WISDOM) {
            System.out.println("You lack the necessary Wisdom to cast Cold Blast!");
            return;
        }

        int damage = rollDamage(wisdom);
        System.out.println("Cold Blast hits " + safeName(target) + " for " + damage + " cold damage!");

        maybeApplyIceStatus(target);
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
        int damage = rollDamage(intelligence);
        System.out.println("Cold Blast (Intelligence) deals " + damage + " cold damage!");
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        if (toonWisdom < MINIMUM_WISDOM) {
            System.out.println("You lack the necessary Wisdom to cast Cold Blast!");
            return;
        }

        int base = (Math.max(0, toonWisdom) + Math.max(0, toonIntelligence)) / 2;
        int damage = rollDamage(base);
        System.out.println("Cold Blast (Wisdom \\& Intelligence) deals " + damage + " cold damage!");
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
        return "A blast of freezing air that deals cold damage and may apply Frozen.";
    }

    @Override
    public void castWithStrength(Charecter enemy, double strengthScalar) {
        int base = (enemy != null) ? Math.max(0, enemy.getStrength()) : 0;
        int scaled = (int) Math.round(base * Math.max(0.0, strengthScalar));
        int damage = rollDamage(scaled);
        System.out.println("Cold Blast (Strength) deals " + damage + " cold damage!");
    }

    @Override
    public void cast(Charecter caster, Enemies target) {
        // If Enemies has its own status system, wire it here (API unknown).
        cast(caster);
    }

    private static int rollDamage(int stat) {
        int bound = Math.max(1, stat);
        return RNG.nextInt(bound) + 1;
    }

    private static void maybeApplyIceStatus(Charecter target) {
        if (target == null) return;

        if (RNG.nextDouble() <= ICE_STATUS_CHANCE) {
            try {
                target.addStatus(new IceStatus());
                System.out.println(safeName(target) + " is Frozen!");
            } catch (Exception ignored) {
                System.out.println("Could not apply Frozen status (status system not available on target).");
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
