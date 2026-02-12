
package Guild.CelestialArcaneOrder.Spells;

import Spells.Spell;
import Status.AstralWardStatus;
import Status.Status;

import java.util.List;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;

public class AstralWard implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 12;
    private static final Guild SPELL_GUILD = Guild.CELESTIAL_ARCANE_ORDER;
    private static final String SPELL_NAME = "Astral Ward";

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (target == null) return;
        Status status = new AstralWardStatus(5); // 5 minutes duration
        target.addStatus(status);
        System.out.println(target.getName() + " is protected by an Astral Ward!");
    }

    @Override
    public String getName() { return SPELL_NAME; }

    @Override
    public Guild getSpellGuild() { return SPELL_GUILD; }

    @Override
    public boolean isGuildSpell() { return true; }

    @Override
    public int getRequiredMagicPoints() { return REQUIRED_MAGIC_POINTS; }

    @Override
    public String getDescription() { return "Grants a temporary shield that absorbs damage for 5 minutes."; }

    // Stub implementations for required interface methods
    @Override
    public void cast() {}

    @Override
    public void cast(Charecter caster) {}

    @Override
    public void cast(int x) {}

    @Override
    public void cast(int x, int y) {}

    @Override
    public void cast(Charecter caster, List<Charecter> targets) {}

    @Override
    public void castWithStrength(Charecter caster, double strength) {}

    @Override
    public void castWithIntelligence(int intelligence) {}
}
