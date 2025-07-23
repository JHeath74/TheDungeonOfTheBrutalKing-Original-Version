
package Race;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a Human race in a game with specific traits, strengths, and weaknesses.
 */
public class Human {
    // Fields to store the human's attributes
    private String name; // Name of the human
    private String description; // Description of the human's traits and flavor
    private String strength; // Strength attribute
    private String weakness; // Weakness attribute
    private List<String> allowedClasses; // List of allowed classes for the human

    /**
     * Constructor to initialize the Human object with its attributes.
     *
     * @param name        The name of the human.
     * @param description A description of the human's traits and flavor.
     * @param strength    The strength attribute of the human.
     * @param weakness    The weakness attribute of the human.
     */
    public Human(String name, String description, String strength, String weakness) {
        this.name = name;
        this.description = description;
        this.strength = strength;
        this.weakness = weakness;
        // Initialize allowed classes with predefined values
        this.allowedClasses = Arrays.asList("Bard", "Cleric", "Hunter", "Paladin", "Rogue", "Warrior", "Wizard");
    }

    /**
     * Returns the name of the human.
     *
     * @return The name "Human".
     */
    public String getName() {
        return "Human";
    }

    /**
     * Returns a detailed description of the human's traits and flavor.
     *
     * @return A multiline string describing the human.
     */
    public String getDescription() {
        return """
        Human (Character Description)
        Versatile and ambitious, humans are the most adaptable race in the realms.
        Found in every corner of the world, they vary wildly in culture, belief, and talent.
        What binds them is an indomitable spirit—whether forging empires, unlocking arcane secrets,
        or mastering the blade, humans push boundaries like no other.

        Though flexible and determined, humans lack the innate hardiness or arcane lineage of other races.
        As a result, they suffer a 10% reduction in both health and mana pools, representing their physical
        and magical limitations. This drawback often forces them to rely on ingenuity, grit, and teamwork
        to survive and thrive.

        Traits & Flavor:
        - Adaptability – Balanced stat bonus allows flexible builds
        - Innate Courage – Resistant to fear-related affects
        - Rapid Advancement – Learns quickly and progresses fast

        Strengths:
        - Ability Score Increase: +1 to all core stats (Agility, Strength, Wisdom, Intelligence)
        - Weaknesses: -10% to HP and MP
        """;
    }

    /**
     * Updates the stats in the provided CharInfo list for indices 6 to 11.
     *
     * @param CharInfo A list of character information stats.
     */
    public void updateStatsInCharInfo(List<String> CharInfo) {
        for (int i = 6; i <= 11; i++) {
            int currentStat = Integer.parseInt(CharInfo.get(i)); // Parse the stat value
            CharInfo.set(i, String.valueOf(currentStat + 1)); // Increment and update the stat
        }
        System.out.println("Stats in CharInfo have been updated for indices 6-11.");
    }

    /**
     * Applies the human's strength by updating stats in CharInfo.
     *
     * @param CharInfo A list of character information stats.
     */
    public void setStrength(List<String> CharInfo) {
        updateStatsInCharInfo(CharInfo);
    }

    /**
     * Applies the human's weakness by decreasing stats at indices 4 and 5 by 10%.
     *
     * @param CharInfo A list of character information stats.
     */
    public void Weakness(List<String> CharInfo) {
        for (int i = 4; i <= 5; i++) {
            double currentStat = Double.parseDouble(CharInfo.get(i)); // Parse the stat value
            currentStat *= 0.9; // Decrease by 10%
            CharInfo.set(i, String.valueOf(currentStat)); // Update the stat
        }
        System.out.println("Weakness applied: indices 4 and 5 decreased by 10%.");
    }

    /**
     * Returns the list of allowed classes for the human.
     *
     * @return A list of allowed classes.
     */
    public List<String> getAllowedClasses() {
        return allowedClasses;
    }

    /**
     * Sets the allowed classes for the human.
     *
     * @param allowedClasses A list of allowed classes to set.
     */
    public void setAllowedClasses(List<String> allowedClasses) {
        this.allowedClasses = allowedClasses;
    }

    /**
     * Displays the details of the human, including name, description, strength, weakness, and allowed classes.
     */
    public void displayDetails() {
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Strength: " + strength);
        System.out.println("Weakness: " + weakness);
        System.out.println("Allowed Classes: " + String.join(", ", allowedClasses));
    }
}
