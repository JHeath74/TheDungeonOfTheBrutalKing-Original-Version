package Guild.ObsidianHexCoven.Spells;

import java.util.List;
import SharedData.Guild;
import Spells.Spell;
import DungeonoftheBrutalKing.Charecter;

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
        System.out.println("A radiant light fills the area!");
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
            cast(caster.getWisdom());
        } else {
            cast();
        }
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster != null) {
            cast(caster.getWisdom());
            // Optionally: affect target with light
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
}
