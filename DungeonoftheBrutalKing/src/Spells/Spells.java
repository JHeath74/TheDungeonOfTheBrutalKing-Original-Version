
// File: Spells.java
package Spells;

public interface Spells {
    void cast();
    boolean isGuildSpell();
	void cast(int attackerWisdom);
	SpellAlignment getSpellAlignment();
}
