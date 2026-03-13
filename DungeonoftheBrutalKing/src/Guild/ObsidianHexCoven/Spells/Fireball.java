
package Guild.ObsidianHexCoven.Spells;

import java.util.List;
import java.util.Random;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;
import Status.FireStatus;

public class Fireball implements Spell {

    private static final int MINIMUM_WISDOM = 10;
    private static final int REQUIRED_MAGIC_POINTS = 10;
    private static final Guild SPELL_GUILD = Guild.OBSIDIAN_HEX_COVEN;

    private static final Random RNG = new Random();
    private static final Charecter MY_CHAR = Charecter.getInstance();

    @Override
    public String getName() {
        return "Fireball";
    }

    @Override
    public void cast() {
        if (MY_CHAR == null) {
            System.out.println("Character is not available.");
            return;
        }

        int attackerWisdom = safeParseStatFromCharInfo(MY_CHAR, 10);
        cast(attackerWisdom);
    }

    @Override
    public void cast(int attackerWisdom) {
        if (attackerWisdom < MINIMUM_WISDOM) {
            System.out.println("You lack the necessary Wisdom to cast Fireball!");
            return;
        }

        int damage = rollDamage(attackerWisdom);
        System.out.println("Fireball deals " + damage + " fire damage!");
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
            caster = MY_CHAR;
        }
        if (target == null) {
            cast(caster);
            return;
        }

        int wisdom = (caster != null) ? Math.max(0, caster.getWisdom()) : 0;
        if (wisdom < MINIMUM_WISDOM) {
            System.out.println("You lack the necessary Wisdom to cast Fireball!");
            return;
        }

        int damage = rollDamage(wisdom);
        System.out.println("Fireball hits " + target.getName() + " for " + damage + " fire damage!");

        // Apply the Fire status to the target.
        applyFireStatus(target);
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
        System.out.println("Fireball (Intelligence) deals " + damage + " fire damage!");
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        if (toonWisdom < MINIMUM_WISDOM) {
            System.out.println("You lack the necessary Wisdom to cast Fireball!");
            return;
        }

        int base = (Math.max(0, toonWisdom) + Math.max(0, toonIntelligence)) / 2;
        int damage = rollDamage(base);
        System.out.println("Fireball (Wisdom & Intelligence) deals " + damage + " fire damage!");
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
        return "Hurls a fiery projectile that deals damage and applies Burned.";
    }

    @Override
    public void castWithStrength(Charecter enemy, double strengthScalar) {
        int base = (enemy != null) ? Math.max(0, enemy.getStrength()) : 0;
        int scaled = (int) Math.round(base * Math.max(0.0, strengthScalar));
        int damage = rollDamage(scaled);
        System.out.println("Fireball (Strength) deals " + damage + " fire damage!");
    }

    @Override
    public void cast(Charecter caster, Enemies target) {
        cast(caster);
        // Status application here depends on your Enemies API (e.g., addStatus/statusManager).
    }

    private static void applyFireStatus(Charecter target) {
        try {
            // Prefer a direct API if your Character supports it.
            // This call name may differ in your project; adjust as needed.
            target.addStatus(new FireStatus());
        } catch (Exception ignored) {
            // Fallback: if there is no status system hooked up on Charecter, avoid crashing.
            System.out.println("Could not apply Burned status (status system not available on target).");
        }
    }

    private static int rollDamage(int stat) {
        int bound = Math.max(1, stat);
        return RNG.nextInt(bound) + 1;
    }

    private static int safeParseStatFromCharInfo(Charecter ch, int index) {
        try {
            if (ch == null || ch.getCharInfo() == null) return 0;
            String value = ch.getCharInfo().get(index);
            if (value == null) return 0;
            return Integer.parseInt(value.trim());
        } catch (Exception ignored) {
            return 0;
        }
    }
}
