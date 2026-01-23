package Guild.AuroraArcanum.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.TimeClock;
import SharedData.Guild;
import Spells.Spell;

public class CelestialWard implements Spell {
    private static final int BASE_DEFENSE = 8;
    private static final int DURATION_SECONDS = 15;
    private static final int REQUIRED_MAGIC_POINTS = 12;
    private static final Guild SPELL_GUILD = Guild.AURORA_ARCANUM;

    private TimeClock timer;
    private boolean active = false;

    public int calculateDefenseBoost(Charecter charecter) {
        int intelligence = charecter.getIntelligence();
        int level = charecter.getLevel();
        return BASE_DEFENSE + intelligence + (level * 2);
    }

    public void activate() {
        timer = new TimeClock(TimeClock.Month.REBIRTH, null, null);
        timer.startClock();
        active = true;
    }

    public boolean isActive() {
        if (!active) return false;
        if (timer.getElapsedSeconds() >= DURATION_SECONDS) {
            active = false;
        }
        return active;
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
    public void cast(int toonWisdom) {
        activate();
    }

    @Override
    public void castWithIntelligence(int toonIntelligence) {
        activate();
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        activate();
    }

    @Override
    public String getName() {
        return "Celestial Ward";
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        cast(caster);
    }

    @Override
    public void cast(Charecter caster) {
        activate();
        // Optionally, apply defense boost to caster here
    }

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}
}
