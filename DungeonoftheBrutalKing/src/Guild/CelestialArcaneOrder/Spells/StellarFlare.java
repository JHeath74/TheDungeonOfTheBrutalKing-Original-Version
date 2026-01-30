
package Guild.CelestialArcaneOrder.Spells;

import SharedData.Guild;
import Spells.Spell;
import DungeonoftheBrutalKing.Charecter;
import java.util.List;

public class StellarFlare implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 15;
    private static final Guild SPELL_GUILD = Guild.CELESTIAL_ARCANE_ORDER;
    private static final String SPELL_NAME = "Stellar Flare";
    private static final int BASE_DAMAGE = 18;

    public StellarFlare() {}

    // Deals radiant damage to all targets in the list
    private void dealStellarFlareDamage(Charecter caster, List<Charecter> targets) {
        if (targets == null || targets.isEmpty()) return;
        int intelligence = caster != null ? caster.getIntelligence() : 0;
        int damage = BASE_DAMAGE + intelligence;
        for (Charecter target : targets) {
            if (target == null) continue;
            int currentHealth = target.getHitPoints();
            int newHealth = Math.max(currentHealth - damage, 0);
            target.setHitPoints(newHealth);
            System.out.println(target.getName() + " takes " + (currentHealth - newHealth) + " radiant damage from " + SPELL_NAME + "!");
        }
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        // Single target version
        dealStellarFlareDamage(caster, List.of(target));
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        dealStellarFlareDamage(caster, allCharacters);
    }

    @Override
    public void cast(Charecter caster) {
        // Not applicable: needs targets
    }

    @Override
    public void cast() {
        // Not applicable: needs targets
    }

    @Override
    public void cast(int toonWisdom) {
        // Not used for this spell
    }

    @Override
    public void castWithIntelligence(int toonIntelligence) {
        // Not used for this spell
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        // Not used for this spell
    }

    @Override
    public boolean isGuildSpell() {
        return true;
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
        return SPELL_NAME;
    }
}
