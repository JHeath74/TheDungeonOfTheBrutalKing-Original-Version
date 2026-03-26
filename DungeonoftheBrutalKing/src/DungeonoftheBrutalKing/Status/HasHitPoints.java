
// src/Status/HasHitPoints.java
package DungeonoftheBrutalKing.Status;

public interface HasHitPoints {
    int getHitPoints();
    void setHitPoints(int hp);
    int getMaxHitPoints();
    void addStatus(Status effectStatus);
	String getClassName();
}
