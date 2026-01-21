package Status;

public interface HasHitPoints {
    int getHitPoints();
    void setHitPoints(int hp);
    int getMaxHitPoints();
	void addStatus(Status effectStatus);
}