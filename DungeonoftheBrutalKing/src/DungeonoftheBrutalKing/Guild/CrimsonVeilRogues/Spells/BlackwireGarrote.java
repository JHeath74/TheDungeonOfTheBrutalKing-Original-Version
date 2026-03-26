
// src/Guild/CrimsonVeilRogues/Spells/BlackwireGarrote.java
package Guild.CrimsonVeilRogues.Spells;

import java.util.List;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import StatusEffects.StatusType;

public class BlackwireGarrote implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 6;
    private static final Guild SPELL_GUILD = Guild.CRIMSON_VEIL_ROGUES;
    private static final int SILENCE_DURATION = 2;

    private final String name = "Blackwire Garrote";
    private final String description = "A thin enchanted wire lashes out, briefly silencing or restricting a target’s spellcasting or special abilities.";

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
    public void cast(Charecter caster, List<Charecter> allCharacters) { /* Not used */ }

    @Override
    public void cast(Charecter caster) { /* Not used */ }

    @Override
    public void cast() {
        System.out.println("Blackwire Garrote is cast, but there is no caster or target.");
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        applyBlackwireGarrote(caster, target);
    }

    private void applyBlackwireGarrote(Charecter caster, Charecter target) {
        if (caster == null || target == null) return;
        if (caster.getGuild() != SPELL_GUILD) {
            System.out.println("Only members of the Crimson Veil Rogues guild can use Blackwire Garrote.");
            return;
        }
        if (caster.getMagicPoints() < REQUIRED_MAGIC_POINTS) {
            System.out.println(caster.getName() + " does not have enough magic points to cast Blackwire Garrote.");
            return;
        }
        target.applyStatusEffect(Status.StatusType.SILENCED_STATUS, SILENCE_DURATION, 0, caster); // Fix enum value
        caster.setMagicPoints(caster.getMagicPoints() - REQUIRED_MAGIC_POINTS);
        System.out.println(caster.getName() + " casts Blackwire Garrote on " + target.getName() + ", silencing them for " + SILENCE_DURATION + " turns!");
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) { /* Not used */ }
}
