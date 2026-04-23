package DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;

public final class GreaterHealSpell implements Spell {

    private static final String NAME = "GreaterHeal";
    private static final double HEAL_PERCENT = 0.20;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isGuildSpell() {
        return true;
    }

    @Override
    public Guild getSpellGuild() {
        return Guild.OBSIDIAN_SHADOW_SYNDICATE; // adjust to your real enum/value
    }

    @Override
    public int getRequiredMagicPoints() {
        // set to whatever MP cost you want
        return 5;
    }

    // Core heal logic in a private helper that returns the healed amount
    private int doHeal(Character caster) {
        if (caster == null) return 0;

        int maxHp = Math.max(0, caster.getMaxHealth());
        int currentHp = Math.max(0, caster.getHitPoints());
        if (currentHp >= maxHp) return 0;

        int amount = (int) Math.round(maxHp * HEAL_PERCENT);
        amount = Math.max(1, amount);

        int healed = Math.min(amount, maxHp - currentHp);
        caster.setHitPoints(currentHp + healed);
        return healed;
    }

    @Override
    public void cast(Character caster) {
        doHeal(caster);
    }

    @Override
    public void cast() {
        // no\-arg cast does nothing or could be wired to a global caster if you have one
    }

    @Override
    public void cast(int toonWisdom) {
        // optional: scale heal by wisdom if desired
    }

    @Override
    public void castWithIntelligence(int toonIntelligence) {
        // optional: scale heal by intelligence if desired
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        // optional: combined stats logic
    }

    @Override
    public void cast(Character caster, List<Character> allCharacters) {
        // for now, just heal the caster
        doHeal(caster);
    }

    @Override
    public void cast(Character caster, Character target) {
        // heal the target instead of caster
        doHeal(target);
    }

    @Override
    public void castWithStrength(Character enemy, double d) {
        // not really used for healing; leave empty or repurpose if needed
    }

    @Override
    public void cast(Character caster, Enemies target) {
        // GreaterHeal does not affect enemies; leave empty
    }

    @Override
    public String getDescription() {
        return "Heals the target for about 20\\% of their maximum health.";
    }
}
