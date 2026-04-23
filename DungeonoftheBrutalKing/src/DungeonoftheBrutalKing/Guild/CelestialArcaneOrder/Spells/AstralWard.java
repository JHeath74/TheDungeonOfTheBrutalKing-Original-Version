
package DungeonoftheBrutalKing.Guild.CelestialArcaneOrder.Spells;

import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Status.AstralWardStatus;
import DungeonoftheBrutalKing.Status.Status;

import java.util.List;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;

public class AstralWard implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 12;
    private static final Guild SPELL_GUILD = Guild.CELESTIAL_ARCANE_ORDER;
    private static final String SPELL_NAME = "Astral Ward";

    @Override
    public void cast(Character caster, Character target) {
        if (target == null) return;
        Status status = new AstralWardStatus(5); // 5 minutes duration
        target.addStatus(status);
        System.out.println(target.getName() + " is protected by an Astral Ward!");
    }

    @Override
    public String getName() { return SPELL_NAME; }

    @Override
    public Guild getSpellGuild() { return SPELL_GUILD; }

    @Override
    public boolean isGuildSpell() { return true; }

    @Override
    public int getRequiredMagicPoints() { return REQUIRED_MAGIC_POINTS; }

    @Override
    public String getDescription() { return "Grants a temporary shield that absorbs damage for 5 minutes."; }

    // Stub implementations for required interface methods
    @Override
    public void cast() {}

    @Override
    public void cast(Character caster) {}

    @Override
    public void cast(int x) {}

    @Override
    public void cast(int x, int y) {}

    @Override
    public void cast(Character caster, List<Character> targets) {}

    @Override
    public void castWithStrength(Character caster, double strength) {}

    @Override
    public void castWithIntelligence(int intelligence) {}

	@Override
	public void cast(Character caster, Enemies target) {
		// TODO Auto-generated method stub
		
	}
}
