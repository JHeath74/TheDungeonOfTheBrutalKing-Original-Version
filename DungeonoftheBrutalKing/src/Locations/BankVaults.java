
// Represents a bank vault location in the dungeon
package Locations;


public class BankVaults {
    // Stores the location of the bank vault (name, x, y)
    private Location vaultLocation = new Location("Bank Vaults", 10, 20);
    // Stores the dungeon level where the vault is located
    private int dungeonLevel = 3;

    // Constructor to initialize the vault's name, dungeon level, and coordinates
    public BankVaults(String name, int dungeonLevel, int x, int y) {
        this.vaultLocation = new Location(name, x, y);
        this.dungeonLevel = dungeonLevel;
    }

    // Returns the vault's location object
    public Location getVaultLocation() {
        return vaultLocation;
    }

    // Returns the dungeon level of the vault
    public int getDungeonLevel() {
        return dungeonLevel;
    }
}
