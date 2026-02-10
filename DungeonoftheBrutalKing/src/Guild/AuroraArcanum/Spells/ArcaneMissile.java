
package Guild.AuroraArcanum.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;

public class ArcaneMissile implements Spell {
    private static final int BASE_POWER = 10;
    private static final int REQUIRED_MAGIC_POINTS = 6;

    @Override
    public boolean isGuildSpell() {
        return true;
    }

    @Override
    public Guild getSpellGuild() {
        return Guild.AURORA_ARCANUM;
    }

    @Override
    public int getRequiredMagicPoints() {
        return REQUIRED_MAGIC_POINTS;
    }

    @Override
    public void cast(int toonWisdom) {
        // Not used for this spell
    }

    @Override
    public void castWithIntelligence(int toonIntelligence) {
        // Example: could be used for direct damage calculation
        int damage = BASE_POWER + (toonIntelligence * 2);
        // Apply damage logic here
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        // Not used for this spell
    }

    @Override
    public String getName() {
        return "Arcane Missile";
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        // Example: target the first enemy in the list
        if (allCharacters != null && !allCharacters.isEmpty()) {
            Charecter target = allCharacters.get(0);
            int damage = calculatePower(caster);
            target.reduceHitPoints(damage);
        }
    }

    @Override
    public void cast(Charecter caster) {
        // Single target version, if needed
        // Implement logic as appropriate
    }

    public int calculatePower(Charecter charecter) {
        int intelligence = charecter.getIntelligence();
        int level = charecter.getLevel();
        return BASE_POWER + (intelligence * 2) + (level * 1);
    }

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cast(Charecter caster, Charecter target) {
		// TODO Auto-generated method stub
		
	}
}
