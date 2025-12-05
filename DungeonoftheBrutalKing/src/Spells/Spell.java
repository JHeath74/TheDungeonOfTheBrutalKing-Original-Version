
// src/Spells/Spell.java
package Spells;

import SharedData.Guild;

public interface Spell {
    void cast();
    boolean isGuildSpell();
    void cast(int attackerWisdom);
    Guild getSpellGuild();
    int getRequiredMagicPoints();
	void cast(int toonWisdom, int toonIntelligence);
}
