
package Races;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a Dwarf race with specific strengths and weaknesses.
 */
public class Dwarf {
    private String name;
    private String description;
    private String strength;
    private String weakness;
    private List<String> allowedClasses;

    public Dwarf(String name, String description, String strength, String weakness) {
        this.name = name;
        this.description = description;
        this.strength = strength;
        this.weakness = weakness;
        this.allowedClasses = Arrays.asList("Bard", "Hunter", "Rogue");
    }

    public String getName() {
        return "Dwarf";
    }


public String getRaceDescription() {
    return "Dwarf (Character Description)\n"
        + "Stout and resilient, Dwarves are known for their endurance and agility in harsh environments.\n"
        + "While physically adept, they are less inclined toward scholarly pursuits.\n\n"
        + "Strengths:\n"
        + "- Ability Score Increase: +5 Agility\n"
        + "Weaknesses:\n"
        + "-5 Intelligence, -5 Wisdom\n"
        + "Allowed Classes:\n"
        + "- " + String.join(", ", allowedClasses);
}

    
    public String getRaceImagePath() {
        return "src/DungeonoftheBrutalKing/Images/Race/Dwarf.png";
    }

    /**
     * Increases Agility (index 6) by 5 in the provided CharInfo list.
     */
    public void setStrength(List<String> CharInfo) {
        int agility = Integer.parseInt(CharInfo.get(6));
        CharInfo.set(6, String.valueOf(agility + 5));
        System.out.println("Agility increased by 5.");
    }

    /**
     * Decreases Intelligence (index 9) and Wisdom (index 10) by 5 in the provided CharInfo list.
     */
    public void setWeakness(List<String> CharInfo) {
        int intelligence = Integer.parseInt(CharInfo.get(9));
        int wisdom = Integer.parseInt(CharInfo.get(10));
        CharInfo.set(9, String.valueOf(intelligence - 5));
        CharInfo.set(10, String.valueOf(wisdom - 5));
        System.out.println("Intelligence and Wisdom decreased by 5.");
    }

    public List<String> getAllowedClasses() {
        return allowedClasses;
    }

    public void setAllowedClasses(List<String> allowedClasses) {
        this.allowedClasses = allowedClasses;
    }

    public void displayDetails() {
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Strength: " + strength);
        System.out.println("Weakness: " + weakness);
        System.out.println("Allowed Classes: " + String.join(", ", allowedClasses));
    }
}
