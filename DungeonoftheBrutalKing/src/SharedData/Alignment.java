
package SharedData;

public enum Alignment {
    NOT_ALIGNED("Not aligned with any moral or ethical stance."),
    EVIL("Aligned with malevolent and selfish intentions."),
    GOOD("Aligned with benevolent and selfless intentions.");

    private final String description;

    Alignment(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
