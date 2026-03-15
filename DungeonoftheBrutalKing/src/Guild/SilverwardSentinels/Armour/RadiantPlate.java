// filepath: g:\Programs\Github\Java\TheDungeonOfTheBrutalKing-Original-Version\DungeonoftheBrutalKing\src\Guild\SilverwardSentinels\Armour\RadiantPlate.java
package Guild.SilverwardSentinels.Armour;

import DungeonoftheBrutalKing.Charecter;

/**
 * Compatibility subclass: RadiantPlate delegates to SilverwardRadiantPlate (new suite name).
 */
public class RadiantPlate extends SilverwardRadiantPlate {
    public RadiantPlate(String effect) { super(effect); }

    public static RadiantPlate createRadiantPlate(Charecter character, String effect) {
        // Reuse validation from SilverwardRadiantPlate
        SilverwardRadiantPlate.createSilverwardRadiantPlate(character, effect);
        return new RadiantPlate(effect);
    }
}