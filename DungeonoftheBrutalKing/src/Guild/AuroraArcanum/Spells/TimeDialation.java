
// src/Guild/AuroraArcanum/Spells/TimeDialation.java
package Guild.AuroraArcanum.Spells;

import java.util.List;
import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.TimeClock;
import SharedData.Guild;
import Spells.Spell;

public class TimeDialation implements Spell {
    private static final int BASE_SLOW_PERCENT = 20; // slows by 20% base
    private static final int REQUIRED_MAGIC_POINTS = 10;
    private static final int DURATION_SECONDS = 15;
    private static final Guild SPELL_GUILD = Guild.AURORA_ARCANUM;

    private TimeClock timer;
    private boolean active = false;
    private int enemySilenceRounds = 0;

    public int calculateSlowAmount(Charecter charecter) {
        int intelligence = charecter.getIntelligence();
        int level = charecter.getLevel();
        return BASE_SLOW_PERCENT + (int)(intelligence * 0.5) + (level * 1);
    }

    public void activate(Charecter caster) {
        timer = new TimeClock(TimeClock.Month.REBIRTH, null, null);
        timer.startClock();
        enemySilenceRounds = 2;
        active = true;
        // Optionally, apply slow effect to enemies here
    }

    public boolean isActive() {
        if (!active) return false;
        if (timer.getElapsedSeconds() >= DURATION_SECONDS) {
            active = false;
        }
        return active;
    }

    // Call this at the end of each enemy round
    public void decrementEnemySilence() {
        if (enemySilenceRounds > 0) {
            enemySilenceRounds--;
        }
    }

    // Call this before the enemy tries to attack
    public boolean isEnemySilenced() {
        return enemySilenceRounds > 0;
    }

    // Example: slows enemy speed by the calculated percentage
    public int applySlow(Charecter enemy, int originalSpeed, Charecter caster) {
        if (isActive()) {
            int slowPercent = calculateSlowAmount(caster);
            int slowedSpeed = originalSpeed - (originalSpeed * slowPercent / 100);
            return Math.max(1, slowedSpeed);
        }
        return originalSpeed;
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
        return "Time Dialation";
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
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        // Applies to the caster only
        cast(caster);
    }

    @Override
    public void cast(Charecter caster) {
        if (caster != null) {
            activate(caster);
        }
    }

    @Override
    public void cast() {
        // Not applicable: requires a caster
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        // Applies to the target if not null, otherwise to the caster
        if (target != null) {
            activate(target);
        } else if (caster != null) {
            activate(caster);
        }
    }
}
