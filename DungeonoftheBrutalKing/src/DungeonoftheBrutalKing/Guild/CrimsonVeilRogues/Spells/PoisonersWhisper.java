package Guild.CrimsonVeilRogues.Spells;

import java.util.List;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import Status.PoisonStatus;

public class PoisonersWhisper implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 5;
    private static final int POISON_DURATION = 3;
    private static final Guild SPELL_GUILD = Guild.CRIMSON_VEIL_ROGUES;

    private final String name = "Poisoner's Whisper";
    private final String description = "A quick application of a fast-acting toxin. The next successful hit inflicts ongoing poison damage.";

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
        System.out.println("Poisoner's Whisper is cast, but there is no caster or target.");
    }

    @Override
    public void cast(Charecter caster, Charecter target) { 
        applyPoison(caster, target); 
    }

    private void applyPoison(Charecter caster, Charecter target) {
        if (caster == null || target == null) return;
        if (caster.getGuild() != SPELL_GUILD) {
            System.out.println("Only members of the Crimson Veil Rogues guild can use Poisoner's Whisper.");
            return;
        }
        if (caster.getMagicPoints() < REQUIRED_MAGIC_POINTS) {
            System.out.println(caster.getName() + " does not have enough magic points to cast Poisoner's Whisper.");
            return;
        }
        caster.setMagicPoints(caster.getMagicPoints() - REQUIRED_MAGIC_POINTS);
        target.addStatus(new PoisonStatus(POISON_DURATION));
        System.out.println(target.getName() + " is afflicted with poison for " + POISON_DURATION + " turns!");
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) { /* Not used */ }
}
