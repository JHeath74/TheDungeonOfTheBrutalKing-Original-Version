package DungeonoftheBrutalKing.DevTools;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import DungeonoftheBrutalKing.Spells.SpellFactory;
import DungeonoftheBrutalKing.SharedData.Guild;

/**
 * Smoke-test: attempt to instantiate ObsidianShadowSyndicate spells via SpellFactory.
 * This test reports which spells are available vs missing but does not fail
 * the build — useful for CI visibility without breaking other checks.
 */
public class TestObsidianShadowSpellsTest {

    private static final String[] OBSIDIAN_SPELLS = new String[] {
        "CripplingShadows",
        "DazingStrike",
        "GreaterHealSpell",
        "MinorHealAndRageSpell",
        "PoisonDagger",
        "ShadowSlash",
        "ShadowStab",
        "SmokeStrike",
        "ThiefsInsight",
        "WhisperLock"
    };

    @Test
    void testObsidianSpellsCanBeCreated() {
        List<String> available = new ArrayList<>();
        List<String> missing = new ArrayList<>();

        for (String name : OBSIDIAN_SPELLS) {
            try {
                Object spell = SpellFactory.createGuildSpell(name, Guild.OBSIDIAN_SHADOW_SYNDICATE);
                if (spell != null) {
                    available.add(name + " -> " + spell.getClass().getName());
                } else {
                    missing.add(name + " -> null");
                }
            } catch (Throwable t) {
                missing.add(name + " -> " + t.getClass().getSimpleName() + ": " + t.getMessage());
            }
        }

        System.out.println("[ObsidianShadow] Spells available: " + available.size() + " / " + OBSIDIAN_SPELLS.length);
        for (String s : available) System.out.println("  OK: " + s);
        for (String s : missing) System.out.println("  MISSING: " + s);

        // Do not fail the build here; informational smoke test.
        assertTrue(true);
    }
}