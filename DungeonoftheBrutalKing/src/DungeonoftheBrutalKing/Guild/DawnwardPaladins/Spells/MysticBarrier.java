
package DungeonoftheBrutalKing.Guild.DawnwardPaladins.Spells;

import java.util.List;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Status.StatusType;

public class MysticBarrier implements Spell {
    private static final int BASE_REDUCTION = 5;
    private static final int REQUIRED_MAGIC_POINTS = 8;
    private static final int BASE_DURATION = 2;
    private static final Guild SPELL_GUILD = Guild.DAWNWARD_PALADINS;

    private boolean canUseSpell(Character caster) {
        return caster != null && caster.getGuild() == Guild.DAWNWARD_PALADINS;
    }

    @Override
    public boolean isGuildSpell() {
        return true;
    }

    @Override
    public Guild getSpellGuild() {
        return Guild.DAWNWARD_PALADINS;
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
        return "Mystic Barrier";
    }

    @Override
    public void cast(Character caster, List<Character> allCharacters) {
        if (canUseSpell(caster) && allCharacters != null && !allCharacters.isEmpty()) {
            Character target = allCharacters.get(0);
            applyBarrier(target, caster.getIntelligence());
        }
    }

    @Override
    public void cast(Character caster) {
        if (canUseSpell(caster)) {
            applyBarrier(caster, caster.getIntelligence());
        }
    }

    public void applyBarrier(Character target, int intelligence) {
        int reduction = BASE_REDUCTION + (intelligence / 4);
        int duration = BASE_DURATION + (intelligence / 10);
        // Assuming DefenseUpStatus takes (duration, value)
        target.applyStatusEffect(StatusType.DEFENSE_UP_STATUS, duration, reduction, null);
    }

    @Override
    public void cast() { }

    @Override
    public void cast(Character caster, Character target) {
        if (canUseSpell(caster) && target != null) {
            applyBarrier(target, caster.getIntelligence());
        }
    }

    @Override
    public String getDescription() {
        return "Mystic Barrier: Surrounds the target with a magical shield, reducing incoming damage for several turns. Effectiveness scales with intelligence.";
    }

    @Override
    public void castWithStrength(Character enemy, double strength) { }

	@Override
	public void cast(Character caster, Enemies target) {
		// TODO Auto-generated method stub
		
	}
}
