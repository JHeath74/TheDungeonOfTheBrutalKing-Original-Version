
// `src/Guild/DirgeweaversChorus/Spells/DiscordantHex.java`
package DungeonoftheBrutalKing.Guild.DirgeweaversChorus.Spells;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Status.BlindStatus;
import DungeonoftheBrutalKing.Status.DazeStatus;
import DungeonoftheBrutalKing.Status.ImmobilizedStatus;
import DungeonoftheBrutalKing.Status.PoisonStatus;
import DungeonoftheBrutalKing.Status.ReduceDefenseStatus;
import DungeonoftheBrutalKing.Status.SilencedStatus;
import DungeonoftheBrutalKing.Status.Status;

public class DiscordantHex implements Spell {

    private static final String NAME = "Discordant Hex";
    private static final String DESCRIPTION =
            "A twisting verse that inflicts a random affliction: daze, blind, immobilize, poison, reduce defense, or silence.";

    private static final Guild SPELL_GUILD = Guild.DIRGEWEAVERS_CHORUS;

    private static final int REQUIRED_WISDOM = 7;
    private static final int REQUIRED_MAGIC_POINTS = 8;

    private static final int BASE_DAMAGE = 2;

    // Durations / magnitudes (tune as needed)
    private static final int DAZE_PENALTY = 2;
    private static final int DAZE_DURATION = 2;

    private static final int BLIND_DURATION = 2;

    private static final int IMMOBILIZE_DURATION = 1;

    private static final int POISON_DAMAGE_PER_TURN = 2;
    private static final int POISON_DURATION = 3;

    private static final int REDUCE_DEFENSE_AMOUNT = 2;
    private static final int REDUCE_DEFENSE_DURATION = 2;

    private static final int SILENCE_DURATION = 2;

    private enum Debuff {
        DAZE,
        BLIND,
        IMMOBILIZED,
        POISON,
        REDUCE_DEFENSE,
        SILENCE
    }

    @Override
    public String getName() { return NAME; }

    @Override
    public String getDescription() { return DESCRIPTION; }

    public boolean canCast(Character caster) {
        return caster != null && caster.getWisdom() >= REQUIRED_WISDOM;
    }

    public void cast(Character caster, Enemies target) {
        if (caster == null || target == null) return;
        if (target.isDead()) return;
        if (!canCast(caster)) return;

        int scaling = Math.max(caster.getWisdom(), caster.getIntelligence()) / 10;
        int damage = Math.max(1, BASE_DAMAGE + scaling);
        target.takeDamage(damage, null);

        if (target.isDead()) return;

        Debuff pick = Debuff.values()[ThreadLocalRandom.current().nextInt(Debuff.values().length)];
        Status status = createStatusForPick(pick);
        if (status != null) {
            target.addStatus(status);
        }
    }

    private static Status createStatusForPick(Debuff pick) {
        return switch (pick) {
            case DAZE -> new DazeStatus(DAZE_PENALTY, DAZE_DURATION);
            case BLIND -> new BlindStatus();
            case IMMOBILIZED -> new ImmobilizedStatus(IMMOBILIZE_DURATION);
            case POISON -> new PoisonStatus(POISON_DAMAGE_PER_TURN);
            case REDUCE_DEFENSE -> new ReduceDefenseStatus();
            case SILENCE -> new SilencedStatus(SILENCE_DURATION);
        };
    }

    @Override
    public boolean isGuildSpell() { return true; }

    @Override
    public Guild getSpellGuild() { return SPELL_GUILD; }

    @Override
    public int getRequiredMagicPoints() { return REQUIRED_MAGIC_POINTS; }

    @Override
    public void cast(int toonWisdom) { }

    @Override
    public void castWithIntelligence(int toonIntelligence) { }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) { }

    @Override
    public void cast(Character caster, List<Character> allCharacters) { }

    @Override
    public void cast(Character caster) { }

    @Override
    public void cast() { }

    @Override
    public void cast(Character caster, Character target) {
        if (caster == null || target == null) return;
    }

    @Override
    public void castWithStrength(Character enemy, double d) { }
}
