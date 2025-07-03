package Spells;

public enum SpellAlignment {
    NON_ALIGNED("A spell with no specific alignment."),
    GOOD("A spell aligned with good intentions."),
    NEUTRAL("A spell with a neutral alignment."),
    EVIL("A spell aligned with dark or evil intentions.");

    private final String description;

    SpellAlignment(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
