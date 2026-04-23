package DungeonoftheBrutalKing.Guild.CelestialArcaneOrder.Spells;

import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;

import java.util.List;

public class Heal implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 8;
    private static final Guild SPELL_GUILD = Guild.CELESTIAL_ARCANE_ORDER;
    private static final String SPELL_NAME = "Heal";

    public Heal() {}

    // Core spell logic: heals the target character
    private void healCharacter(Character target) {
        if (target == null) return;
        int intelligence = target.getIntelligence();
        int maxHealth = target.getMaxHitPoints();
        int currentHealth = target.getHitPoints();

        int healthRestored = 10 + intelligence;
        int newHealth = Math.min(currentHealth + healthRestored, maxHealth);

        target.setHitPoints(newHealth);

        System.out.println(target.getName() + " restored " + (newHealth - currentHealth) + " health points!");
    }

    @Override
    public void cast(Character caster, Character target) {
        healCharacter(target != null ? target : caster);
    }

    @Override
    public void cast(Character caster, List<Character> allCharacters) {
        if (allCharacters != null && !allCharacters.isEmpty()) {
            for (Character ch : allCharacters) {
                healCharacter(ch);
            }
        }
    }

    @Override
    public void cast(Character caster) {
        healCharacter(caster);
    }

    @Override
    public void cast() {
        // Not applicable: requires a character
    }

    @Override
    public void cast(int toonWisdom) {
        // Not used for this spell
    }

    @Override
    public void castWithIntelligence(int toonIntelligence) {
        // Not used for this spell
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        // Not used for this spell
    }

    @Override
    public boolean isGuildSpell() {
        return true;
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
    public String getName() {
        return SPELL_NAME;
    }

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
