
// File: Spells.java
package Spells;

import SharedData.Alignment;

public interface Spell {
    void cast();
    boolean isGuildSpell();
    void cast(int attackerWisdom);
    Alignment getSpellAlignment();
}
