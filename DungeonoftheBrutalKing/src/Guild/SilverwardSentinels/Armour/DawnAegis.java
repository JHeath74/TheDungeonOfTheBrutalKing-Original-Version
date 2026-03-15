// filepath: g:\Programs\Github\Java\TheDungeonOfTheBrutalKing-Original-Version\DungeonoftheBrutalKing\src\Guild\SilverwardSentinels\Armour\DawnAegis.java
package Guild.SilverwardSentinels.Armour;

import DungeonoftheBrutalKing.Charecter;

/**
 * Compatibility subclass: DawnAegis delegates to SilverwardDawnAegis (new suite name).
 */
public class DawnAegis extends SilverwardDawnAegis {
    public DawnAegis(String effect) { super(effect); }

    public static DawnAegis createDawnAegis(Charecter character, String effect) {
        // Reuse validation from SilverwardDawnAegis
        SilverwardDawnAegis.createSilverwardDawnAegis(character, effect);
        return new DawnAegis(effect);
    }
}