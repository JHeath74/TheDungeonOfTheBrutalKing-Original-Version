
// src/Spells/Fireball.java
package Guild.ObsidianHexCoven.Spells;

import java.util.Random;
import SharedData.Guild;
import Spells.Spell;
import DungeonoftheBrutalKing.Charecter;

public class Fireball implements Spell {

    private static final int MINIMUM_WISDOM = 10;
    private static final Guild SPELL_GUILD = Guild.NON_GUILD;
    private static Charecter myChar = Charecter.getInstance();

    @Override
    public void cast() {
        if (myChar == null || myChar.getCharInfo() == null) {
            System.out.println("Character or character info is not available.");
            return;
        }
        String wisdomValue = myChar.getCharInfo().get(10);
        int attackerWisdom = (wisdomValue != null) ? Integer.parseInt(wisdomValue) : 0;

        if (attackerWisdom < MINIMUM_WISDOM) {
            System.out.println("You lack the necessary Wisdom to cast Fireball!");
        } else {
            Random random = new Random();
            int damage = random.nextInt(attackerWisdom) + 1;
            System.out.println("Fireball deals " + damage + " fire damage!");
        }
    }

    @Override
    public boolean isGuildSpell() {
        return SPELL_GUILD != Guild.NON_GUILD;
    }

    public Guild getSpellGuild() {
        return SPELL_GUILD;
    }

	
	public void cast(int attackerWisdom) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getRequiredMagicPoints() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void cast(int toonWisdom, int toonIntelligence) {
		// TODO Auto-generated method stub
		
	}
}
