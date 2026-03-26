package Guild.CrimsonBlades.Spells;

import java.util.List;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import Status.StatusType;

public class BloodboundVow implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 8;
    private static final int STRENGTH_BUFF = 5;
    private static final int DURATION = 2;
    
    private static final Guild SPELL_GUILD = Guild.CRIMSON_BLADES;

    private final String name = "Bloodbound Vow";
    private final String description = "The warrior swears an oath mid‐battle, gaining strength as long as they stand their ground.";

    @Override
    public String getName() { return name; }

    @Override
    public String getDescription() { return description; }

    @Override
    public boolean isGuildSpell() { return true; }

    @Override
    public Guild getSpellGuild() { return Guild.CRIMSON_BLADES; }

    @Override
    public int getRequiredMagicPoints() { return REQUIRED_MAGIC_POINTS; }

    @Override
    public void cast(int toonWisdom) { /* Not used */ }

    @Override
    public void castWithIntelligence(int toonIntelligence) { /* Not used */ }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) { /* Not used */ }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) { applyBuff(caster); }

    @Override
    public void cast(Charecter caster) { applyBuff(caster); }

    @Override
    public void cast() {
        System.out.println("Bloodbound Vow is cast, but there is no caster.");
    }

    @Override
    public void cast(Charecter caster, Charecter target) { applyBuff(caster); }

    private void applyBuff(Charecter caster) {
        if (caster == null) return;
        if (caster.getGuild() != Guild.CRIMSON_BLADES) {
            System.out.println("Only members of the Crimson Blades guild can use Bloodbound Vow.");
            return;
        }
        caster.applyStatusEffect(StatusType.STRENGTH_BUFF_STATUS, DURATION, STRENGTH_BUFF, caster);
        System.out.println(caster.getName() + " swears a Bloodbound Vow and gains +" + STRENGTH_BUFF + " strength for " + DURATION + " turns!");
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) { /* Not used */ }
}
