package DungeonoftheBrutalKing.Guild.SilverwardSentinels.Spells;

import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

/**
 * Manager for Silverward Sentinels guild spells.
 *
 * Improvements over the original:
 * - Thread-safe storage via {@link ConcurrentHashMap}
 * - Case-insensitive spell name lookup (uses Locale.ROOT)
 * - Null-safety and defensive copies for returned maps/collections
 * - Utility methods for unregistering and bulk-registering spells
 * - Auto-register compiled spell classes found under src/Guild/SilverwardSentinels/Spells
 */
public class SilverwardSentinelsGuildSpellsManager {
    private final Guild guild;
    // store lower-cased names (Locale.ROOT) to support case-insensitive lookup
    private final Map<String, Spell> guildSpells = new ConcurrentHashMap<>();

    public SilverwardSentinelsGuildSpellsManager(Guild guild) {
        this.guild = guild;
        // Attempt to auto-register any compiled Silverward spell classes available on the classpath.
        autoRegisterFromSourceFolder();
        // Also attempt to register the known/expected Silverward spell classes by name.
        registerDefaultSpells();
    }

    /**
     * Scan the repository source folder for Silverward spell classes and attempt
     * to load and register any compiled spell classes by convention.
     *
     * This is a best-effort developer convenience: Class.forName may fail if
     * classes are not yet compiled or not on the classpath. Failures are
     * swallowed to avoid impacting normal game startup.
     */
    private void autoRegisterFromSourceFolder() {
        try {
            Path root = Paths.get(System.getProperty("user.dir"));
            Path spellsSrc = root.resolve("src").resolve("Guild").resolve("SilverwardSentinels").resolve("Spells");
            if (!Files.exists(spellsSrc) || !Files.isDirectory(spellsSrc)) return;

            Files.list(spellsSrc).filter(p -> p.toString().endsWith(".java")).forEach(javaFile -> {
                String className = javaFile.getFileName().toString().replaceFirst("\\.java$", "");
                String fqcn = "Guild.SilverwardSentinels.Spells." + className;
                try {
                    Class<?> cls = Class.forName(fqcn);
                    if (!Spell.class.isAssignableFrom(cls)) return;
                    @SuppressWarnings("unchecked")
                    Class<? extends Spell> scls = (Class<? extends Spell>) cls;
                    Spell instance = scls.getDeclaredConstructor().newInstance();
                    // Use registerSpell which performs validation
                    registerSpell(instance);
                } catch (ClassNotFoundException cnf) {
                    // Not compiled / not on classpath: ignore
                } catch (Throwable t) {
                    // Swallow and continue with other classes
                    try { System.out.println("Auto-register failed for " + fqcn + " -> " + t.getMessage()); } catch (Exception ignored) { }
                }
            });
        } catch (IOException ioe) {
            // Ignore filesystem access issues - best effort only
        } catch (Exception e) {
            // Defensive: ensure constructor doesn't throw
            try { System.out.println("Auto-register initialization error: " + e.getMessage()); } catch (Exception ignored) { }
        }
    }

    /**
     * Register a single spell with the manager.
     * The spell must declare it is a guild spell and belong to this manager's guild.
     * Registration is case-insensitive by spell name.
     */
    public void registerSpell(Spell spell) {
        if (spell == null) return;
        try {
            if (!spell.isGuildSpell()) return;
            if (spell.getSpellGuild() != guild) return;
            String key = safeNameKey(spell.getName());
            if (key == null) return;
            guildSpells.put(key, spell);
        } catch (Exception ignored) {
            // Defensive: do not propagate exceptions during registration
        }
    }

    /**
     * Register multiple spells in a batch.
     */
    public void registerAll(Iterable<Spell> spells) {
        if (spells == null) return;
        for (Spell s : spells) registerSpell(s);
    }

    /**
     * Unregister a spell by instance (removes by its declared name).
     */
    public void unregisterSpell(Spell spell) {
        if (spell == null) return;
        String key = safeNameKey(spell.getName());
        if (key == null) return;
        guildSpells.remove(key);
    }

    /**
     * Unregister a spell by name (case-insensitive).
     */
    public void unregisterSpellByName(String name) {
        String key = safeNameKey(name);
        if (key == null) return;
        guildSpells.remove(key);
    }

    /**
     * Returns the registered Spell for the given name (case-insensitive), or null
     * if none is registered.
     */
    public Spell getSpell(String name) {
        String key = safeNameKey(name);
        if (key == null) return null;
        return guildSpells.get(key);
    }

    /**
     * Returns an immutable copy of the registered spells map (name -> Spell).
     * The map's keys are the original spell names lower-cased with Locale.ROOT.
     */
    public Map<String, Spell> getAllSpells() {
        return Collections.unmodifiableMap(new HashMap<>(guildSpells));
    }

    /**
     * Returns true if the manager has a spell with the given name (case-insensitive).
     */
    public boolean hasSpell(String name) {
        return getSpell(name) != null;
    }

    private static String safeNameKey(String name) {
        if (name == null) return null;
        try {
            String k = name.trim();
            if (k.isEmpty()) return null;
            return k.toLowerCase(Locale.ROOT);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Attempts to register a class by simple class name (assumed to be in
     * Guild.SilverwardSentinels.Spells package). Safe: failures are ignored.
     */
    public void registerByClassName(String simpleClassName) {
        if (simpleClassName == null || simpleClassName.isBlank()) return;
        String fqcn = "Guild.SilverwardSentinels.Spells." + simpleClassName.trim();
        try {
            Class<?> cls = Class.forName(fqcn);
            if (!Spell.class.isAssignableFrom(cls)) return;
            @SuppressWarnings("unchecked")
            Class<? extends Spell> scls = (Class<? extends Spell>) cls;
            Spell instance = scls.getDeclaredConstructor().newInstance();
            registerSpell(instance);
        } catch (ClassNotFoundException cnf) {
            // class not available on classpath; ignore
        } catch (Throwable t) {
            try { System.out.println("registerByClassName failed for " + fqcn + " -> " + t.getMessage()); } catch (Exception ignored) { }
        }
    }

    /**
     * Register the expected Silverward spell classes by name. This helps when
     * compiled classes exist but the source-based scan missed them.
     */
    public void registerDefaultSpells() {
        String[] defaults = new String[] {
            "BlessingOfPurity",
            "BlessingofRestoration",
            "Dawnbind",
            "JudgementBrand",
            "Location",
            "OathbreakersRuin",
            "Port",
            "RadiantStrike",
            "SanctifiedPurge",
            "SmiteOfTheDawn"
        };
        for (String s : defaults) registerByClassName(s);
    }
}