package DungeonoftheBrutalKing.Guild.NightShadeHunters.Spells;

public interface LocalSpell {
    boolean isGuildSpell();
    // Use String for guild name to avoid depending on SharedData types
    String getSpellGuild();
    int getRequiredMagicPoints();
    String getName();
    String getDescription();
    // Lightweight/no-op casting signatures to avoid depending on project-wide Charecter/Enemies types
    void cast();
    void cast(Object caster);
    void cast(Object caster, Object target);
}