
// Java
package SharedData;

public enum QuestLocationType {
    STATIC, DYNAMIC, INTERACTIVE, HIDDEN;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
