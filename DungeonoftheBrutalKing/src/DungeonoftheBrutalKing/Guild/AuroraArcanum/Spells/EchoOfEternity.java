
// src/Guild/AuroraArcanum/Spells/EchoOfEternity.java
package DungeonoftheBrutalKing.Guild.AuroraArcanum.Spells;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Status.EchoOfEternityAuraStatus;
import java.util.List;

public class EchoOfEternity implements Spell {
    private static final int DURATION = 8; // seconds
    private static final Guild SPELL_GUILD = Guild.AURORA_ARCANUM;

    private boolean canUseSpell(Character caster) {
        return caster != null && caster.getGuild() == Guild.AURORA_ARCANUM;
    }

    @Override
    public void cast(Character caster, List<Character> allCharacters) {
        if (canUseSpell(caster)) {
            caster.addStatus(new EchoOfEternityAuraStatus(DURATION, caster));
        }
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
        return 10;
    }

    @Override
    public void cast(int toonWisdom) {
        // No effect for wisdom in current implementation
    }

    @Override
    public void castWithIntelligence(int toonIntelligence) {
        // No effect for intelligence in current implementation
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        // No effect for both stats in current implementation
    }

    @Override
    public String getName() {
        return "Echo of Eternity";
    }

    @Override
    public void cast(Character caster) {
        if (canUseSpell(caster)) {
            caster.addStatus(new EchoOfEternityAuraStatus(DURATION, caster));
        }
    }

    @Override
    public void cast() {
        // No caster provided, nothing to apply
    }

    @Override
    public void cast(Character caster, Character target) {
        if (canUseSpell(caster) && target != null) {
            target.addStatus(new EchoOfEternityAuraStatus(DURATION, caster));
        }
    }

    @Override
    public String getDescription() {
        return "Echo of Eternity: Imbues the target with a mystical aura for a short duration. Only available to AuroraArcanum guild members.";
    }

    @Override
    public void castWithStrength(Character enemy, double strength) {
        // Not applicable for this spell, so do nothing
    }

	@Override
	public void cast(Character caster, Enemies target) {
		// TODO Auto-generated method stub
		
	}
}
