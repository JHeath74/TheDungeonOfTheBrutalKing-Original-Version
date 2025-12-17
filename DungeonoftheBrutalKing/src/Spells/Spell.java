package Spells;

import SharedData.Guild;

public interface Spell {
    boolean isGuildSpell();
    Guild getSpellGuild();
    int getRequiredMagicPoints();
    void cast(int toonWisdom);
    void castWithIntelligence(int toonIntelligence);
    void cast(int toonWisdom, int toonIntelligence);
    String getName();
}