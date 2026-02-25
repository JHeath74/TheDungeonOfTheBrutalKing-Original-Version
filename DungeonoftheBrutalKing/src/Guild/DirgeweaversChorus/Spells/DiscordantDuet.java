
package Guild.DirgeweaversChorus.Spells;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildMembershipStatus;
import SharedData.GuildType;
import Spells.Spell;

public class DiscordantDuet implements Spell {

    private static final int MAGIC_POINTS_COST = 12;

    // Damage scaling (level-based fallback)
    private static final int BASE_DAMAGE = 6;
    private static final int DAMAGE_PER_LEVEL = 2;
    private static final int MAX_DAMAGE = 60;

    // 1-enemy mode: restore based on % of enemy HP pool
    private static final double RESTORE_PERCENT_OF_ENEMY_HP = 0.20; // 20%

    // Random outcome weights (must sum to 100)
    private static final int CHANCE_HP_ONLY = 45;
    private static final int CHANCE_MP_ONLY = 35;
    private static final int CHANCE_BOTH_SMALL = 20;

    // Both-small multipliers
    private static final double BOTH_SMALL_HP_MULT = 0.50;
    private static final double BOTH_SMALL_MP_MULT = 0.35;

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        if (caster == null || allCharacters == null) return;

        if (caster.getCurrentGuildStatus() != GuildMembershipStatus.FULL_MEMBER) return;
        if (caster.getCurrentGuild() != GuildType.BARD) return;

        if (caster.getMagicPoints() < MAGIC_POINTS_COST) return;

        List<Charecter> enemies = extractEnemiesExcludingCaster(caster, allCharacters);
        if (enemies.size() != 1 && enemies.size() != 2) return;

        caster.setMagicPoints(caster.getMagicPoints() - MAGIC_POINTS_COST);

        int damage = computeDamage(caster);

        if (enemies.size() == 1) {
            Charecter enemy = enemies.get(0);

            // Damage enemy
            enemy.setHitPoints(Math.max(0, enemy.getHitPoints() - damage));

            // Random restore: HP, MP, or small amount of both (based on enemy HP pool)
            int baseRestore = computeRestoreFromEnemyHp(enemy);
            applyRandomRestore(caster, baseRestore);
            return;
        }

        // Exactly 2 enemies: make them "attack each other" 1 or 2 times.
        Charecter e1 = enemies.get(0);
        Charecter e2 = enemies.get(1);

        int swings = ThreadLocalRandom.current().nextInt(1, 3); // 1..2
        for (int i = 0; i < swings; i++) {
            if (e1.getHitPoints() > 0) {
                e2.setHitPoints(Math.max(0, e2.getHitPoints() - damage));
            }
            if (e2.getHitPoints() > 0) {
                e1.setHitPoints(Math.max(0, e1.getHitPoints() - damage));
            }
            if (e1.getHitPoints() <= 0 || e2.getHitPoints() <= 0) break;
        }
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster == null || target == null) return;

        if (caster.getCurrentGuildStatus() != GuildMembershipStatus.FULL_MEMBER) return;
        if (caster.getCurrentGuild() != GuildType.BARD) return;

        if (caster.getMagicPoints() < MAGIC_POINTS_COST) return;
        caster.setMagicPoints(caster.getMagicPoints() - MAGIC_POINTS_COST);

        int damage = computeDamage(caster);
        target.setHitPoints(Math.max(0, target.getHitPoints() - damage));
    }

    private void applyRandomRestore(Charecter caster, int baseRestore) {
        if (caster == null || baseRestore <= 0) return;

        int roll = ThreadLocalRandom.current().nextInt(100); // 0..99

        if (roll < CHANCE_HP_ONLY) {
            healHitPointsClamped(caster, baseRestore);
            return;
        }

        if (roll < CHANCE_HP_ONLY + CHANCE_MP_ONLY) {
            restoreMagicPointsClamped(caster, baseRestore);
            return;
        }

        int hpSmall = (int) Math.floor(baseRestore * BOTH_SMALL_HP_MULT);
        int mpSmall = (int) Math.floor(baseRestore * BOTH_SMALL_MP_MULT);
        healHitPointsClamped(caster, hpSmall);
        restoreMagicPointsClamped(caster, mpSmall);
    }

    private List<Charecter> extractEnemiesExcludingCaster(Charecter caster, List<Charecter> allCharacters) {
        List<Charecter> enemies = new ArrayList<>();
        for (Charecter c : allCharacters) {
            if (c == null) continue;
            if (c == caster) continue;
            enemies.add(c);
        }
        return enemies;
    }

    private int computeDamage(Charecter caster) {
        int level = 1;
        try {
            level = Math.max(1, caster.getLevel());
        } catch (Throwable ignored) {
        }
        int dmg = BASE_DAMAGE + (level * DAMAGE_PER_LEVEL);
        return Math.min(MAX_DAMAGE, dmg);
    }

    private int computeRestoreFromEnemyHp(Charecter enemy) {
        int enemyHpPool = Math.max(0, safeGetMaxHitPoints(enemy));
        if (enemyHpPool <= 0) {
            enemyHpPool = Math.max(0, enemy.getHitPoints());
        }

        int restore = (int) Math.floor(enemyHpPool * RESTORE_PERCENT_OF_ENEMY_HP);
        return Math.max(0, restore);
    }

    private int safeGetMaxHitPoints(Charecter c) {
        try {
            return c.getMaxHitPoints();
        } catch (Throwable ignored) {
            return 0;
        }
    }

    private void healHitPointsClamped(Charecter target, int amount) {
        if (target == null || amount <= 0) return;

        int current = Math.max(0, target.getHitPoints());

        int max;
        try {
            max = target.getMaxHitPoints();
        } catch (Throwable ignored) {
            target.setHitPoints(current + amount);
            return;
        }

        int newHp = Math.min(Math.max(0, max), current + amount);
        target.setHitPoints(newHp);
    }

    private void restoreMagicPointsClamped(Charecter target, int amount) {
        if (target == null || amount <= 0) return;

        int current = Math.max(0, target.getMagicPoints());

        int max;
        try {
            max = target.getMaxMagicPoints();
        } catch (Throwable ignored) {
            target.setMagicPoints(current + amount);
            return;
        }

        int newMp = Math.min(Math.max(0, max), current + amount);
        target.setMagicPoints(newMp);
    }

    @Override
    public boolean isGuildSpell() { return true; }

    @Override
    public Guild getSpellGuild() { return Guild.DIRGEWEAVERS_CHORUS; }

    @Override
    public int getRequiredMagicPoints() { return MAGIC_POINTS_COST; }

    @Override
    public void cast(int toonWisdom) { }

    @Override
    public void castWithIntelligence(int toonIntelligence) { }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) { }

    @Override
    public String getName() { return "Discordant Duet"; }

    @Override
    public void cast(Charecter caster) { }

    @Override
    public void cast() { }

    @Override
    public String getDescription() {
        return "If facing 1 enemy: damage them and randomly restore HP, MP, or a small amount of both based on their health. If facing 2 enemies: turn their aggression on each other.";
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) { }
}
