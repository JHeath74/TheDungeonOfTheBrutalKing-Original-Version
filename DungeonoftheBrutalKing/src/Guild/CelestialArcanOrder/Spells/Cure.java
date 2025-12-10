
// src/Spells/Cure.java
package Guild.CelestialArcanOrder.Spells;

import java.util.Random;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Singleton;
import SharedData.Guild;
import Spells.Spell;

public class Cure implements Spell {

    private static final Guild SPELL_GUILD = Guild.NON_GUILD;
    private static final int REQUIRED_MAGIC_POINTS = 5;

    public Cure() {
        // No need for super()
    }

    @Override
    public void cast() {
        String[] negativeEffects = {"Poison", "Blindness", "Paralysis"};
        Random random = new Random();
        Character character = Singleton.myCharSingleton();
        for (String effect : negativeEffects) {
            if (random.nextDouble() < 0.75) {
                character.getStatusManager().removeStatusByName(effect);
                System.out.println("Cured " + effect + "!");
            } else {
                System.out.println("Failed to cure " + effect + ".");
            }
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
