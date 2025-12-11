
// src/Spells/Cold_Blast.java
package Guild.ObsidianHexCoven.Spells;

import java.util.Random;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;

public class Cold_Blast implements Spell {

    private static final int MINIMUM_WISDOM = 10;
    private static final int REQUIRED_MAGIC_POINTS = 8;
    private static Charecter myChar = Charecter.getInstance();
    private static final Guild SPELL_GUILD = Guild.NON_GUILD;

    int attackerWisdom = myChar.getWisdom();

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
    public boolean isGuildSpell() {
        return SPELL_GUILD != Guild.NON_GUILD;
    }

    public Guild getSpellGuild() {
        return SPELL_GUILD;
    }

    @Override
    public void cast() {
        // TODO: Implement if needed
    }

    @Override
    public int getRequiredMagicPoints() {
        return REQUIRED_MAGIC_POINTS;
    }
}
