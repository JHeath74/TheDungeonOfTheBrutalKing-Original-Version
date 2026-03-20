package Guild.ObsidianShadowSyndicate.Spells;

import java.util.List;
import java.util.Random;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;
import Status.StatusType;

/**
 * ShadowSlash: a swift strike from the shadows that deals damage
 * and briefly heightens the caster's evasiveness on a successful hit.
 */
public final class ShadowSlash implements Spell {

    private static final String NAME = "Shadow Slash";
    private static final String DESCRIPTION =
            "Strike from the shadows, dealing damage that scales with agility. "
          + "On a successful hit, your form blurs, increasing your chance to evade attacks for a short time.";

    // MP cost
    private static final int REQUIRED_MAGIC_POINTS = 5;

    // Damage based on caster max HP and agility
    private static final double BASE_HP_PERCENT = 0.06;   // 6% of max HP
    private static final double AGILITY_SCALING   = 0.4;  // 0.4 damage per point of agility

    // Hit and evade\-buff parameters
    private static final double BASE_HIT_CHANCE = 0.85;   // 85% base hit
    private static final int EVADE_BONUS = 12;            // evasion points
    private static final int EVADE_DURATION_TURNS = 3;    // buff duration

    // Guild restriction
    private static final Guild SPELL_GUILD = Guild.OBSIDIAN_SHADOW_SYNDICATE;

    private static final Random RNG = new Random();

    public ShadowSlash() { }

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
        if (caster == null) caster = Charecter.getInstance();
        if (caster == null || target == null) return;

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

        // Hit check: base hit modified by caster accuracy/evasion logic
        double hitChance = BASE_HIT_CHANCE;
        try {
            hitChance *= Math.max(0.1, Math.min(1.5, caster.getHitChance()));
        } catch (Exception ignored) { }

        boolean hit = RNG.nextDouble() < hitChance;
        if (!hit) {
            System.out.println(caster.getName() + " lunges with " + NAME +
                    ", but the attack misses " + target.getName() + "!");
            return;
        }

        int casterMaxHp = Math.max(0, caster.getMaxHealth());
        int targetHp    = Math.max(0, target.getHitPoints());
        int agility     = Math.max(0, caster.getAgility());

        int damageFromHp  = (int) Math.round(casterMaxHp * BASE_HP_PERCENT);
        int damageFromAgi = (int) Math.round(agility * AGILITY_SCALING);
        int totalDamage   = Math.max(1, damageFromHp + damageFromAgi);

        int dealt = Math.min(totalDamage, targetHp);
        target.setHitPoints(targetHp - dealt);

        System.out.println(caster.getName() + " strikes from the shadows with " + NAME +
                ", dealing " + dealt + " damage to " + target.getName() + "!");

        // On hit: grant temporary evasion boost via status system
        try {
            // Use the existing status pipeline: applyStatusEffect(StatusType, duration, value, source)
            caster.applyStatusEffect(StatusType.EVASION_STATUS,
                                     EVADE_DURATION_TURNS,
                                     EVADE_BONUS,
                                     caster);
            System.out.println(caster.getName() + " becomes harder to hit, gaining +" +
                    EVADE_BONUS + " evasion for " + EVADE_DURATION_TURNS + " turns.");
        } catch (Exception ignored) {
            // Fallback: directly tweak evasion if status pipeline is unavailable
            try {
                int currentEvade = caster.getEvasion();
                caster.setEvasion(currentEvade + EVADE_BONUS);
            } catch (Exception ignoredToo) {
                // Silent failure if neither path is available.
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
        if (caster == null) return;

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
        if (caster == null) return;
        if (enemy == null) enemy = caster;
        cast(caster, enemy);
    }

    @Override
    public void cast(Charecter caster, Enemies target) {
        // If you have a bridge from Enemies to Charecter, adapt here. For now, hit the caster's current foe or self.
        cast(caster != null ? caster : Charecter.getInstance(),
             caster != null ? caster : Charecter.getInstance());
    }
}
