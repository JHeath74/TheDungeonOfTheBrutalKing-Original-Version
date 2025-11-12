
// File: Spells.java
package Spells;

import Alignment.Alignment;

public interface Spell {
    void cast();
    boolean isGuildSpell();
    void cast(int attackerWisdom);
    Alignment getSpellAlignment();
    int getRequiredMagicPoints(); // Added method
}
