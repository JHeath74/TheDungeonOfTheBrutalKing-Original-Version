package DungeonoftheBrutalKing.Guild.SilverwardSentinels.Spells;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Status.OathbreakersRuinStatus;

public final class OathbreakersRuin implements Spell {

    private static final String NAME = "Oathbreaker's Ruin";
    private static final int REQUIRED_MP = 10;
    private static final int BASE_DAMAGE = 12;
    private static final double WISDOM_SCALING = 1.0;
    private static final double STRENGTH_SCALING = 0.5;
    private static final int DEBUFF_DURATION_MINUTES = 3;
    private static final int DEBUFF_INTENSITY = 20;

    @Override
    public String getName() { return NAME; }
    @Override
    public boolean isGuildSpell() { return true; }
    @Override
    public Guild getSpellGuild() { return Guild.SILVERWARD_SENTINELS; }
    @Override
    public int getRequiredMagicPoints() { return REQUIRED_MP; }
    @Override
    public String getDescription() {
        return "Oathbreaker's Ruin: unleash the full wrath of your sacred oath upon a single foe. "
             + "Deals heavy oath-infused damage that tears through defenses and leaves the target "
             + "weakened under the weight of their betrayal.";
    }

    private void applyRuin(Charecter caster, Enemies target) {
        if (caster == null || target == null) return;
        Guild casterGuild = caster.getGuild();
        if (casterGuild == null || casterGuild != getSpellGuild()) return;

        int damage = computeDamage(caster, casterGuild);
        target.takeDamageWithStatuses(damage);

        OathbreakersRuinStatus debuff = new OathbreakersRuinStatus(
            "Oathbreaker's Ruin",
            DEBUFF_DURATION_MINUTES,
            DEBUFF_INTENSITY
        );
        target.addStatus(debuff);
    }

    private int computeDamage(Charecter caster, Guild oath) {
        int wis = Math.max(0, caster.getWisdom());
        int str = Math.max(0, caster.getStrength());
        double base = BASE_DAMAGE + (wis * WISDOM_SCALING) + (str * STRENGTH_SCALING);
        double result = base * 1.3;
        if (oath == Guild.SILVERWARD_SENTINELS) result *= 1.1;
        return (int) Math.max(1, Math.round(result));
    }

    @Override public void cast() {}
    @Override public void cast(int toonWisdom) {}
    @Override public void castWithIntelligence(int toonIntelligence) {}
    @Override public void cast(int toonWisdom, int toonIntelligence) {}
    @Override public void cast(Charecter caster) {}
    @Override public void cast(Charecter caster, Charecter target) {}
    @Override public void cast(Charecter caster, java.util.List<Charecter> allCharacters) {}
    @Override public void castWithStrength(Charecter enemy, double strengthScaling) {}
    @Override public void cast(Charecter caster, Enemies target) { applyRuin(caster, target); }
}
