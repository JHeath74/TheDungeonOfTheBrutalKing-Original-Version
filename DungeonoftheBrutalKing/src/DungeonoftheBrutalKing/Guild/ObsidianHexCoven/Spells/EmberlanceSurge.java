package DungeonoftheBrutalKing.Guild.ObsidianHexCoven.Spells;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Status.ReduceDefenseStatus;
import DungeonoftheBrutalKing.Status.StunStatus;

/**
 * Emberlance Surge: a concentrated bolt of superheated flame that pierces a single
 * target before erupting in a brief shockwave. If the target is already burning,
 * the explosion radius doubles.
 */
public class EmberlanceSurge implements Spell {
    private static final String NAME = "Emberlance Surge";
    private static final String DESCRIPTION = "A piercing lance of searing flame that erupts into a shockwave; stronger if the target is burning.";
    private static final int REQUIRED_MAGIC_POINTS = 12;
    private static final int MINIMUM_INTELLIGENCE = 10;
    private static final Guild SPELL_GUILD = Guild.OBSIDIAN_HEX_COVEN;

    private static final java.util.Random RNG = new java.util.Random();

    public EmberlanceSurge() { }

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
    public void cast() { cast(Character.getInstance()); }

    @Override
    public void cast(Character caster) {
        if (caster == null) { System.out.println("No caster available for " + NAME + "."); return; }
        System.out.println("You focus and fire an Emberlance Surge — target a foe to pierce.");
    }

    @Override
    public void cast(Character caster, Character target) {
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

        int base = 6 + intel / 3;
        int initial = base + RNG.nextInt(4);

        // Apply piercing damage to the primary target, honoring statuses
        try {
            target.takeDamageWithStatuses(initial);
        } catch (Exception ignored) {
            target.takeDamage(initial);
        }

        System.out.println(caster.getName() + " fires an Emberlance that pierces " + safeName(target) + " for " + initial + " fire damage!");

        // Break defenses: apply a brief ReduceDefenseStatus
        try {
            target.addStatus(new ReduceDefenseStatus(2));
            System.out.println(safeName(target) + " has their defenses weakened by the lance impact.");
        } catch (Exception ignored) { }

        // Determine explosion radius multiplier if target is burning
        int radiusMultiplier = 1;
        try {
            if (target.hasStatus("Burned") || target.hasStatus("FireStatus") ) radiusMultiplier = 2;
        } catch (Exception ignored) { }

        int shockDamage = Math.max(1, initial / 2);
        int shockTotal = shockDamage * radiusMultiplier;

        // Try to apply shockwave to nearby enemies via Combat.getNearbyEnemies(caster) if present
        try {
            Class<?> combatClass = Class.forName("DungeonoftheBrutalKing.Combat");
            try {
                java.lang.reflect.Method getNearby = combatClass.getMethod("getNearbyEnemies", Character.class);
                Object nearby = getNearby.invoke(null, target);
                if (nearby instanceof java.util.List<?> list) {
                    for (Object o : list) {
                        if (o == target) continue;
                        if (o instanceof Character) {
                            Character ch = (Character) o;
                            try { ch.takeDamageWithStatuses(shockTotal); } catch (Exception ex) { ch.takeDamage(shockTotal); }
                        } else if (o instanceof Enemies) {
                            Enemies e = (Enemies) o;
                            try { e.takeDamageWithStatuses(shockTotal); } catch (Exception ex) { e.takeDamage(shockTotal, null); }
                        }
                    }
                    System.out.println("The Emberlance erupts in a shockwave, dealing " + shockTotal + " to nearby foes (radius x" + radiusMultiplier + ").");
                } else {
                    System.out.println("The Emberlance erupts in a small shockwave for " + shockTotal + " damage.");
                }
            } catch (NoSuchMethodException ignored) {
                System.out.println("The Emberlance erupts in a small shockwave for " + shockTotal + " damage.");
            } catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        } catch (ClassNotFoundException ignored) {
            System.out.println("The Emberlance erupts in a small shockwave for " + shockTotal + " damage.");
        }

        // Chance to interrupt spellcasters: if the target is a caster, apply a short stun
        try {
            boolean isCaster = false;
            try { isCaster = target.getClass().getMethod("isMagicUser").invoke(target) instanceof Boolean && (Boolean) target.getClass().getMethod("isMagicUser").invoke(target); } catch (Exception ex) { /* ignore */ }
            if (isCaster) {
                target.addStatus(new StunStatus(1));
                System.out.println(safeName(target) + " is momentarily interrupted and stunned!");
            }
        } catch (Exception ignored) { }
    }

    @Override
    public void cast(Character caster, List<Character> targets) {
        if (caster == null) { cast(); return; }
        if (targets == null || targets.isEmpty()) { cast(caster); return; }

        for (Character t : targets) {
            cast(caster, t);
        }
    }

    @Override
    public void castWithIntelligence(int intelligence) {
        if (intelligence < MINIMUM_INTELLIGENCE) {
            System.out.println("You lack the necessary Intelligence to cast " + NAME + "!");
            return;
        }
        int approx = 6 + intelligence / 3;
        System.out.println(NAME + " (Intelligence) fires a lance that would pierce for ~" + approx + " damage before a shockwave.");
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        int base = (Math.max(0, toonWisdom) + Math.max(0, toonIntelligence)) / 2;
        castWithIntelligence(base);
    }

    @Override
    public void castWithStrength(Character enemy, double d) { System.out.println(NAME + " does not scale with Strength."); }

    @Override
    public void cast(Character caster, Enemies target) {
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

        int base = 6 + intel / 3;
        int initial = base + RNG.nextInt(4);

        try { target.takeDamageWithStatuses(initial); } catch (Exception ignored) { target.takeDamage(initial, null); }
        System.out.println(caster.getName() + " fires an Emberlance that pierces " + target.getName() + " for " + initial + " fire damage!");

        try { target.addStatus(new ReduceDefenseStatus(2)); } catch (Exception ignored) { }

        int radiusMultiplier = 1;
        try { if (target.hasStatus("Burned") || target.hasStatus("FireStatus")) radiusMultiplier = 2; } catch (Exception ignored) { }

        int shock = Math.max(1, initial / 2) * radiusMultiplier;
        System.out.println("The Emberlance erupts in a shockwave dealing " + shock + " damage around the target.");
    }

    private static String safeName(Character c) { try { String name = c.getName(); return (name == null || name.isBlank()) ? "target" : name; } catch (Exception ignored) { return "target"; } }

	@Override
	public void cast(int toonWisdom) {
		// TODO Auto-generated method stub
		
	}
}