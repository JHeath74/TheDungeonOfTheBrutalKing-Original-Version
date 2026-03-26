
// src/Guild/AuroraArcanum/Spells/ManaSurge.java
package Guild.AuroraArcanum.Spells;

import java.util.List;
import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.TimeClock;
import SharedData.Guild;
import Spells.Spell;

public class ManaSurge implements Spell {
    private static final int BASE_MAGIC_BOOST = 10;
    private static final int REQUIRED_MAGIC_POINTS = 8;
    private static final int DURATION_SECONDS = 15;
    private static final Guild SPELL_GUILD = Guild.AURORA_ARCANUM;

    private TimeClock timer;
    private boolean active = false;

    private boolean canUseSpell(Charecter caster) {
        return caster != null && caster.getGuild() == SPELL_GUILD;
    }

    public int calculateMagicBoost(Charecter charecter) {
        int intelligence = charecter.getIntelligence();
        int level = charecter.getLevel();
        return BASE_MAGIC_BOOST + (intelligence * 2) + (level * 1);
    }

    public void activate(Charecter charecter) {
        timer = new TimeClock(TimeClock.Month.REBIRTH, null, null);
        timer.startClock();
        healCaster(charecter);
        active = true;
        // Optionally, apply magic boost to charecter here
    }

    private void healCaster(Charecter charecter) {
        int maxHealth = charecter.getMaxHitPoints();
        int healAmount = (int) Math.ceil(maxHealth * 0.05);
        int newHealth = Math.min(charecter.getHitPoints() + healAmount, maxHealth);
        charecter.setHitPoints(newHealth);
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
    public String getName() {
        return "Mana Surge";
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        if (canUseSpell(caster)) {
            cast(caster);
        }
    }

    @Override
    public void cast(Charecter caster) {
        if (canUseSpell(caster)) {
            activate(caster);
        }
    }

    @Override
    public void cast() {
        // Not applicable: requires a caster
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (canUseSpell(caster)) {
            if (target != null) {
                activate(target);
            } else {
                activate(caster);
            }
        }
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
