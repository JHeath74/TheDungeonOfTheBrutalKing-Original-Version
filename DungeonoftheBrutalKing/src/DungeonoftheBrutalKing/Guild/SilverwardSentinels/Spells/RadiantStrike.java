package Guild.SilverwardSentinels.Spells;

import java.util.Random;
import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;
import Status.JudgementBrandStatus;
import Status.Status;
import Status.StatusManager;
import Spells.SpellBalanceManager;

/**
 * RadiantStrike - a Silverward offensive spell that deals damage and has a
 * chance to apply Judgement Brand (increases damage taken) to the enemy.
 */
public final class RadiantStrike implements Spell {

    private static final String NAME = "Radiant Strike";
    private static final int REQUIRED_MP = SpellBalanceManager.getInt("RadiantStrike.requiredMp", 7);

    private static final int BASE_DAMAGE = (int) SpellBalanceManager.getInt("RadiantStrike.baseDamage", 10);
    private static final double STRENGTH_SCALING = SpellBalanceManager.getDouble("RadiantStrike.strengthScaling", 0.5);
    private static final double BRAND_CHANCE = SpellBalanceManager.getDouble("RadiantStrike.brandChance", 0.30);
    private static final int BRAND_DURATION_MINUTES = SpellBalanceManager.getInt("RadiantStrike.brandDurationMinutes", 3);
    private static final int BRAND_INTENSITY_PERCENT = SpellBalanceManager.getInt("RadiantStrike.brandIntensityPercent", 20);

    public RadiantStrike() {}

    @Override
    public String getName() { return NAME; }

    @Override
    public boolean isGuildSpell() { return true; }

    @Override
    public Guild getSpellGuild() { return Guild.SILVERWARD_SENTINELS; }

    @Override
    public int getRequiredMagicPoints() { return REQUIRED_MP; }

    @Override
    public String getDescription() { return "A concentrated strike of radiant energy that can brand the foe, causing them to take increased damage."; }

    private void applyRadiantStrike(Charecter caster, Enemies target) {
        if (caster == null || target == null) return;

        // Guild check
        Guild g = caster.getGuild();
        if (g == null || g != getSpellGuild()) return;

        // MP check
        if (caster.getMagicPoints() < REQUIRED_MP) return;
        caster.setMagicPoints(Math.max(0, caster.getMagicPoints() - REQUIRED_MP));

        int damage = computeDamage(caster, target);
        int newHp = Math.max(0, target.getHitPoints() - damage);
        target.setHitPoints(newHp);

        System.out.println(caster.getName() + " strikes " + target.getName() + " for " + damage + " radiant damage.");

        // Chance to apply Judgement Brand
        Random rnd = new Random();
        if (rnd.nextDouble() < BRAND_CHANCE) {
            JudgementBrandStatus brand = new JudgementBrandStatus(BRAND_DURATION_MINUTES, BRAND_INTENSITY_PERCENT, caster);
            // Prefer StatusManager if present
            try {
                java.lang.reflect.Method m = target.getClass().getMethod("getStatusManager");
                Object sm = m.invoke(target);
                if (sm instanceof StatusManager) {
                    ((StatusManager) sm).addStatus(brand, null);
                } else {
                    target.addStatus(brand);
                }
            } catch (NoSuchMethodException nsme) {
                target.addStatus(brand);
            } catch (Exception ignored) {
                try { target.addStatus(brand); } catch (Exception ignored2) { }
            }
            System.out.println(target.getName() + " is branded by Radiant Strike and will take increased damage!");
        }
    }

    private int computeDamage(Charecter caster, Enemies target) {
        int strength = Math.max(0, caster.getStrength());
        double dmg = BASE_DAMAGE + (strength * STRENGTH_SCALING);
        return (int) Math.max(1, Math.round(dmg));
    }

    // Spell interface stubs
    @Override public void cast() {}
    @Override public void cast(int toonWisdom) {}
    @Override public void castWithIntelligence(int toonIntelligence) {}
    @Override public void cast(int toonWisdom, int toonIntelligence) {}
    @Override public void cast(Charecter caster) {}
    @Override public void cast(Charecter caster, Charecter target) {}
    @Override public void cast(Charecter caster, java.util.List<Charecter> allCharacters) {}
    @Override public void castWithStrength(Charecter enemy, double d) {}
    @Override public void cast(Charecter caster, Enemies target) { applyRadiantStrike(caster, target); }
}