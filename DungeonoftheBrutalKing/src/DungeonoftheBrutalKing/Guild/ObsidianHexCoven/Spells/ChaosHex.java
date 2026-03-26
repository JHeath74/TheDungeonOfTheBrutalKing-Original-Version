// src/Spells/ChaosHex.java
package Guild.ObsidianHexCoven.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Status.Status;
import Status.StatusRegistry;
import Spells.Spell;
import SharedData.RandomFactory;

/**
 * ChaosHex: a mage spell that applies a random status effect to a single enemy.
 */
public class ChaosHex implements Spell {
    private static final String NAME = "Chaos Hex";
    private static final String DESCRIPTION = "Hex the enemy with a random affliction from the arcane tapestry.";
    private static final int REQUIRED_MAGIC_POINTS = 7;
    private static final int MINIMUM_INTELLIGENCE = 6;
    private static final Guild SPELL_GUILD = Guild.OBSIDIAN_HEX_COVEN;

    // Candidate status names (present in StatusRegistry)
    private static final String[] CANDIDATES = new String[] {
        "PoisonStatus",
        "StunStatus",
        "SilencedStatus",
        "ImmobilizedStatus",
        "ReduceDefenseStatus",
        "FireStatus",
        "IceStatus",
        "LightningStatus",
        "BlindStatus",
        "DazeStatus"
    };

    public ChaosHex() { }

    @Override
    public boolean isGuildSpell() { return SPELL_GUILD != Guild.NON_GUILD; }

    @Override
    public Guild getSpellGuild() { return SPELL_GUILD; }

    @Override
    public int getRequiredMagicPoints() { return REQUIRED_MAGIC_POINTS; }

    @Override
    public String getName() { return NAME; }

    @Override
    public String getDescription() { return DESCRIPTION; }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster == null) caster = Charecter.getInstance();
        if (target == null) {
            System.out.println("No target for " + NAME + ".");
            return;
        }

        int intelligence = Math.max(0, caster.getIntelligence());
        if (intelligence < MINIMUM_INTELLIGENCE) {
            System.out.println("You lack the necessary Intelligence to cast " + NAME + "!");
            return;
        }

        if (caster.getMagicPoints() < REQUIRED_MAGIC_POINTS) {
            System.out.println(caster.getName() + " does not have enough magic points to cast " + NAME + "!");
            return;
        }

        caster.setMagicPoints(caster.getMagicPoints() - REQUIRED_MAGIC_POINTS);

        // Pick a random candidate
        String chosenName = CANDIDATES[RandomFactory.gameplayInt(CANDIDATES.length)];
        Class<? extends Status> cls = StatusRegistry.getStatusClass(chosenName);
        if (cls == null) {
            System.out.println("Chaos Hex fizzles — the weave cannot find a suitable curse.");
            return;
        }

        Status statusInstance = instantiateStatusWithDefaults(cls);
        if (statusInstance == null) {
            System.out.println("Chaos Hex fizzles — failed to conjure the status.");
            return;
        }

        // Apply the status to the target
        try {
            if (target.getStatusManager() != null) {
                target.getStatusManager().addStatus(statusInstance, target);
            } else {
                statusInstance.applyEffect(target);
            }
            System.out.println(caster.getName() + " casts " + NAME + ", inflicting " + statusInstance.getName() + " on " + safeName(target) + ".");
        } catch (Exception e) {
            System.out.println("Chaos Hex failed to anchor the curse.");
        }
    }

    // Try a few common constructor patterns: no-arg, (int duration), (int duration, int value)
    private static Status instantiateStatusWithDefaults(Class<? extends Status> cls) {
        try {
            try {
                return cls.getDeclaredConstructor().newInstance();
            } catch (Exception ignored) { }

            try {
                return cls.getDeclaredConstructor(int.class).newInstance(3);
            } catch (Exception ignored) { }

            try {
                return cls.getDeclaredConstructor(int.class, int.class).newInstance(3, 5);
            } catch (Exception ignored) { }

            // As last resort, try single-string name constructor if present
            try {
                return cls.getDeclaredConstructor(String.class).newInstance(cls.getSimpleName());
            } catch (Exception ignored) { }
        } catch (Exception ignored) { }
        return null;
    }

    private static String safeName(Charecter c) {
        try {
            String name = c.getName();
            return (name == null || name.isBlank()) ? "target" : name;
        } catch (Exception ignored) {
            return "target";
        }
    }

    // Minimal compatible Spell interface implementations
    @Override
    public void cast(Charecter caster) { cast(caster, (Charecter) null); }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        if (allCharacters == null || allCharacters.isEmpty()) { cast(caster); return; }
        cast(caster, allCharacters.get(0));
    }

    @Override
    public void cast() { cast(Charecter.getInstance(), (Charecter) null); }

    @Override
    public void cast(int toonWisdom) { cast(Charecter.getInstance(), (Charecter) null); }

    @Override
    public void castWithIntelligence(int toonIntelligence) { cast(Charecter.getInstance(), (Charecter) null); }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) { cast(Charecter.getInstance(), (Charecter) null); }

    @Override
    public void castWithStrength(Charecter enemy, double d) { /* not applicable */ }

    @Override
    public void cast(Charecter caster, Enemies target) { /* best-effort: not implemented for Enemies */ cast(caster); }
}