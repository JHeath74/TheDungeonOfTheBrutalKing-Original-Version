package DungeonoftheBrutalKing.Guild.CelestialArcaneOrder.Spells;

import java.util.List;
import java.util.Random;
import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;

public class Cure implements Spell {

    private static final Guild SPELL_GUILD = Guild.CELESTIAL_ARCANE_ORDER;
    private static final int REQUIRED_MAGIC_POINTS = 5;
    private static final String[] NEGATIVE_EFFECTS = {"Poison", "Blindness", "Paralysis"};

    public Cure() {}

    // Core spell logic: attempts to cure negative effects for the target character
    private void cureEffects(Charecter target) {
        if (target == null) return;
        Random random = new Random();
        for (String effect : NEGATIVE_EFFECTS) {
            if (random.nextDouble() < 0.75) {
                // pass target to match StatusManager.removeStatusByName(String, Charecter)
                target.getStatusManager().removeStatusByName(effect, target);
                System.out.println(target.getName() + " cured " + effect + "!");
            } else {
                System.out.println(target.getName() + " failed to cure " + effect + ".");
            }
        }
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        cureEffects(target != null ? target : caster);
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        if (allCharacters != null && !allCharacters.isEmpty()) {
            for (Charecter ch : allCharacters) {
                cureEffects(ch);
            }
        }
    }

    @Override
    public void cast(Charecter caster) {
        cureEffects(caster);
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
        return "Cure";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) {
        // Not used for this spell
    }

    @Override
    public void cast(Charecter caster, Enemies target) {
        // Not used for this spell
    }
}
