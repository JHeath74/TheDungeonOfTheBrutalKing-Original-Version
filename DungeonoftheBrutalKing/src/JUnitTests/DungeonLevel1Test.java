
package JUnitTests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import Maps.DungeonLevel1;
import Maps.DungeonLevel2;
import SharedData.LocationType;

public class DungeonLevel1Test {

    @Test
    public void testStairsDownToLevel2() {
        DungeonLevel1 level1 = new DungeonLevel1();
        // Check special location type
        assertEquals(LocationType.STAIRS_DOWN, level1.getSpecialLocation(118, 2));
        // Check goDown returns DungeonLevel2
        assertTrue(level1.goDown() instanceof DungeonLevel2);
    }
}
