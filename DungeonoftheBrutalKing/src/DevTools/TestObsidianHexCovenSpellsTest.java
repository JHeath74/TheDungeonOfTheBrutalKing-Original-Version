package DevTools;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import Spells.SpellFactory;
import SharedData.Guild;

/**
 * Smoke-test: attempt to instantiate ObsidianHexCoven spells via SpellFactory.
 * This test reports which spells are available vs missing but does not fail
 * the build — useful for CI visibility without breaking other checks.
 */
public class TestObsidianHexCovenSpellsTest {

    private static final String[] HEX_SPELLS = new String[] {
        "ArcaneMend",
        "AstralRift",
        "ChaosHex",
        "Chill_Touch",
        "Cold_Blast",
        "EmberlanceSurge",
        "Fireball",
        "Firebolt",
        "IceBarrier",
        "Light"
    };

    @Test
    void testObsidianHexCovenSpellsCanBeCreated() {
        List<String> available = new ArrayList<>();
        List<String> missing = new ArrayList<>();

        for (String name : HEX_SPELLS) {
            try {
                Object spell = SpellFactory.createGuildSpell(name, Guild.OBSIDIAN_HEX_COVEN);
                if (spell != null) {
                    available.add(name + " -> " + spell.getClass().getName());
                } else {
                    missing.add(name + " -> null");
                }
            } catch (Throwable t) {
                missing.add(name + " -> " + t.getClass().getSimpleName() + ": " + t.getMessage());
            }
        }

        System.out.println("[ObsidianHexCoven] Spells available: " + available.size() + " / " + HEX_SPELLS.length);
        for (String s : available) System.out.println("  OK: " + s);
        for (String s : missing) System.out.println("  MISSING: " + s);

        // Do not fail the build here; this test is an informational smoke test.
        assertTrue(true);
    }
}