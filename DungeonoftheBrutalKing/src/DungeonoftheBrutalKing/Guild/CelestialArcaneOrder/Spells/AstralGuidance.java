package Guild.CelestialArcaneOrder.Spells;

import SharedData.Guild;
import Spells.Spell;
import DungeonoftheBrutalKing.Charecter;
import Status.StatusType; // Import the StatusType enum

public class AstralGuidance implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 7;
    private static final Guild SPELL_GUILD = Guild.CELESTIAL_ARCANE_ORDER;
    private static final String SPELL_NAME = "Astral Guidance";
    private static final int ACCURACY_BONUS = 15;
    private static final int DURATION = 1;

    public AstralGuidance() {}

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (target == null) return;
        target.applyStatusEffect(StatusType.ACCURACY_STATUS, DURATION, ACCURACY_BONUS, target);
        System.out.println(target.getName() + "'s accuracy is increased by " + ACCURACY_BONUS + " for " + DURATION + " turn by Astral Guidance!");
    }

    // Other cast methods not used for this spell
    @Override public void cast(Charecter caster, java.util.List<Charecter> allCharacters) {}
    @Override public void cast(Charecter caster) {}
    @Override public void cast() {}
    @Override public void cast(int toonWisdom) {}
    @Override public void castWithIntelligence(int toonIntelligence) {}
    @Override public void cast(int toonWisdom, int toonIntelligence) {}

    @Override public boolean isGuildSpell() { return true; }
    @Override public Guild getSpellGuild() { return SPELL_GUILD; }
    @Override public int getRequiredMagicPoints() { return REQUIRED_MAGIC_POINTS; }
    @Override public String getName() { return SPELL_NAME; }

    @Override
    public String getDescription() {
        return "Astral Guidance: Increases a target's accuracy for a short duration.";
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) {
        // Not used for this spell
    }
}
