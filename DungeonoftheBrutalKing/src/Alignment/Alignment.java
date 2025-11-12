
// src/SharedData/Alignment.java
package Alignment;

public enum Alignment {
    POSITIVE("Aligned with benevolent or positive actions."),
    NEGATIVE("Aligned with malevolent or negative actions."),
    NOT_ALIGNED("Not Aligned as either Positive or Negative Actions.");

    private final String description;

    Alignment(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
