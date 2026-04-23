package DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;

/**
 * ShadowStab: a precise strike from the shadows that deals more damage when the caster is wounded.
 */
public final class ShadowStab implements Spell {

    private static final String NAME = "Shadow Stab";
    private static final String DESCRIPTION =
            "A precise strike from the shadows that deals more damage when the caster is wounded.";

    private static final int REQUIRED_MAGIC_POINTS = 5; // adjust as desired

    // Percent of caster's max HP as damage
    private static final double BASE_DAMAGE_PERCENT = 0.12;
    // Extra damage multiplier when low health
    private static final double LOW_HP_MULTIPLIER = 1.5;
    private static final double LOW_HP_THRESHOLD = 0.5; // 50%

    // Guild allowed to cast this spell
    private static final Guild SPELL_GUILD = Guild.OBSIDIAN_SHADOW_SYNDICATE;

    public ShadowStab() { }

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

    // --- Core single-target behavior ---

    @Override
    public void cast(Character caster, Character target) {
        if (caster == null) caster = Character.getInstance();
        if (caster == null || target == null) return;

        // Optional guild enforcement similar to SmokeStrike
        try {
            Guild casterGuild = caster.getGuild();
            if (isGuildSpell() && casterGuild != SPELL_GUILD) {
                System.out.println(caster.getName() + " cannot cast " + NAME + " (wrong guild).");
                return;
            }
        } catch (Exception ignored) {
            // If Charecter has no getGuild(), skip strict enforcement.
        }

        // Magic points check
        if (caster.getMagicPoints() < REQUIRED_MAGIC_POINTS) {
            System.out.println(caster.getName() + " does not have enough magic points to cast " + NAME + "!");
            return;
        }

        caster.setMagicPoints(caster.getMagicPoints() - REQUIRED_MAGIC_POINTS);

        int casterMaxHp = Math.max(0, caster.getMaxHealth());
        int casterHp = Math.max(0, caster.getHitPoints());
        int targetHp = Math.max(0, target.getHitPoints());

        int damage = (int) Math.round(casterMaxHp * BASE_DAMAGE_PERCENT);
        damage = Math.max(1, damage);

        // Bonus damage if caster is below 50% health
        if (casterHp < casterMaxHp * LOW_HP_THRESHOLD) {
            damage = (int) Math.round(damage * LOW_HP_MULTIPLIER);
        }

        int dealt = Math.min(damage, targetHp);
        target.setHitPoints(targetHp - dealt);

        System.out.println(caster.getName() + " uses " + NAME + " from the shadows, dealing "
                + dealt + " damage to " + target.getName() + ".");
    }

    // --- Other Spell overloads for compatibility ---

    @Override
    public void cast(Character caster) {
        // If only caster is known, treat as self-target (may be used in special flows)
        cast(caster, caster);
    }

    @Override
    public void cast(Character caster, java.util.List<Character> allCharacters) {
        if (caster == null && allCharacters != null && !allCharacters.isEmpty()) {
            caster = allCharacters.get(0);
        }
        if (caster == null) return;

        Character target = caster;
        if (allCharacters != null && allCharacters.size() > 1) {
            // Simple heuristic: second entry as target
            target = allCharacters.get(1);
        }
        cast(caster, target);
    }

    @Override
    public void cast() {
        Character player = Character.getInstance();
        cast(player, player);
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
    public void castWithStrength(Character enemy, double d) {
        Character caster = Character.getInstance();
        if (caster == null) return;
        if (enemy == null) enemy = caster;
        cast(caster, enemy);
    }

    @Override
    public void cast(Character caster, Enemies target) {
        // If the Enemies-API is used, treat as a strike against an enemy abstraction or fallback
        // to buffing / using self if no mapping exists. Here we just use caster as both.
        cast(caster != null ? caster : Character.getInstance(),
             caster != null ? caster : Character.getInstance());
    }
}