
package Races;

import java.util.Arrays;
import java.util.List;

public class HighElf {
    private String name;
    private String description;
    private String strength;
    private String weakness;
    private List<String> allowedClasses;

    public HighElf() {
        this.name = "High Elf";
        this.description = "";
        this.strength = "+10 Wisdom, +10 Intelligence";
        this.weakness = "-10 Strength, -10 Endurance";
        this.allowedClasses = Arrays.asList("Rogue", "Bard", "Ranger");
    }

    public String getName() {
        return name;
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
    




    public String getRaceDescription() {
        return """
        High Elf (Character Description)
        High Elves are renowned for their wisdom and intelligence, excelling in both magic and leadership. 
        However, they are physically weaker and less enduring than other races.

        Strengths:
        - +10 Wisdom, +10 Intelligence

        Weaknesses:
        - -10 Strength, -10 Endurance

        Allowed Classes:
        - Wizard, Cleric, Paladin
        """;
    }



    
    public String getRaceImagePath() {
        return "src/DungeonoftheBrutalKing/Images/Race/HighElf.png";
    }

    public void displayDetails() {
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Strength: " + strength);
        System.out.println("Weakness: " + weakness);
        System.out.println("Allowed Classes: " + String.join(", ", allowedClasses));
    }

    // Example stat modification methods (adjust indices as needed)
    public void setStrength(List<String> CharInfo) {
        int wisdom = Integer.parseInt(CharInfo.get(10));
        CharInfo.set(10, String.valueOf(wisdom + 10));
        int intelligence = Integer.parseInt(CharInfo.get(9));
        CharInfo.set(9, String.valueOf(intelligence + 10));
        System.out.println("Wisdom and Intelligence increased by 10.");
    }

    public void setWeakness(List<String> CharInfo) {
        int strength = Integer.parseInt(CharInfo.get(5));
        CharInfo.set(5, String.valueOf(strength - 10));
        int endurance = Integer.parseInt(CharInfo.get(7));
        CharInfo.set(7, String.valueOf(endurance - 10));
        System.out.println("Strength and Endurance decreased by 10.");
    }
}
