package Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;

public interface Spell {
    boolean isGuildSpell();
    Guild getSpellGuild();
    int getRequiredMagicPoints();
    void cast(int toonWisdom);
    void castWithIntelligence(int toonIntelligence);
    void cast(int toonWisdom, int toonIntelligence);
    String getName();
	static Spell createGuildSpell(String spellName, Guild guild) {
		// TODO Auto-generated method stub
		return null;
	}
	void cast(Charecter caster, List<Charecter> allCharacters);
	void cast(Charecter caster);
	void cast();
	void cast(Charecter caster, Charecter target);
}