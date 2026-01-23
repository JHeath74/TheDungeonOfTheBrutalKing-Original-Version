
// src/Guild/AuroraArcanum/Spells/EchoOfEternity.java
package Guild.AuroraArcanum.Spells;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import Status.EchoOfEternityAuraStatus;
import java.util.List;

public class EchoOfEternity implements Spell {
    private static final int DURATION = 8; // seconds

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        caster.addStatus(new EchoOfEternityAuraStatus(DURATION, caster));
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
        return 10; // Example value, adjust as needed
    }

    @Override
    public void cast(int toonWisdom) {
        // Implement logic if wisdom affects the spell
    }

    @Override
    public void castWithIntelligence(int toonIntelligence) {
        // Implement logic if intelligence affects the spell
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        // Implement logic if both stats affect the spell
    }

    @Override
    public String getName() {
        return "Echo of Eternity";
    }

	@Override
	public void cast(Charecter caster) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}
}
