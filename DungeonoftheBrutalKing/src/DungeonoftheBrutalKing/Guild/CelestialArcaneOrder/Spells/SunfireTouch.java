
package DungeonoftheBrutalKing.Guild.CelestialArcaneOrder.Spells;

import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Status.FireStatus;
import DungeonoftheBrutalKing.Status.Status;
import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Enemies.Enemies;

public class SunfireTouch implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 11;
    private static final Guild SPELL_GUILD = Guild.CELESTIAL_ARCANE_ORDER;
    private static final String SPELL_NAME = "Sunfire Touch";
    private static final int DAMAGE = 20;

    public SunfireTouch() {}

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (target == null) return;
        int currentHealth = target.getHitPoints();
        int newHealth = Math.max(currentHealth - DAMAGE, 0);
        target.setHitPoints(newHealth);
        System.out.println(target.getName() + " takes " + DAMAGE + " fire and radiant damage from Sunfire Touch!");

        // Apply burning status effect for 2 minutes
        Status burning = new FireStatus(); // duration in minutes
        target.addStatus(burning);
    }

    // Other cast methods not used for this spell
    @Override public void cast(Charecter caster, java.util.List<Charecter> allCharacters) {}
    @Override public void cast(Charecter caster) {}
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
	public void castWithStrength(Charecter enemy, double d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cast(Charecter caster, Enemies target) {
		// TODO Auto-generated method stub
		
	}
}
