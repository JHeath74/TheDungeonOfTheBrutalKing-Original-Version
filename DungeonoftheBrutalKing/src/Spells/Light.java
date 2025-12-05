
// src/Spells/Light.java
package Spells;

import SharedData.Guild;

public class Light implements Spell {

    private static final Guild SPELL_GUILD = Guild.NON_GUILD;
    private static final int REQUIRED_MAGIC_POINTS = 10;

    public Light() {
        super();
    }

    @Override
    public void cast() {
        System.out.println("Casting the Light spell...");
        // Add specific behavior for the Light spell here
    }

    @Override
    public boolean isGuildSpell() {
        return SPELL_GUILD != Guild.NON_GUILD;
    }

    public Guild getSpellGuild() {
        return SPELL_GUILD;
    }

    @Override
    public void cast(int attackerWisdom) {
        // Not used for this spell
    }

    @Override
    public int getRequiredMagicPoints() {
        return REQUIRED_MAGIC_POINTS;
    }

	
	public void cast(int toonWisdom, int toonIntelligence) {
		// TODO Auto-generated method stub
		
	}
}
