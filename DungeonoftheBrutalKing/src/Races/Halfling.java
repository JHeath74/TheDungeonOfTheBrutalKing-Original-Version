package Races;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a Halfling race with specific strengths and weaknesses.
 */
public class Halfling {
    private String name;
    private String description;
    private String strength;
    private String weakness;
    private List<String> allowedClasses;

    public Halfling(String name, String description, String strength, String weakness) {
        this.name = "Halfling";
        this.description = description;
        this.strength = strength;
        this.weakness = weakness;
        this.allowedClasses = Arrays.asList("Hunter", "Paladin", "Rogue");
    }

    public String getName() {
        return "Halfling";
    }


public String getRaceDescription() {
    return """
    Halfling (Character Description)
    Halflings are nimble and quick-witted, excelling at stealth and agility.
    They are not as strong or resilient as other races, but their resourcefulness is unmatched.

    Strengths:
    - +10 Agility, +10 Stealth

    Weaknesses:
    - -10 Strength, -10 Endurance

    Allowed Classes:
    - Rogue, Bard, Ranger
    """;
}

    
    public String getRaceImagePath() {
        return "src/DungeonoftheBrutalKing/Images/Race/Halfling.png";
    }

    /**
     * Increases Agility (index 6) and Stamina (index 8) by 5 in the provided CharInfo list.
     */
    public void setStrength(List<String> CharInfo) {
        int agility = Integer.parseInt(CharInfo.get(6));
        CharInfo.set(6, String.valueOf(agility + 5));
        int stamina = Integer.parseInt(CharInfo.get(8));
        CharInfo.set(8, String.valueOf(stamina + 5));
        System.out.println("Agility and Stamina increased by 5.");
    }

    /**
     * Decreases Intelligence (index 9) and Wisdom (index 10) by 5 in the provided CharInfo list.
     */
    public void setWeakness(List<String> CharInfo) {
        int intelligence = Integer.parseInt(CharInfo.get(9));
        CharInfo.set(9, String.valueOf(intelligence - 5));
        int wisdom = Integer.parseInt(CharInfo.get(10));
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
