
// src/Guild/CrimsonVeilRogues/Spells/GhosthandLift.java
package Guild.CrimsonVeilRogues.Spells;

import java.util.List;
import java.util.Random;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;

public class GhosthandLift implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 6;
    private static final int MIN_GOLD = 10;
    private static final int MAX_GOLD = 30;
    private static final Guild SPELL_GUILD = Guild.CRIMSON_VEIL_ROGUES;

    private final String name = "Ghosthand Lift";
    private final String description = "The rogue uses supernatural sleight of hand to steal a small item or gold from the target.";

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
        System.out.println("Ghosthand Lift is cast, but there is no caster or target.");
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        applyGhosthandLift(caster, target);
    }

    private void applyGhosthandLift(Charecter caster, Charecter target) {
        if (caster == null || target == null) return;
        if (caster.getGuild() != SPELL_GUILD) {
            System.out.println("Only members of the Crimson Veil Rogues guild can use Ghosthand Lift.");
            return;
        }
        if (caster.getMagicPoints() < REQUIRED_MAGIC_POINTS) {
            System.out.println(caster.getName() + " does not have enough magic points to cast Ghosthand Lift.");
            return;
        }
        int goldToSteal = MIN_GOLD + new Random().nextInt(MAX_GOLD - MIN_GOLD + 1);
        int targetGold = target.getGold();
        if (targetGold <= 0) {
            System.out.println(target.getName() + " has no gold to steal.");
            return;
        }
        int actualStolen = Math.min(goldToSteal, targetGold);
        target.setGold(targetGold - actualStolen);
        caster.setGold(caster.getGold() + actualStolen);
        caster.setMagicPoints(caster.getMagicPoints() - REQUIRED_MAGIC_POINTS);
        System.out.println(caster.getName() + " steals " + actualStolen + " gold from " + target.getName() + " with Ghosthand Lift!");
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) { /* Not used */ }
}
