
package Races;

import java.util.Arrays;
import java.util.List;

public class Elf {
    private String name;
    private String description;
    private String strength;
    private String weakness;
    private List<String> allowedClasses;

    public Elf() {
        this.name = "Elf";
        this.description = "";
        this.strength = "+10 Agility, +5 Wisdom, +5 Intelligence";
        this.weakness = "-5 Strength, -5 Endurance";
        this.allowedClasses = Arrays.asList("Hunter", "Bard", "Paladin", "Cleric", "Wizard");
    }

    public String getName() {
        return name;
    }

    public String getRaceDescription() {
        return """
         Elf (Character Description)
        Elves are graceful and agile, excelling in speed and dexterity.
        However, their focus on agility comes at the expense of raw physical strength.

        Strengths:
        - Ability Score Increase: +10 Agility, +5 Wisdom, +5 Intelligence

        Weaknesses:
        -5 Strength, -5 Endurance

        Allowed Classes:
        - Hunter, Bard, Paladin, Cleric, Wizard
        """;
    }

    public String getRaceImagePath() {
        return "src/DungeonoftheBrutalKing/Images/Race/Elf.png";
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

 
    	public void setStrength(List<String> CharInfo) {
    		int agility = Integer.parseInt(CharInfo.get(6));
            CharInfo.set(6, String.valueOf(agility + 10));
            int wisdom = Integer.parseInt(CharInfo.get(10));
            CharInfo.set(10, String.valueOf(wisdom + 5));
            int intelligence = Integer.parseInt(CharInfo.get(9));
            CharInfo.set(9, String.valueOf(intelligence + 5));
            System.out.println("Agility increased by 10. Wisdom and Intelligence increased by 5.");
        }

    	public void setWeakness(List<String> CharInfo) {
    		 int strength = Integer.parseInt(CharInfo.get(7));
    	        CharInfo.set(7, String.valueOf(strength - 5));
    	        System.out.println("Strength decreased by 5.");
    	}

    public void displayDetails() {
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Strength: " + strength);
        System.out.println("Weakness: " + weakness);
        System.out.println("Allowed Classes: " + String.join(", ", allowedClasses));
    }
}
