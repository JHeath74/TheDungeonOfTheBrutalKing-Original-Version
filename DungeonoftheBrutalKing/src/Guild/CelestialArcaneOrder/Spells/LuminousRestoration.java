
package Guild.CelestialArcaneOrder.Spells;

import SharedData.Guild;
import Spells.Spell;
import DungeonoftheBrutalKing.Charecter;

public class LuminousRestoration implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 9;
    private static final Guild SPELL_GUILD = Guild.CELESTIAL_ARCANE_ORDER;
    private static final String SPELL_NAME = "Luminous Restoration";
    private static final int HEAL_AMOUNT = 12;

    public LuminousRestoration() {}

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (target == null) return;
        target.removeOneNegativeEffect();
        int newHealth = target.getHitPoints() + HEAL_AMOUNT;
        target.setHitPoints(newHealth);
        System.out.println(target.getName() + " is partially cleansed and healed for " + HEAL_AMOUNT + " hit points by Luminous Restoration!");
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
}
