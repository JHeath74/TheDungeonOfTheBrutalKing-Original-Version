package DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Spells;

import java.util.List;
import java.util.Random;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Status.Status;
import DungeonoftheBrutalKing.Status.DazeStatus;

/**
 * DazingStrike: attempts to daze the target. If daze succeeds, the hit is a strong crit;
 * if it fails, the hit still lands but for reduced damage.
 */
public final class DazingStrike implements Spell {

    private static final String NAME = "Dazing Strike";
    private static final String DESCRIPTION =
            "Attempts to daze the target. A successful daze turns the blow into a heavy critical hit; "
          + "a failed attempt results in only a glancing strike.";

    private static final int REQUIRED_MAGIC_POINTS = 6;
    // Base percent of caster's max HP as damage
    private static final double BASE_DAMAGE_PERCENT = 0.08;

    // Crit vs. glancing multipliers
    private static final double CRIT_MULTIPLIER = 2.0;
    private static final double GRAZING_MULTIPLIER = 0.6;

    // Chance to daze (and crit) in [0.0, 1.0]
    private static final double DAZE_CHANCE = 0.45;

    // Daze status duration (turns)
    private static final int DAZE_DURATION_TURNS = 2;

    // Guild allowed to cast this spell
    private static final Guild SPELL_GUILD = Guild.OBSIDIAN_SHADOW_SYNDICATE;

    private static final Random RNG = new Random();

    public DazingStrike() { }

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

        // Optional guild enforcement
        try {
            Guild casterGuild = caster.getGuild();
            if (isGuildSpell() && casterGuild != SPELL_GUILD) {
                System.out.println(caster.getName() + " cannot cast " + NAME + " (wrong guild).");
                return;
            }
        } catch (Exception ignored) { }

        // Magic points check
        if (caster.getMagicPoints() < REQUIRED_MAGIC_POINTS) {
            System.out.println(caster.getName() + " does not have enough magic points to cast " + NAME + "!");
            return;
        }
        caster.setMagicPoints(caster.getMagicPoints() - REQUIRED_MAGIC_POINTS);

        int casterMaxHp = Math.max(0, caster.getMaxHealth());
        int targetHp = Math.max(0, target.getHitPoints());

        int baseDamage = (int) Math.round(casterMaxHp * BASE_DAMAGE_PERCENT);
        baseDamage = Math.max(1, baseDamage);

        boolean dazed = RNG.nextDouble() < DAZE_CHANCE;

        int finalDamage = dazed
                ? (int) Math.round(baseDamage * CRIT_MULTIPLIER)
                : (int) Math.round(baseDamage * GRAZING_MULTIPLIER);
        finalDamage = Math.max(1, finalDamage);

        int dealt = Math.min(finalDamage, targetHp);
        target.setHitPoints(targetHp - dealt);

        if (dazed) {
            System.out.println(caster.getName() + " lands a dazing critical strike with " + NAME +
                    ", dealing " + dealt + " damage to " + target.getName() + "!");

            // Apply DazeStatus from the Status package
            try {
                Status daze = new DazeStatus(DAZE_DURATION_TURNS);
                if (target.getStatusManager() != null) {
                    target.getStatusManager().addStatus(daze, target);
                } else {
                    target.addStatus(daze);
                }
            } catch (Exception ignored) { }
        } else {
            System.out.println(caster.getName() + " fails to daze with " + NAME +
                    ", landing only a glancing blow for " + dealt +
                    " damage on " + target.getName() + ".");
        }
    }

    // --- Other Spell overloads for compatibility ---

    @Override
    public void cast(Character caster) {
        cast(caster, caster);
    }

    @Override
    public void cast(Character caster, List<Character> allCharacters) {
        if (caster == null && allCharacters != null && !allCharacters.isEmpty()) {
            caster = allCharacters.get(0);
        }
        if (caster == null) return;

        Character target = caster;
        if (allCharacters != null && allCharacters.size() > 1) {
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
        cast(caster != null ? caster : Character.getInstance(),
             caster != null ? caster : Character.getInstance());
    }
}
