
package Races;

import java.util.Arrays;
import java.util.List;

public class Human {
    private String name;
    private String description;
    private String strength;
    private String weakness;
    private List<String> allowedClasses;

    public Human() {
        this.name = "Human";
        this.description = "";
        this.strength = "+1 To All Core Stats";
        this.weakness = "-5 Endurance, -10% Health and Mana Pools";
        this.allowedClasses = Arrays.asList("Bard", "Cleric", "Hunter", "Paladin", "Rogue", "Warrior", "Wizard");
    }

    public String getName() {
        return name;
    }

    public String getRaceDescription() {
        return """
        Human (Character Description)
        Versatile and ambitious, humans are the most adaptable race in the realms. Found in every corner of the world, they vary wildly in culture, belief, and talent.
        What binds them is an indomitable spirit—whether forging empires, unlocking arcane secrets, or mastering the blade, humans push boundaries like no other.

        Though flexible and determined, humans lack the innate hardiness or arcane lineage of other races. As a result, they suffer a 10% reduction in health, magic points, and action points, representing their physical
        and magical limitations. This drawback often forces them to rely on ingenuity, grit, and teamwork to survive and thrive.

        Strengths:
        - Ability Score Increase: +2 to all core stats (Agility, Strength, Wisdom, Intelligence)
        - Weaknesses: -5 to Endurance, -10% to Health, Magic Points, or Action Points

        Allowed Classes:
        - Bard, Cleric, Hunter, Paladin, Rogue, Warrior, Wizard
        """;
    }

    public String getRaceImagePath() {
        return "src/DungeonoftheBrutalKing/Images/Race/Human.png";
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
    		// Increase all stats (assuming indices 0 to CharInfo.size()-1 are stats)
    		for (int i = 0; i < CharInfo.size(); i++) {
    			int stat = Integer.parseInt(CharInfo.get(i));
    			CharInfo.set(i, String.valueOf(stat + 2));
    		}
System.out.println("All core stats increased by 2.");
        }

    	public void setWeakness(List<String> CharInfo) {
    	    // Reduce Endurance by 5
    	    int endurance = Integer.parseInt(CharInfo.get(7));
    	    CharInfo.set(7, String.valueOf(endurance - 5));

    	    // Reduce HP, MP, and AP by 10%
    	    int hpIndex = 11; // adjust if needed
    	    int mpIndex = 12; // adjust if needed
    	    int apIndex = 13; // adjust if needed

    	    int hp = Integer.parseInt(CharInfo.get(hpIndex));
    	    int mp = Integer.parseInt(CharInfo.get(mpIndex));
    	    int ap = Integer.parseInt(CharInfo.get(apIndex));

    	    CharInfo.set(hpIndex, String.valueOf((int)(hp * 0.9)));
    	    CharInfo.set(mpIndex, String.valueOf((int)(mp * 0.9)));
    	    CharInfo.set(apIndex, String.valueOf((int)(ap * 0.9)));

    	    System.out.println("Endurance decreased by 5. HP, MP, and AP reduced by 10%.");
    	}

    public void displayDetails() {
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Strength: " + strength);
        System.out.println("Weakness: " + weakness);
        System.out.println("Allowed Classes: " + String.join(", ", allowedClasses));
    }
}
