
package Races;

import java.util.List;

public class Gnome {
    private String name;
    private String description;
    private String strength;
    private String weakness;
    private List<String> allowedClasses;

    public Gnome() {
        this.name = "Gnome";
        this.description = "";
        this.strength = "+10 Intelligence, +10 Dexterity";
        this.weakness = "-10 Strength, -10 Endurance";
        this.allowedClasses = List.of("Wizard", "Cleric", "Rogue");
    }

    public String getName() {
        return name;
    }

    public String getRaceDescription() {
        return """
        Gnome (Character Description)
        Gnomes are clever and resourceful, known for their curiosity and affinity with magic.
        They are quick-witted but physically small and less robust than other races.

        Strengths:
        - +10 Intelligence, +10 Dexterity

        Weaknesses:
        - -10 Strength, -10 Endurance

        Allowed Classes:
        - Wizard, Cleric, Rogue
        """;
    }

    public String getRaceImagePath() {
        return "src/DungeonoftheBrutalKing/Images/Race/Gnome.png";
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

    public void displayDetails() {
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Strength: " + strength);
        System.out.println("Weakness: " + weakness);
        System.out.println("Allowed Classes: " + String.join(", ", allowedClasses));
    }
}
