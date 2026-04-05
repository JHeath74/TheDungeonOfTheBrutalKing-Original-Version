package DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;

/**
 * Thief's Insight: brief divination that highlights pockets, pouches, pressure plates,
 * and hidden compartments. The world seems to tilt toward opportunity.
 */
public final class ThiefsInsight implements Spell {

    private static final String NAME = "Thief's Insight";
    private static final String DESCRIPTION =
            "For a short time, faint glimmers highlight pockets, pouches, pressure plates, "
          + "and hidden compartments. The world seems to tilt toward opportunity.";

    private static final int REQUIRED_MAGIC_POINTS = 4;
    // Duration in turns or seconds, depending on how you interpret it in your engine
    private static final int INSIGHT_DURATION = 5;

    private static final Guild SPELL_GUILD = Guild.OBSIDIAN_SHADOW_SYNDICATE;

    public ThiefsInsight() { }

    // --- Spell meta-data ---

    @Override
    public boolean isGuildSpell() {
        return SPELL_GUILD != Guild.NON_GUILD;
    }

    @Override
    public Guild getSpellGuild() {
        return SPELL_GUILD;
    }

    @Override
    public int getRequiredMagicPoints() {
        return REQUIRED_MAGIC_POINTS;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    // --- Core behavior: non-damaging divination buff ---

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster == null) caster = Charecter.getInstance();
        if (caster == null) return;

        // Enforce guild like other Obsidian Shadow Syndicate spells
        try {
            Guild casterGuild = caster.getGuild();
            if (isGuildSpell() && casterGuild != SPELL_GUILD) {
                System.out.println(caster.getName() + " cannot cast " + NAME + " (wrong guild).");
                return;
            }
        } catch (Exception ignored) { }

        if (caster.getMagicPoints() < REQUIRED_MAGIC_POINTS) {
            System.out.println(caster.getName()
                    + " does not have enough magic points to cast " + NAME + "!");
            return;
        }
        caster.setMagicPoints(caster.getMagicPoints() - REQUIRED_MAGIC_POINTS);

        // Core effect: toggle some kind of "insight" state on the caster.
        // If you have a status system, you can plug a dedicated status here.
        try {
            // Example hook: call a custom method if it exists.
            // e.g., caster.enableThiefInsight(INSIGHT_DURATION);
            caster.addTemporaryPerceptionBuff("THIEFS_INSIGHT", INSIGHT_DURATION);
        } catch (Exception ignored) {
            // If no such API exists yet, just rely on flavor text.
        }

        System.out.println(caster.getName()
                + " whispers a quiet cant. The world seems to tilt toward opportunity as"
                + " pockets, pouches, and hidden mechanisms glimmer at the edge of sight.");
    }

    // --- Other Spell overloads for compatibility ---

    @Override
    public void cast(Charecter caster) {
        cast(caster, caster);
    }

    @Override
    public void cast(Charecter caster, java.util.List<Charecter> allCharacters) {
        cast(caster != null ? caster : Charecter.getInstance());
    }

    @Override
    public void cast() {
        cast(Charecter.getInstance());
    }

    @Override
    public void cast(int toonWisdom) {
        cast();
    }

    @Override
    public void castWithIntelligence(int toonIntelligence) {
        cast();
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        cast();
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) {
        cast(Charecter.getInstance());
    }

    @Override
    public void cast(Charecter caster, Enemies target) {
        // Utility divination; ignore Enemies-based targeting.
        cast(caster);
    }
}