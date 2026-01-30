
package Guild.CelestialArcaneOrder.Spells;

import SharedData.Guild;
import Spells.Spell;
import DungeonoftheBrutalKing.Charecter;
import java.util.List;

public class RadiantBolt implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 10;
    private static final Guild SPELL_GUILD = Guild.CELESTIAL_ARCANE_ORDER;
    private static final String SPELL_NAME = "Radiant Bolt";
    private static final int BASE_DAMAGE = 25;

    public RadiantBolt() {}

    // Core spell logic: deals radiant damage to the target character
    private void dealRadiantDamage(Charecter caster, Charecter target) {
        if (target == null) return;
        int intelligence = caster != null ? caster.getIntelligence() : 0;
        int damage = BASE_DAMAGE + intelligence;
        int currentHealth = target.getHitPoints();
        int newHealth = Math.max(currentHealth - damage, 0);

        target.setHitPoints(newHealth);

        System.out.println(target.getName() + " takes " + (currentHealth - newHealth) + " radiant damage from " + SPELL_NAME + "!");
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        dealRadiantDamage(caster, target);
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        if (allCharacters != null && !allCharacters.isEmpty()) {
            for (Charecter ch : allCharacters) {
                dealRadiantDamage(caster, ch);
            }
        }
    }

    @Override
    public void cast(Charecter caster) {
        // Not applicable: needs a target
    }

    @Override
    public void cast() {
        // Not applicable: needs a target
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
