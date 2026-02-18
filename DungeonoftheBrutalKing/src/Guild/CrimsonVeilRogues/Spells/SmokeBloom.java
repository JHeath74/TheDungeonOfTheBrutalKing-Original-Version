
// src/Guild/CrimsonVeilRogues/Spells/SmokeBloom.java
package Guild.CrimsonVeilRogues.Spells;

import java.util.List;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import Status.StatusType;

public class SmokeBloom implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 7;
    private static final int DEFENSE_BUFF = 6;
    private static final int DURATION = 2;
    private static final Guild SPELL_GUILD = Guild.CRIMSON_VEIL_ROGUES;

    private final String name = "Smoke Bloom";
    private final String description = "A smoke pellet bursts, obscuring vision and allowing the rogue to hide or reposition, increasing their defense temporarily.";

    @Override
    public String getName() { return name; }

    @Override
    public String getDescription() { return description; }

    @Override
    public boolean isGuildSpell() { return true; }

    @Override
    public Guild getSpellGuild() { return SPELL_GUILD; }

    @Override
    public int getRequiredMagicPoints() { return REQUIRED_MAGIC_POINTS; }

    @Override
    public void cast(int toonWisdom) { /* Not used */ }

    @Override
    public void castWithIntelligence(int toonIntelligence) { /* Not used */ }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) { /* Not used */ }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) { applySmokeBloom(caster); }

    @Override
    public void cast(Charecter caster) { applySmokeBloom(caster); }

    @Override
    public void cast() {
        System.out.println("Smoke Bloom is cast, but there is no caster.");
    }

    @Override
    public void cast(Charecter caster, Charecter target) { applySmokeBloom(caster); }

    private void applySmokeBloom(Charecter caster) {
        if (caster == null) return;
        if (caster.getGuild() != SPELL_GUILD) {
            System.out.println("Only members of the Crimson Veil Rogues guild can use Smoke Bloom.");
            return;
        }
        if (caster.getMagicPoints() < REQUIRED_MAGIC_POINTS) {
            System.out.println(caster.getName() + " does not have enough magic points to cast Smoke Bloom.");
            return;
        }
        caster.setMagicPoints(caster.getMagicPoints() - REQUIRED_MAGIC_POINTS);
        caster.applyStatusEffect(StatusType.DEFENSE_UP_STATUS, DURATION, DEFENSE_BUFF, caster);
        System.out.println(caster.getName() + " vanishes in a cloud of smoke, gaining +" + DEFENSE_BUFF + " defense for " + DURATION + " turns!");
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) { /* Not used */ }
}
