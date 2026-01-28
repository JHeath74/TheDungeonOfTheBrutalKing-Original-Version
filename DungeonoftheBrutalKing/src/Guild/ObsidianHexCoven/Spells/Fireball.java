
java
package Guild.ObsidianHexCoven.Spells;

import java.util.List;
import java.util.Random;
import SharedData.Guild;
import Spells.Spell;
import DungeonoftheBrutalKing.Charecter;

public class Fireball implements Spell {

    private static final int MINIMUM_WISDOM = 10;
    private static final int REQUIRED_MAGIC_POINTS = 10;
    private static final Guild SPELL_GUILD = Guild.OBSIDIAN_HEX_COVEN; // Set to correct guild
    private static Charecter myChar = Charecter.getInstance();

    @Override
    public String getName() {
        return "Fireball";
    }

    @Override
    public void cast() {
        if (myChar == null || myChar.getCharInfo() == null) {
            System.out.println("Character or character info is not available.");
            return;
        }
        String wisdomValue = myChar.getCharInfo().get(10);
        int attackerWisdom = (wisdomValue != null) ? Integer.parseInt(wisdomValue) : 0;
        cast(attackerWisdom);
    }

    @Override
    public void cast(int attackerWisdom) {
        if (attackerWisdom < MINIMUM_WISDOM) {
            System.out.println("You lack the necessary Wisdom to cast Fireball!");
        } else {
            int damage = new Random().nextInt(attackerWisdom) + 1;
            System.out.println("Fireball deals " + damage + " fire damage!");
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
            // TODO: Apply effects to target if needed
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
        int damage = new Random().nextInt(intelligence) + 1;
        System.out.println("Fireball (Intelligence) deals " + damage + " fire damage!");
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        if (toonWisdom < MINIMUM_WISDOM) {
            System.out.println("You lack the necessary Wisdom to cast Fireball!");
        } else {
            int base = (toonWisdom + toonIntelligence) / 2;
            int damage = new Random().nextInt(Math.max(base, 1)) + 1;
            System.out.println("Fireball (Wisdom & Intelligence) deals " + damage + " fire damage!");
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
