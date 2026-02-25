
// src/Guild/DirgeweaversChorus/Spells/DirgeweaversDreadVerse.java
package Guild.DirgeweaversChorus.Spells;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;
import Status.StunStatus;

public class DreadVerse implements Spell {

    private static final String NAME = "Dread Verse";
    private static final String DESCRIPTION =
            "A discordant, profane verse that rattles the mind, dealing minor damage and sometimes stunning the target.";

    private static final Guild SPELL_GUILD = Guild.DIRGEWEAVERS_CHORUS;

    private static final int REQUIRED_WISDOM = 5;
    private static final int REQUIRED_MAGIC_POINTS = 5;

    private static final int BASE_DAMAGE = 4;

    private static final int STUN_CHANCE_PERCENT = 30;
    private static final int STUN_DURATION_TURNS = 1;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    public boolean canCast(Charecter caster) {
        return caster != null && caster.getWisdom() >= REQUIRED_WISDOM;
    }

    public void cast(Charecter caster, Enemies target) {
        if (caster == null || target == null) return;
        if (target.isDead()) return;
        if (!canCast(caster)) return;

        int damage = Math.max(1, BASE_DAMAGE + (caster.getCharisma() / 8));
        target.takeDamage(damage);

        int roll = ThreadLocalRandom.current().nextInt(1, 101);
        if (roll <= STUN_CHANCE_PERCENT && !target.isDead()) {
            target.addStatus(new StunStatus(STUN_DURATION_TURNS));
        }
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
    public void cast(int toonWisdom) {
        // No-op: cannot resolve caster/target from stats alone.
    }

    @Override
    public void castWithIntelligence(int toonIntelligence) {
        // No-op: this spell is wisdom/charisma based and needs entities.
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        // No-op: needs entities.
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        // No-op: single-target enemy spell.
    }

    @Override
    public void cast(Charecter caster) {
        // No-op: needs a target.
    }

    @Override
    public void cast() {
        // No-op: needs caster and target.
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster == null || target == null) return;
        // No-op: cannot safely treat Charecter as Enemies with current types.
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) {
        // No-op: not strength-based.
    }
}
