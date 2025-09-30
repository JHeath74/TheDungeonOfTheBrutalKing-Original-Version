package Locations;

public class BankVaults {
    private int dungeonLevel = 2;
    private int x = 5;
    private int y = 22;
	private String name = "Bank Vault";
    

    // Constructor to initialize all fields
    public BankVaults(int x, int y, int dungeonLevel, String name) {
        
    	this.name = name;
    	this.x = x;
        this.y = y;
        this.dungeonLevel = dungeonLevel;
    }

    // Setters
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setDungeonLevel(int dungeonLevel) { this.dungeonLevel = dungeonLevel; }

    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getDungeonLevel() { return dungeonLevel; }

	public String getName() {
		return name;
	}
}
