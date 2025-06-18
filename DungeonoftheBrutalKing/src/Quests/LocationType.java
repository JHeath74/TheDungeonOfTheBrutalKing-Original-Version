
// Java
package Quests;

public enum LocationType {
    STATIC, DYNAMIC, INTERACTIVE, HIDDEN;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
