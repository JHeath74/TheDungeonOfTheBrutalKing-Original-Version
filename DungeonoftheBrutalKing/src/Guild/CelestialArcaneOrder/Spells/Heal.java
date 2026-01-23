
// src/Spells/Heal.java
package Guild.CelestialArcaneOrder.Spells;

import SharedData.Guild;
import Spells.Spell;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Singleton;

public class Heal implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 8;
    private static final Guild SPELL_GUILD = Guild.NON_GUILD;
    private static Charecter myChar = Charecter.getInstance();
    private String name;

    public Heal() {
        this.name = "Heal";
    }

    @Override
    public void cast() {
        int intelligence = myChar.getIntelligence();
        int maxHealth = myChar.getMaxHitPoints();
        int currentHealth = myChar.getHitPoints();

        int healthRestored = 10 + intelligence;
        int newHealth = Math.min(currentHealth + healthRestored, maxHealth);

        Singleton.myCharSingleton().setHitPoints(newHealth);

        System.out.println("Restored " + (newHealth - currentHealth) + " health points!");
    }

    @Override
    public boolean isGuildSpell() {
        return SPELL_GUILD != Guild.NON_GUILD;
    }

    public Guild getSpellGuild() {
        return SPELL_GUILD;
    }

    
    public void cast(int attackerWisdom) {
        // Not used for this spell
    }

    @Override
    public int getRequiredMagicPoints() {
        return REQUIRED_MAGIC_POINTS;
    }

    
    public void cast(int toonWisdom, int toonIntelligence) {
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
