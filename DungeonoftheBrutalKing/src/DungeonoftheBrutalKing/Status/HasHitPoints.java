
// src/Status/HasHitPoints.java
package DungeonoftheBrutalKing.Status;

import DungeonoftheBrutalKing.MainGameScreen;

public interface HasHitPoints {
    int getHitPoints();
    void setHitPoints(int hp);
    int getMaxHitPoints();
    void addStatus(Status effectStatus);
	String getClassName();
	void takeDamage(int damage, MainGameScreen mainGameScreen);
}
