
package Guild;

public enum Alignment {
    NOT_ALIGNED("Not aligned with any moral or ethical stance."),
    EVIL("Aligned with malevolent and selfish intentions."),
    NEUTRAL("Maintains a balance between good and evil."),
    GOOD("Aligned with benevolent and selfless intentions.");

    private final String description;

    Alignment(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
