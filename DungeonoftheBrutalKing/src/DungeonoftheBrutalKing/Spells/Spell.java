package DungeonoftheBrutalKing.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;

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
	void cast(Character caster, List<Character> allCharacters);
	void cast(Character caster);
	void cast();
	void cast(Character caster, Character target);
	String getDescription();
	void castWithStrength(Character enemy, double d);
	void cast(Character caster, Enemies target);
}