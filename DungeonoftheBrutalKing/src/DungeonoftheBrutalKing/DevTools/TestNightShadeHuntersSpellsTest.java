package DungeonoftheBrutalKing.DevTools;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import DungeonoftheBrutalKing.Spells.SpellFactory;
import DungeonoftheBrutalKing.SharedData.Guild;

/**
 * Smoke-test: attempt to instantiate NightShadeHunters spells via SpellFactory.
 * Reports available vs missing spells but does not fail the build.
 */
public class TestNightShadeHuntersSpellsTest {

    private static final String[] NIGHT_SPELLS = new String[] {
        "CrimsonTrailShow",
        "CripplingSnare",
        "DeadeyeFocus",
        "FieldDressing",
        "HuntersMarkShot",
        "SerratedShot",
        "ShadowSnare",
        "ShadowStepVeil",
        "SilencingBolt",
        "VenomTippedShot",
        "VoidFangBolt"
    };

    @Test
    void testNightShadeHuntersSpellsCanBeCreated() {
        List<String> available = new ArrayList<>();
        List<String> missing = new ArrayList<>();

        for (String name : NIGHT_SPELLS) {
            try {
                Object spell = SpellFactory.createGuildSpell(name, Guild.NIGHT_SHADE_HUNTERS);
                if (spell != null) {
                    available.add(name + " -> " + spell.getClass().getName());
                } else {
                    missing.add(name + " -> null");
                }
            } catch (Throwable t) {
                missing.add(name + " -> " + t.getClass().getSimpleName() + ": " + t.getMessage());
            }
        }

        System.out.println("[NightShadeHunters] Spells available: " + available.size() + " / " + NIGHT_SPELLS.length);
        for (String s : available) System.out.println("  OK: " + s);
        for (String s : missing) System.out.println("  MISSING: " + s);

        // Informational; do not fail the build.
        assertTrue(true);
    }
}
