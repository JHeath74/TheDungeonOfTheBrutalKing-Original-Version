
package DungeonoftheBrutalKing.Guild.CelestialArcaneOrder.Spells;

import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;

import java.util.List;

public class StellarFlare implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 15;
    private static final Guild SPELL_GUILD = Guild.CELESTIAL_ARCANE_ORDER;
    private static final String SPELL_NAME = "Stellar Flare";
    private static final int BASE_DAMAGE = 18;

    public StellarFlare() {}

    // Deals radiant damage to all targets in the list
    private void dealStellarFlareDamage(Character caster, List<Character> targets) {
        if (targets == null || targets.isEmpty()) return;
        int intelligence = caster != null ? caster.getIntelligence() : 0;
        int damage = BASE_DAMAGE + intelligence;
        for (Character target : targets) {
            if (target == null) continue;
            int currentHealth = target.getHitPoints();
            int newHealth = Math.max(currentHealth - damage, 0);
            target.setHitPoints(newHealth);
            System.out.println(target.getName() + " takes " + (currentHealth - newHealth) + " radiant damage from " + SPELL_NAME + "!");
        }
    }

    @Override
    public void cast(Character caster, Character target) {
        // Single target version
        dealStellarFlareDamage(caster, List.of(target));
    }

    @Override
    public void cast(Character caster, List<Character> allCharacters) {
        dealStellarFlareDamage(caster, allCharacters);
    }

    @Override
    public void cast(Character caster) {
        // Not applicable: needs targets
    }

    @Override
    public void cast() {
        // Not applicable: needs targets
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
