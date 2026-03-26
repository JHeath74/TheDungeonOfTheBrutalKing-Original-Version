package Guild.ObsidianShadowSyndicate.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;

/**
 * WhisperLock: you whisper a command word and a mundane lock clicks open
 * as if picked by invisible tools. Does not work on magical or rune-protected locks.
 */
public final class WhisperLock implements Spell {

    private static final String NAME = "Whisper Lock";
    private static final String DESCRIPTION =
            "You whisper a command word and a mundane lock clicks open as if picked by invisible tools. "
          + "Ineffective against magical or rune-protected locks.";

    private static final int REQUIRED_MAGIC_POINTS = 3;

    private static final Guild SPELL_GUILD = Guild.OBSIDIAN_SHADOW_SYNDICATE;

    public WhisperLock() { }

    // --- Spell meta-data ---

    @Override
    public boolean isGuildSpell() {
        return SPELL_GUILD != Guild.NON_GUILD;
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
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    // --- Core behavior ---

    /**
     * Core unlock logic. This expects some target object that represents a lock.
     * Replace the Lockable interface usage with your actual door / chest / lock type.
     */
    private void tryUnlock(Charecter caster, Object potentialLock) {
        if (potentialLock == null) {
            System.out.println(NAME + " finds no lock to affect.");
            return;
        }

        // Example integration point: if you have a Lockable interface in your project, use that.
        // interface Lockable {
        //     boolean isLocked();
        //     boolean isMagical();
        //     boolean hasRunes();
        //     void unlock();
        //     String getDisplayName();
        // }

        if (!(potentialLock instanceof Lockable)) {
            System.out.println(NAME + " can only be used on mundane locks.");
            return;
        }

        Lockable lock = (Lockable) potentialLock;

        if (!lock.isLocked()) {
            System.out.println("There is no need to use " + NAME + "; the "
                    + lock.getDisplayName() + " is already unlocked.");
            return;
        }

        if (lock.isMagical() || lock.hasRunes()) {
            System.out.println(caster.getName() + " whispers the command, but "
                    + NAME + " fizzles against the warded " + lock.getDisplayName() + ".");
            return;
        }

        lock.unlock();
        System.out.println(caster.getName() + " whispers softly. The "
                + lock.getDisplayName() + " clicks open as if picked by invisible tools.");
    }

    // --- Spell interface implementations ---

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster == null) caster = Charecter.getInstance();
        if (caster == null) return;

        // Guild enforcement similar to other Obsidian Shadow Syndicate spells
        try {
            Guild casterGuild = caster.getGuild();
            if (isGuildSpell() && casterGuild != SPELL_GUILD) {
                System.out.println(caster.getName() + " cannot cast " + NAME + " (wrong guild).");
                return;
            }
        } catch (Exception ignored) { }

        if (caster.getMagicPoints() < REQUIRED_MAGIC_POINTS) {
            System.out.println(caster.getName()
                    + " does not have enough magic points to cast " + NAME + "!");
            return;
        }
        caster.setMagicPoints(caster.getMagicPoints() - REQUIRED_MAGIC_POINTS);

        // Without a dedicated lock object, just flavor text.
        System.out.println(caster.getName()
                + " whispers a soft command, but there is no visible lock to affect.");
    }

    @Override
    public void cast(Charecter caster) {
        // No lock context; behave like a no-op with flavor.
        cast(caster, caster);
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        // This spell is utility, not combat-AOE; reuse basic behavior.
        cast(caster);
    }

    @Override
    public void cast() {
        cast(Charecter.getInstance());
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
        cast(Charecter.getInstance());
    }

    @Override
    public void cast(Charecter caster, Enemies target) {
        // Non-combat: ignore Enemies-based API.
        cast(caster);
    }

    // --- Example Lockable interface stub ---
    // Remove this inner interface if your project already defines a proper Lockable type.
    public interface Lockable {
        boolean isLocked();
        boolean isMagical();
        boolean hasRunes();
        void unlock();
        String getDisplayName();
    }
}
