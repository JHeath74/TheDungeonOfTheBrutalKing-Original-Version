package Guild.CelestialArcaneOrder.Spells;

import SharedData.Guild;
import Spells.Spell;
import DungeonoftheBrutalKing.Charecter;
import java.util.List;

public class Heal implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 8;
    private static final Guild SPELL_GUILD = Guild.CELESTIAL_ARCANE_ORDER;
    private static final String SPELL_NAME = "Heal";

    public Heal() {}

    // Core spell logic: heals the target character
    private void healCharacter(Charecter target) {
        if (target == null) return;
        int intelligence = target.getIntelligence();
        int maxHealth = target.getMaxHitPoints();
        int currentHealth = target.getHitPoints();

        int healthRestored = 10 + intelligence;
        int newHealth = Math.min(currentHealth + healthRestored, maxHealth);

        target.setHitPoints(newHealth);

        System.out.println(target.getName() + " restored " + (newHealth - currentHealth) + " health points!");
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        healCharacter(target != null ? target : caster);
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        if (allCharacters != null && !allCharacters.isEmpty()) {
            for (Charecter ch : allCharacters) {
                healCharacter(ch);
            }
        }
    }

    @Override
    public void cast(Charecter caster) {
        healCharacter(caster);
    }

    @Override
    public void cast() {
        // Not applicable: requires a character
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
