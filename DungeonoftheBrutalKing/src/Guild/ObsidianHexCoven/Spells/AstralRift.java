package Guild.ObsidianHexCoven.Spells;

import java.util.List;
import java.util.Random;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;
import Status.AstralRiftStatus;

/**
 * Astral Rift: creates a small fracture that pulls enemies toward its center,
 * deals arcane damage over time and reduces their spell resistance.
 */
public class AstralRift implements Spell {
    private static final String NAME = "Astral Rift";
    private static final String DESCRIPTION = "Tear open a small fracture in space; pulls enemies in, deals arcane damage over time and reduces spell resistance.";
    private static final int REQUIRED_MAGIC_POINTS = 14;
    private static final int MINIMUM_INTELLIGENCE = 12;
    private static final Guild SPELL_GUILD = Guild.OBSIDIAN_HEX_COVEN;

    private static final Random RNG = new Random();

    public AstralRift() { }

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
        if (caster == null) {
            System.out.println("No caster available for " + NAME + ".");
            return;
        }

        int intel = Math.max(0, caster.getIntelligence());
        if (intel < MINIMUM_INTELLIGENCE) {
            System.out.println("You lack the necessary Intelligence to tear open an Astral Rift!");
            return;
        }

        if (caster.getMagicPoints() < REQUIRED_MAGIC_POINTS) {
            System.out.println(caster.getName() + " does not have enough magic points to cast " + NAME + "!");
            return;
        }

        caster.setMagicPoints(caster.getMagicPoints() - REQUIRED_MAGIC_POINTS);
        System.out.println(caster.getName() + " unfurls a shimmering fracture in space — an Astral Rift appears.");
        // Rift created but no specific targets were provided.
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster == null) { cast(); return; }
        if (target == null) { cast(caster); return; }

        int intel = Math.max(0, caster.getIntelligence());
        if (intel < MINIMUM_INTELLIGENCE) {
            System.out.println("You lack the necessary Intelligence to cast " + NAME + "!");
            return;
        }

        if (caster.getMagicPoints() < REQUIRED_MAGIC_POINTS) {
            System.out.println(caster.getName() + " does not have enough magic points to cast " + NAME + "!");
            return;
        }

        caster.setMagicPoints(caster.getMagicPoints() - REQUIRED_MAGIC_POINTS);
        applyAstralRiftToTarget(caster, target);
    }

    @Override
    public void cast(Charecter caster, List<Charecter> targets) {
        if (caster == null) { cast(); return; }
        if (targets == null || targets.isEmpty()) { cast(caster); return; }

        int intel = Math.max(0, caster.getIntelligence());
        if (intel < MINIMUM_INTELLIGENCE) {
            System.out.println("You lack the necessary Intelligence to cast " + NAME + "!");
            return;
        }

        if (caster.getMagicPoints() < REQUIRED_MAGIC_POINTS) {
            System.out.println(caster.getName() + " does not have enough magic points to cast " + NAME + "!");
            return;
        }

        caster.setMagicPoints(caster.getMagicPoints() - REQUIRED_MAGIC_POINTS);
        System.out.println(caster.getName() + " opens an Astral Rift — arcane gravity pulls at nearby foes!");
        for (Charecter t : targets) applyAstralRiftToTarget(caster, t);
    }

    @Override
    public void castWithIntelligence(int intelligence) {
        if (intelligence < MINIMUM_INTELLIGENCE) {
            System.out.println("You lack the necessary Intelligence to cast " + NAME + "!");
            return;
        }
        int damage = 3 + (intelligence / 3);
        System.out.println(NAME + " (Intelligence) creates a rift that would deal ~" + damage + " arcane damage over time.");
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
        if (target != null) {
            System.out.println("Astral Rift affects the enemy group, pulling them toward the fracture (Enemies integration not fully implemented).");
        }
    }

    private void applyAstralRiftToTarget(Charecter caster, Charecter target) {
        if (target == null) return;

        int intel = (caster != null) ? Math.max(0, caster.getIntelligence()) : 0;
        int initialDamage = 2 + intel / 4 + RNG.nextInt(3);
        try { target.takeDamage(initialDamage); } catch (Exception ignored) {}

        AstralRiftStatus status = new AstralRiftStatus();
        try {
            if (target.getStatusManager() != null) {
                target.getStatusManager().addStatus(status, target);
            } else {
                target.addStatus(status);
            }
            System.out.println(caster.getName() + " anchors an Astral Rift on " + safeName(target) + ", pulling them toward the void and weakening their magic! (" + initialDamage + " initial arcane damage)");
        } catch (Exception e) {
            System.out.println("Astral Rift failed to latch onto " + safeName(target) + ".");
        }
    }

    private static String safeName(Charecter c) {
        try { String name = c.getName(); return (name == null || name.isBlank()) ? "target" : name; } catch (Exception ignored) { return "target"; }
    }

	@Override
	public void cast(int toonWisdom) {
		// TODO Auto-generated method stub
		
	}
}
