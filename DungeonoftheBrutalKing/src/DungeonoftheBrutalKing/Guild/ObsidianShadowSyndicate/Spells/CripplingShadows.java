
package DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Spells;

import java.util.List;
import java.util.Random;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Status.StatusType;

/**
 * CripplingShadows: a subtle strike that weakens the target's agility,
 * making them slower and easier to hit for a short time.
 */
public final class CripplingShadows implements Spell {

    private static final String NAME = "Crippling Shadows";
    private static final String DESCRIPTION =
            "Ensnare your foe in binding shadows, slightly harming them and "
          + "greatly reducing their agility for a few turns.";

    // MP cost
    private static final int REQUIRED_MAGIC_POINTS = 6;

    // Damage parameters (lighter than pure attack spells)
    private static final double BASE_HP_PERCENT = 0.03;   // 3% of caster max HP
    private static final double AGILITY_SCALING = 0.2;    // 0.2 damage per agility

    // Hit and debuff parameters
    private static final double BASE_HIT_CHANCE = 0.9;    // 90% base hit
    private static final int AGILITY_PENALTY = 10;        // agility reduction
    private static final int DEBUFF_DURATION_TURNS = 3;   // duration

    // Guild restriction
    private static final Guild SPELL_GUILD = Guild.OBSIDIAN_SHADOW_SYNDICATE;

    private static final Random RNG = new Random();

    public CripplingShadows() { }

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
    public void cast(Charecter caster, Charecter target) {
        if (caster == null) {
            caster = Charecter.getInstance();
        }
        if (caster == null || target == null) {
            return;
        }

        // Guild enforcement
        try {
            Guild casterGuild = caster.getGuild();
            if (isGuildSpell() && casterGuild != SPELL_GUILD) {
                System.out.println(caster.getName() + " cannot cast " + NAME + " (wrong guild).");
                return;
            }
        } catch (Exception ignored) { }

        // MP check
        if (caster.getMagicPoints() < REQUIRED_MAGIC_POINTS) {
            System.out.println(caster.getName() + " does not have enough magic points to cast " + NAME + "!");
            return;
        }
        caster.setMagicPoints(caster.getMagicPoints() - REQUIRED_MAGIC_POINTS);

        // Hit check
        double hitChance = BASE_HIT_CHANCE;
        try {
            hitChance *= Math.max(0.1, Math.min(1.5, caster.getHitChance()));
        } catch (Exception ignored) { }

        boolean hit = RNG.nextDouble() < hitChance;
        if (!hit) {
            System.out.println(caster.getName() + " calls upon " + NAME +
                    ", but the shadows fail to bind " + target.getName() + "!");
            return;
        }

        // Light damage
        int casterMaxHp = Math.max(0, caster.getMaxHealth());
        int targetHp    = Math.max(0, target.getHitPoints());
        int agility     = Math.max(0, caster.getAgility());

        int damageFromHp  = (int) Math.round(casterMaxHp * BASE_HP_PERCENT);
        int damageFromAgi = (int) Math.round(agility * AGILITY_SCALING);
        int totalDamage   = Math.max(1, damageFromHp + damageFromAgi);

        int dealt = Math.min(totalDamage, targetHp);
        target.setHitPoints(targetHp - dealt);

        System.out.println(caster.getName() + " wraps " + target.getName() +
                " in crippling shadows, dealing " + dealt + " damage!");

        // Apply agility debuff via existing status system on the *target*
        try {
            target.applyStatusEffect(
                    StatusType.AGILITY_DEBUFF_STATUS,
                    DEBUFF_DURATION_TURNS,
                    -AGILITY_PENALTY,
                    caster
            );
            System.out.println(target.getName() + "'s movements slow, losing " +
                    AGILITY_PENALTY + " agility for " + DEBUFF_DURATION_TURNS + " turns.");
        } catch (Exception e) {
            // Fallback to direct stat manipulation if status system fails
            try {
                int currentAgi = target.getAgility();
                target.setAgility(currentAgi - AGILITY_PENALTY);
                System.out.println(target.getName() + "'s agility is reduced by " +
                        AGILITY_PENALTY + "!");
            } catch (Exception ignored) {
                // Silent failure
            }
        }
    }

    // --- Overloads for compatibility ---

    @Override
    public void cast(Charecter caster) {
        cast(caster, caster);
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        if (caster == null && allCharacters != null && !allCharacters.isEmpty()) {
            caster = allCharacters.get(0);
        }
        if (caster == null) {
            return;
        }

        Charecter target = caster;
        if (allCharacters != null && allCharacters.size() > 1) {
            target = allCharacters.get(1);
        }
        cast(caster, target);
    }

    @Override
    public void cast() {
        Charecter player = Charecter.getInstance();
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
    public void castWithStrength(Charecter enemy, double d) {
        Charecter caster = Charecter.getInstance();
        if (caster == null) {
            return;
        }
        if (enemy == null) {
            enemy = caster;
        }
        cast(caster, enemy);
    }

    @Override
    public void cast(Charecter caster, Enemies target) {
        // Bridge as needed; for now default to caster vs. self/current foe
        cast(caster != null ? caster : Charecter.getInstance(),
             caster != null ? caster : Charecter.getInstance());
    }
}
