package Guild.AuroraArcanum.Spells;

import java.util.List;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;

public class ArcaneMissile implements Spell {
    private static final int BASE_POWER = 10;
    private static final int REQUIRED_MAGIC_POINTS = 6;
    private static final Guild SPELL_GUILD = Guild.AURORA_ARCANUM;

    private boolean canUseSpell(Charecter caster) {
        return caster != null && caster.getGuild() == Guild.AURORA_ARCANUM;
    }

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
    public void cast(int toonWisdom) { }

    @Override
    public void castWithIntelligence(int toonIntelligence) { }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) { }

    @Override
    public String getName() {
        return "Arcane Missile";
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        if (canUseSpell(caster) && allCharacters != null && !allCharacters.isEmpty()) {
            Charecter target = allCharacters.get(0);
            int damage = calculatePower(caster);
            target.reduceHitPoints(damage);
        }
    }

    @Override
    public void cast(Charecter caster) { }

    public int calculatePower(Charecter charecter) {
        int intelligence = charecter.getIntelligence();
        int level = charecter.getLevel();
        return BASE_POWER + (intelligence * 2) + (level * 1);
    }

    @Override
    public void cast() { }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (canUseSpell(caster) && target != null) {
            int damage = calculatePower(caster);
            target.reduceHitPoints(damage);
        }
    }

    @Override
    public String getDescription() {
        return "Arcane Missile: Launches a bolt of magical energy at a target, dealing damage based on the caster's intelligence and level.";
    }

    @Override
    public void castWithStrength(Charecter enemy, double strength) { }
}
