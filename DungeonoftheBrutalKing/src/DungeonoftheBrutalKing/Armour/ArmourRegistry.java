
// File: src/DungeonoftheBrutalKing/Armour/ArmourRegistry.java
package DungeonoftheBrutalKing.Armour;

public class ArmourRegistry {
    public static void registerAll() {
        ArmourFactory.register("BreastPlate", BreastPlate::new);
        ArmourFactory.register("Chainmail", Chainmail::new);
        ArmourFactory.register("Cloth", Cloth::new);
        ArmourFactory.register("Plate", Plate::new);
        ArmourFactory.register("Skin", Skin::new);
        ArmourFactory.register("StuddedLeather", StuddedLeather::new);
    }
}
