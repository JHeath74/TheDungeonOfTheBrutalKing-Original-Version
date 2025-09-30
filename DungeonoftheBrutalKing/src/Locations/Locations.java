
package Locations;

import java.util.List;

public class Locations {
    private final String name;
    private final int x;
    private final int y;
    private final int dungeonLevel;

    public Locations(String name, int x, int y, int dungeonLevel) {
        this.name = name;
        this.x = x;
        this.y = y;
		this.dungeonLevel = dungeonLevel ;
		
    }

    public String getName() { return name; }
    public int getX() { return x; }
    public int getY() { return y; }

    public boolean matches(int px, int py) {
        return this.x == px && this.y == py;
    }
    
    public void checkCurrentLocation(int currentX, int currentY, List<Locations> locations) {
        for (Locations loc : locations) {
            if (loc.matches(currentX, currentY)) {
                System.out.println("You are at: " + loc.getName());
                // Additional logic here
            }
        }
}
}
