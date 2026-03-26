
// `src/Guild/CrimsonVeilRogues/Spells/ShadowstepVeil.java`
package Guild.CrimsonVeilRogues.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;
import Status.AccuracyStatus;

public class ShadowstepVeil implements Spell {

    private static final Guild REQUIRED_GUILD = Guild.CRIMSON_VEIL_ROGUES;
    private static final String SKILL_NAME = "Shadowstep Veil";
    private static final String DESCRIPTION =
            "The rogue melts into a nearby shadow. For one turn, attacks from the targeted enemy have reduced accuracy.";
    private static final int ACCURACY_DEBUFF = -30;
    private static final int DURATION = 1;

    private Charecter caster;
    private Enemies target;

    public ShadowstepVeil() {
        // no-arg ctor for SpellFactory
    }

    public ShadowstepVeil(Charecter caster, Enemies target) {
        this.caster = caster;
        this.target = target;
    }

    private boolean apply(Charecter user, Enemies enemy) {
        if (user == null || enemy == null) return false;
        if (user.getGuild() != REQUIRED_GUILD) return false;

        AccuracyStatus debuff = new AccuracyStatus(
                "Shadowstep Veil Debuff",
                DURATION,
                true,
                ACCURACY_DEBUFF
        );
        enemy.addStatus(debuff);
        return true;
    }

    @Override
    public boolean isGuildSpell() {
        return true;
    }

    @Override
    public Guild getSpellGuild() {
        return REQUIRED_GUILD;
    }

    @Override
    public int getRequiredMagicPoints() {
        return 0;
    }

    @Override
    public String getName() {
        return SKILL_NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public void cast() {
        if (caster != null && target != null) {
            apply(caster, target);
        }
    }

    @Override
    public void cast(Charecter caster) {
        this.caster = caster;
        cast();
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        // Not applicable (this spell targets an Enemies instance in your current design)
        this.caster = caster;
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        this.caster = caster;
    }

    @Override
    public void cast(int toonWisdom) {
        cast();
    }

    @Override
    public void castWithIntelligence(int toonIntelligence) {
        cast();
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        cast();
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) {
        // Not applicable
    }
}
