
package Races;

import java.util.Arrays;
import java.util.List;

public class Dragonborne {
    private String name;
    public String description;
    private String strength;
    private String weakness;
    private List<String> allowedClasses;

    public Dragonborne() {
        this.name = "Dragonborne";
        this.description = "";

        this.strength = "+10 Strength, +5 Endurance, +5 Charisma";
        this.weakness = "-5 Wisdom";
        this.allowedClasses = Arrays.asList("Warrior", "Paladin", "Rogue");
    }

    public String getName() {
        return name;
    }


    public String getRaceDescription() {
        return """
        Dragonborne (Character Description)
        Dragonborn are proud, strong, and resilient, with a natural affinity for magic and combat.
        Their draconic heritage grants them great power, but their pride can sometimes cloud their judgment.

        Strengths:
        - +10 Strength, +5 Endurance, +5 Charisma

        Weaknesses:
        - -5 Wisdom

        Allowed Classes:
        - Warrior, Rogue, Paladin
        """;
}


    public String getRaceImagePath() {
        return "src/DungeonoftheBrutalKing/Images/Race/Dragonborne.png";
    }

    public String getStrength() {
        return strength;
    }

    public String getWeakness() {
        return weakness;
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

    // Stat modification methods (adjust indices as needed)
    public void setStrength(List<String> CharInfo) {
        int strength = Integer.parseInt(CharInfo.get(2));
        CharInfo.set(2, String.valueOf(strength + 10));
        int endurance = Integer.parseInt(CharInfo.get(0));
        CharInfo.set(0, String.valueOf(endurance + 5));
        int charisma = Integer.parseInt(CharInfo.get(1));
        CharInfo.set(1, String.valueOf(charisma + 5));
        System.out.println("Strength increased by 10, Endurance and Charisma increased by 5.");
    }

    public void setWeakness(List<String> CharInfo) {
        int wisdom = Integer.parseInt(CharInfo.get(4));
        CharInfo.set(4, String.valueOf(wisdom - 5));
        System.out.println("Wisdom decreased by 5.");
    }
}
