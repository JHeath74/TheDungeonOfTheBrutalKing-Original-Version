
// File: Spells.java
package Spells;

public interface Spell {
    void cast();
    boolean isGuildSpell();
    void cast(int attackerWisdom);
    SharedData.Alignment getSpellAlignment();
    int getRequiredMagicPoints(); // Added method
}
