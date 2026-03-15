package Guild.ObsidianHexCoven.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;
import Status.IceBarrierStatus;

/**
 * Ice Barrier: creates a shimmering barrier of enchanted ice around the caster or an ally.
 * It absorbs a portion of incoming damage and slows melee attackers that strike it.
 * When the barrier ends it shatters, releasing a burst of cold that can freeze weaker foes.
 */
public class IceBarrier implements Spell {
    private static final String NAME = "Ice Barrier";
    private static final String DESCRIPTION = "A shimmering barrier of enchanted ice that absorbs damage and slows melee attackers; shatters into a freezing burst.";
    private static final int REQUIRED_MAGIC_POINTS = 9;
    private static final int MINIMUM_WISDOM = 8;
    private static final Guild SPELL_GUILD = Guild.OBSIDIAN_HEX_COVEN;

    public IceBarrier() { }

    @Override
    public String getName() { return NAME; }

    @Override
    public String getDescription() { return DESCRIPTION; }

    @Override
    public boolean isGuildSpell() { return SPELL_GUILD != Guild.NON_GUILD; }

    @Override
    public Guild getSpellGuild() { return SPELL_GUILD; }

    @Override
    public int getRequiredMagicPoints() { return REQUIRED_MAGIC_POINTS; }

    @Override
    public void cast() {
        Charecter c = Charecter.getInstance();
        cast(c);
    }

    @Override
    public void cast(Charecter caster) {
        if (caster == null) { System.out.println("No caster available for " + NAME + "."); return; }

        int wis = Math.max(0, caster.getWisdom());
        if (wis < MINIMUM_WISDOM) { System.out.println("You lack the necessary Wisdom to cast " + NAME + "!"); return; }

        if (caster.getMagicPoints() < REQUIRED_MAGIC_POINTS) {
            System.out.println(caster.getName() + " does not have enough magic points to cast " + NAME + "!");
            return;
        }

        caster.setMagicPoints(caster.getMagicPoints() - REQUIRED_MAGIC_POINTS);

        IceBarrierStatus status = new IceBarrierStatus();
        try {
            if (caster.getStatusManager() != null) {
                caster.getStatusManager().addStatus(status, caster);
            } else {
                caster.addStatus(status);
            }
            System.out.println(caster.getName() + " is encased in a shimmering Ice Barrier.");
        } catch (Exception e) {
            System.out.println("Failed to conjure the Ice Barrier.");
        }
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster == null) { cast(); return; }
        if (target == null) { cast(caster); return; }

        int wis = Math.max(0, caster.getWisdom());
        if (wis < MINIMUM_WISDOM) { System.out.println("You lack the necessary Wisdom to cast " + NAME + "!"); return; }

        if (caster.getMagicPoints() < REQUIRED_MAGIC_POINTS) {
            System.out.println(caster.getName() + " does not have enough magic points to cast " + NAME + "!");
            return;
        }

        caster.setMagicPoints(caster.getMagicPoints() - REQUIRED_MAGIC_POINTS);

        IceBarrierStatus status = new IceBarrierStatus();
        try {
            if (target.getStatusManager() != null) {
                target.getStatusManager().addStatus(status, target);
            } else {
                target.addStatus(status);
            }
            System.out.println(caster.getName() + " places an Ice Barrier around " + safeName(target) + ".");
        } catch (Exception e) {
            System.out.println("Failed to anchor Ice Barrier on " + safeName(target) + ".");
        }
    }

    @Override
    public void cast(Charecter caster, List<Charecter> targets) {
        if (caster == null) { cast(); return; }
        if (targets == null || targets.isEmpty()) { cast(caster); return; }

        int wis = Math.max(0, caster.getWisdom());
        if (wis < MINIMUM_WISDOM) { System.out.println("You lack the necessary Wisdom to cast " + NAME + "!"); return; }

        if (caster.getMagicPoints() < REQUIRED_MAGIC_POINTS) {
            System.out.println(caster.getName() + " does not have enough magic points to cast " + NAME + "!");
            return;
        }

        caster.setMagicPoints(caster.getMagicPoints() - REQUIRED_MAGIC_POINTS);
        for (Charecter t : targets) {
            IceBarrierStatus status = new IceBarrierStatus();
            try {
                if (t.getStatusManager() != null) {
                    t.getStatusManager().addStatus(status, t);
                } else {
                    t.addStatus(status);
                }
                System.out.println(caster.getName() + " shields " + safeName(t) + " with an Ice Barrier.");
            } catch (Exception e) {
                System.out.println("Failed to anchor Ice Barrier on " + safeName(t) + ".");
            }
        }
    }

    @Override
    public void castWithIntelligence(int intelligence) {
        if (intelligence < MINIMUM_WISDOM) { System.out.println("You lack the necessary mental acuity to cast " + NAME + "!"); return; }
        System.out.println(NAME + " (Intelligence) readies a frostward that would absorb a portion of incoming damage.");
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        int base = (Math.max(0, toonWisdom) + Math.max(0, toonIntelligence)) / 2;
        castWithIntelligence(base);
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) { System.out.println(NAME + " does not scale with Strength."); }

    @Override
    public void cast(Charecter caster, Enemies target) {
        cast(caster);
        if (target != null) System.out.println("Ice Barrier shimmers — foes near the bearer will be chilled if the barrier shatters (Enemies integration not implemented).");
    }

    private static String safeName(Charecter c) { try { String name = c.getName(); return (name == null || name.isBlank()) ? "target" : name; } catch (Exception ignored) { return "target"; } }

    @Override
    public void cast(int toonWisdom) {
        // Treat single-int param as wisdom-driven cast
        castWithIntelligence(Math.max(0, toonWisdom));
    }
}
