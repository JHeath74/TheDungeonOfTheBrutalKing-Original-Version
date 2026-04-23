
// src/Guild/CelestialArcaneOrder/Spells/PurifyingChains.java
package DungeonoftheBrutalKing.Guild.CelestialArcaneOrder.Spells;

import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Status.StatusType;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;

public class PurifyingChains implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 10;
    private static final Guild SPELL_GUILD = Guild.CELESTIAL_ARCANE_ORDER;
    private static final String SPELL_NAME = "Purifying Chains";
    private static final int DURATION = 1;

    public PurifyingChains() {}

    @Override
    public void cast(Character caster, Character target) {
        if (target == null) return;
        target.applyStatusEffect(StatusType.IMMOBILIZED_STATUS, DURATION, 0, caster);
        target.removeStatusEffect(StatusType.POISON_STATUS); // Assuming removeStatusEffect also uses StatusType
        System.out.println(target.getName() + " is bound by Purifying Chains and cannot act for " + DURATION + " turn!");
        System.out.println(target.getName() + " is cured of Poisoned status!");
    }

    // Other cast methods not used for this spell
    @Override public void cast(Character caster, java.util.List<Character> allCharacters) {}
    @Override public void cast(Character caster) {}
    @Override public void cast() {}
    @Override public void cast(int toonWisdom) {}
    @Override public void castWithIntelligence(int toonIntelligence) {}
    @Override public void cast(int toonWisdom, int toonIntelligence) {}

    @Override public boolean isGuildSpell() { return true; }
    @Override public Guild getSpellGuild() { return SPELL_GUILD; }
    @Override public int getRequiredMagicPoints() { return REQUIRED_MAGIC_POINTS; }
    @Override public String getName() { return SPELL_NAME; }

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void castWithStrength(Character enemy, double d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cast(Character caster, Enemies target) {
		// TODO Auto-generated method stub
		
	}
}
