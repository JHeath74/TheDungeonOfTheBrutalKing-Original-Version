package Guild.SilverwardSentinels.Spells;

import java.util.Random;
import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;
import Spells.SpellBalanceManager;
import Status.ImmobilizedStatus;
import Status.StatusManager;

/**
 * Dawnbind - a Silverward Sentinels attack that deals radiant damage and
 * has a chance to immobilize the enemy (prevent movement) for a short time.
 */
public final class Dawnbind implements Spell {

    private static final String NAME = "Dawnbind";

    private static final int REQUIRED_MP = SpellBalanceManager.getInt("Dawnbind.requiredMp", 6);
    private static final int BASE_DAMAGE = SpellBalanceManager.getInt("Dawnbind.baseDamage", 9);
    private static final double STRENGTH_SCALING = SpellBalanceManager.getDouble("Dawnbind.strengthScaling", 0.45);
    private static final double IMMOB_CHANCE = SpellBalanceManager.getDouble("Dawnbind.immobilizeChance", 0.25);
    private static final int IMMOB_DURATION = SpellBalanceManager.getInt("Dawnbind.immobilizeDurationMinutes", 2);

    public Dawnbind() {}

    @Override public String getName() { return NAME; }
    @Override public boolean isGuildSpell() { return true; }
    @Override public Guild getSpellGuild() { return Guild.SILVERWARD_SENTINELS; }
    @Override public int getRequiredMagicPoints() { return REQUIRED_MP; }
    @Override public String getDescription() { return "A radiant strike that may bind the foe, preventing movement for a short time."; }

    private void applyDawnbind(Charecter caster, Enemies target) {
        if (caster == null || target == null) return;

        // Guild check
        Guild g = caster.getGuild();
        if (g == null || g != getSpellGuild()) return;

        // MP check
        if (caster.getMagicPoints() < REQUIRED_MP) return;
        caster.setMagicPoints(Math.max(0, caster.getMagicPoints() - REQUIRED_MP));

        int damage = computeDamage(caster);
        int newHp = Math.max(0, target.getHitPoints() - damage);
        target.setHitPoints(newHp);
        System.out.println(caster.getName() + " hits " + target.getName() + " with Dawnbind for " + damage + " damage.");

        // Chance to immobilize
        Random rnd = new Random();
        if (rnd.nextDouble() < IMMOB_CHANCE) {
            ImmobilizedStatus imm = new ImmobilizedStatus(Math.max(1, IMMOB_DURATION));
            try {
                java.lang.reflect.Method m = target.getClass().getMethod("getStatusManager");
                Object sm = m.invoke(target);
                if (sm instanceof StatusManager) {
                    ((StatusManager) sm).addStatus(imm, null);
                } else {
                    target.addStatus(imm);
                }
            } catch (NoSuchMethodException nsme) {
                target.addStatus(imm);
            } catch (Exception ignored) {
                try { target.addStatus(imm); } catch (Exception ignored2) { }
            }
            System.out.println(target.getName() + " is immobilized by Dawnbind for " + IMMOB_DURATION + " minutes!");
        }
    }

    private int computeDamage(Charecter caster) {
        int str = Math.max(0, caster.getStrength());
        double dmg = BASE_DAMAGE + (str * STRENGTH_SCALING);
        return (int) Math.max(1, Math.round(dmg));
    }

    // Spell interface stubs
    @Override public void cast() {}
    @Override public void cast(int toonWisdom) {}
    @Override public void castWithIntelligence(int toonIntelligence) {}
    @Override public void cast(int toonWisdom, int toonIntelligence) {}
    @Override public void cast(Charecter caster) {}
    @Override public void cast(Charecter caster, Charecter target) {}
    @Override public void cast(Charecter caster, List<Charecter> allCharacters) {}
    @Override public void castWithStrength(Charecter enemy, double d) {}
    @Override public void cast(Charecter caster, Enemies target) { applyDawnbind(caster, target); }
}
