package Guild.ObsidianHexCoven.Spells;

import java.util.List;
import java.util.Random;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;

public class Cold_Blast implements Spell {

    private static final int MINIMUM_WISDOM = 10;
    private static final int REQUIRED_MAGIC_POINTS = 8;
    private static final Guild SPELL_GUILD = Guild.NON_GUILD;

    @Override
    public String getName() {
        return "Cold_Blast";
    }

    @Override
    public void cast(int attackerWisdom) {
        if (attackerWisdom < MINIMUM_WISDOM) {
            System.out.println("You lack the necessary Wisdom to cast Cold Blast!");
        } else {
            Random random = new Random();
            int damage = random.nextInt(attackerWisdom) + 1;
            System.out.println("Cold Blast deals " + damage + " cold damage!");
        }
    }

    @Override
    public void cast() {
        // Default: use a static or default wisdom value
        cast(MINIMUM_WISDOM);
    }

    @Override
    public void cast(Charecter caster) {
        cast(caster.getWisdom());
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        cast(caster.getWisdom());
        // TODO: Apply effects to target if needed
    }

    @Override
    public void cast(Charecter caster, List<Charecter> targets) {
        for (Charecter target : targets) {
            cast(caster, target);
        }
    }

    @Override
    public void castWithIntelligence(int intelligence) {
        // Example: Intelligence could increase damage
        Random random = new Random();
        int damage = random.nextInt(intelligence) + 1;
        System.out.println("Cold Blast (Intelligence) deals " + damage + " cold damage!");
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
public void cast(int toonWisdom, int toonIntelligence) {
    if (toonWisdom < MINIMUM_WISDOM) {
        System.out.println("You lack the necessary Wisdom to cast Cold Blast!");
    } else {
        int base = (toonWisdom + toonIntelligence) / 2;
        int damage = new Random().nextInt(Math.max(base, 1)) + 1;
        System.out.println("Cold Blast (Wisdom & Intelligence) deals " + damage + " cold damage!");
    }
}

}
