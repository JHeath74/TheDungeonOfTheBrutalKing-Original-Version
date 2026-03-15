package Guild.ObsidianHexCoven.Spells;

import java.util.List;
import java.util.Random;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;
import Status.NecroticStatus;

/**
 * Chill Touch: a ghostly skeletal hand that deals necrotic damage and makes it
 * harder for the target to benefit from healing. Against undead, it also
 * weakens their ability to attack you.
 */
public class Chill_Touch implements Spell {

    private static final int MINIMUM_INTELLIGENCE = 8;
    private static final int REQUIRED_MAGIC_POINTS = 5;
    private static final Guild SPELL_GUILD = Guild.OBSIDIAN_HEX_COVEN; // thematic to the coven

    private static final Random RNG = new Random();

    // 30% chance to apply an anti-healing debuff status.
    private static final double NECROTIC_STATUS_CHANCE = 0.30;

    @Override
    public String getName() {
        return "Chill Touch";
    }

    @Override
    public void cast() {
        castWithIntelligence(MINIMUM_INTELLIGENCE);
    }

    @Override
    public void cast(int attackerWisdom) {
        castWithIntelligence(attackerWisdom);
    }

    @Override
    public void cast(Charecter caster) {
        if (caster == null) {
            cast();
            return;
        }
        castWithIntelligence(Math.max(0, caster.getIntelligence()));
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
            System.out.println("You lack the necessary Intelligence to cast Chill Touch!");
            return;
        }

        int damage = rollNecroticDamage(intelligence);
        target.setHitPoints(target.getHitPoints() - damage);
        System.out.println("A ghostly hand grips " + safeName(target) + ", dealing " + damage + " necrotic damage!");

        maybeApplyNecroticDebuff(caster, target);
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
            System.out.println("You lack the necessary Intelligence to cast Chill Touch!");
            return;
        }
        int damage = rollNecroticDamage(intelligence);
        System.out.println("Chill Touch deals " + damage + " necrotic damage!");
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        int base = (Math.max(0, toonWisdom) + Math.max(0, toonIntelligence)) / 2;
        if (base < MINIMUM_INTELLIGENCE) {
            System.out.println("You lack the necessary mental acuity to cast Chill Touch!");
            return;
        }
        int damage = rollNecroticDamage(base);
        System.out.println("Chill Touch (Wisdom & Intelligence) deals " + damage + " necrotic damage!");
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
        return "A ghostly skeletal hand that deals necrotic damage and hinders healing; especially punishing for undead.";
    }

    @Override
    public void castWithStrength(Charecter caster, double strengthScalar) {
        int base = (caster != null) ? Math.max(0, caster.getStrength()) : 0;
        int scaled = (int) Math.round(base * Math.max(0.0, strengthScalar));
        int damage = rollNecroticDamage(scaled);
        System.out.println("Chill Touch (Strength) deals " + damage + " necrotic damage!");
    }

    @Override
    public void cast(Charecter caster, Enemies target) {
        // If Enemies has its own status system, wire it similarly later.
        cast(caster);
    }

    private static int rollNecroticDamage(int stat) {
        // Small but unsettling damage: 1..(stat/2 + 3)
        int bound = Math.max(1, stat / 2 + 3);
        return RNG.nextInt(bound) + 1;
    }

    /**
     * Apply an anti-healing / anti-undead debuff if possible.
     */
    private static void maybeApplyNecroticDebuff(Charecter caster, Charecter target) {
        if (target == null) return;

        if (RNG.nextDouble() > NECROTIC_STATUS_CHANCE) {
            System.out.println(safeName(target) + " recoils, their life-force chilled, but the curse does not fully take hold.");
            return;
        }

        try {
            // Apply a concrete necrotic status that weakens attack and can be used by the healing system.
            target.addStatus(new NecroticStatus());
            System.out.println(safeName(target) + " is gripped by necrotic chill and finds healing far less effective!");

            // Optional flavour only: log extra message against undead without modifying stats twice.
            if (isUndead(target)) {
                System.out.println("Undead flesh withers: " + safeName(target) + " is especially harmed by the necrotic chill.");
            }
        } catch (Exception e) {
            System.out.println("The necrotic curse flickers, unable to fully anchor to the target.");
        }
    }

    private static boolean isUndead(Charecter target) {
        try {
            // If you have a dedicated flag or race type, prefer that here.
            String race = target.getRace();
            return race != null && race.toLowerCase().contains("undead");
        } catch (Exception ignored) {
            return false;
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