
package Location;

import java.util.List;

public class Location {
    private final String name;
    private final int x;
    private final int y;

    public Location(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() { return name; }
    public int getX() { return x; }
    public int getY() { return y; }

    public boolean matches(int px, int py) {
        return this.x == px && this.y == py;
    }
    
    public void checkCurrentLocation(int currentX, int currentY, List<Location> locations) {
        for (Location loc : locations) {
            if (loc.matches(currentX, currentY)) {
                System.out.println("You are at: " + loc.getName());
                // Additional logic here
            }
        }
}
}
