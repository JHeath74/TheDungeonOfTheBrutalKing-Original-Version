
// src/Spells/Conjure_Food.java
package Guild.CelestialArcanOrder.Spells;

import java.util.List;
import java.util.Random;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;

public class Conjure_Food implements Spell {

    private static final int MINIMUM_WISDOM = 10;
    private static final int MINIMUM_INTELLIGENCE = 10;
    private static final int REQUIRED_MAGIC_POINTS = 6;
    private static Charecter myChar = Charecter.getInstance();
    private static final Guild SPELL_GUILD = Guild.NON_GUILD;

    public Conjure_Food() {
        super();
    }

    // Remove @Override, this is a helper method
    public void cast(int toonWisdom, int toonIntelligence) {
        if (toonWisdom < MINIMUM_WISDOM || toonIntelligence < MINIMUM_INTELLIGENCE) {
            System.out.println("You lack the necessary Wisdom or Intelligence to cast Conjure Food!");
        } else {
            Random random = new Random();
            int foodConjured = random.nextInt(3) + 1;
            int currentFood = Integer.parseInt(myChar.getCharInfo().get(13));
            myChar.setFood(currentFood + foodConjured);
            System.out.println("Conjured " + foodConjured + " food items!");
        }
    }

    @Override
    public void cast() {
        int wisdom = myChar.getWisdom();
        int intelligence = myChar.getIntelligence();
        cast(wisdom, intelligence);
    }

    @Override
    public boolean isGuildSpell() {
        return SPELL_GUILD != Guild.NON_GUILD;
    }

    public Guild getSpellGuild() {
        return SPELL_GUILD;
    }

    @Override
    public int getRequiredMagicPoints() {
        return REQUIRED_MAGIC_POINTS;
    }

    @Override
    public void cast(int attackerWisdom) {
        // Not used for this spell
    }

	@Override
	public void castWithIntelligence(int toonIntelligence) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cast(Charecter caster, List<Charecter> allCharacters) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cast(Charecter caster) {
		// TODO Auto-generated method stub
		
	}
}
