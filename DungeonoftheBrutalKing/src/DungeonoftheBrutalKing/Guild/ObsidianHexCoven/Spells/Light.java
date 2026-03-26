package Guild.ObsidianHexCoven.Spells;

import java.util.List;
import SharedData.Guild;
import Spells.Spell;
import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;

public class Light implements Spell {

    private static final Guild SPELL_GUILD = Guild.OBSIDIAN_HEX_COVEN;
    private static final int REQUIRED_MAGIC_POINTS = 10;
    private static final int MINIMUM_WISDOM = 5;

    public Light() {
        super();
    }

    @Override
    public String getName() {
        return "Light";
    }

    @Override
    public void cast() {
        System.out.println("A soft, magical light fills the immediate area.");
    }

    @Override
    public void cast(int attackerWisdom) {
        if (attackerWisdom < MINIMUM_WISDOM) {
            System.out.println("You lack the necessary Wisdom to cast Light!");
        } else {
            System.out.println("You cast Light, illuminating your surroundings.");
        }
    }

    @Override
    public void cast(Charecter caster) {
        if (caster != null) {
            if (caster.getMagicPoints() < REQUIRED_MAGIC_POINTS) {
                System.out.println(caster.getName() + " does not have enough magic points to cast Light!");
                return;
            }
            caster.setMagicPoints(caster.getMagicPoints() - REQUIRED_MAGIC_POINTS);
            cast(caster.getWisdom());
        } else {
            cast();
        }
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster != null) {
            cast(caster.getWisdom());
            if (target != null) {
                System.out.println("The light bathes " + safeName(target) + ", revealing their form.");
            }
        } else {
            cast();
        }
    }

    @Override
    public void cast(Charecter caster, List<Charecter> targets) {
        if (targets != null) {
            for (Charecter target : targets) {
                cast(caster, target);
            }
        }
    }

    @Override
    public void castWithIntelligence(int intelligence) {
        System.out.println("You focus your mind and cast Light with intelligence: " + intelligence);
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        if (toonWisdom < MINIMUM_WISDOM) {
            System.out.println("You lack the necessary Wisdom to cast Light!");
        } else {
            System.out.println("You cast Light using both wisdom and intelligence.");
        }
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
        return "Creates a magical source of illumination that banishes darkness and briefly reveals hidden or invisible things in the area.";
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) {
        // Light is not a strength-based spell. Provide a graceful no-op / feedback.
        System.out.println("Light is a spell of illumination and does not scale with Strength.");
    }

    @Override
    public void cast(Charecter caster, Enemies target) {
        // Best-effort: apply illumination effect to enemy/group if present, otherwise just cast on caster.
        cast(caster);
        if (target != null) {
            System.out.println("The area around the enemy group is brightly lit, hampering stealth and revealing silhouettes.");
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