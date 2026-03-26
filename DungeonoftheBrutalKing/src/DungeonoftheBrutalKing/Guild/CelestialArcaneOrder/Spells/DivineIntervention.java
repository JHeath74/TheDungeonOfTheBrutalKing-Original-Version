
// src/Guild/CelestialArcaneOrder/Spells/Resurrection.java
package Guild.CelestialArcaneOrder.Spells;

import Spells.Spell;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Status.DivineInterventionStatus;


public class DivineIntervention implements Spell {
	
    private static final int REQUIRED_MAGIC_POINTS = 15;
    private static final Guild SPELL_GUILD = Guild.CELESTIAL_ARCANE_ORDER;
    private static final String SPELL_NAME = "Resurrection";
    
    @Override
    public void cast(Charecter caster, Charecter target) {
        if (target == null) return;
        DivineInterventionStatus status = new DivineInterventionStatus(10); // 10 minutes duration
        target.addStatus(status); // Apply the status effect
        System.out.println(target.getName() + " is blessed with Resurrection!");
    }

	@Override
	public boolean isGuildSpell() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Guild getSpellGuild() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRequiredMagicPoints() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void cast(int toonWisdom) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void castWithIntelligence(int toonIntelligence) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cast(int toonWisdom, int toonIntelligence) {
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

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void castWithStrength(Charecter enemy, double d) {
		// TODO Auto-generated method stub
		
	}
}
