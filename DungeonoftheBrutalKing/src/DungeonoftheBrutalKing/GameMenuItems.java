
package DungeonoftheBrutalKing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import SharedData.GameSettings;

/**
 * The GameMenuItems class provides functionality for displaying and managing
 * various game menu options such as character stats and inventory.
 * It uses Swing components to create and display the user interface for these menus.
 */
public class GameMenuItems {

    // Singleton instance of the character
    Charecter myChar = Charecter.Singleton();

    // Game settings instance for UI preferences
    GameSettings myGamePreferences = new GameSettings();

    /**
     * Displays the character stats in a new window.
     * The stats include attributes such as name, class, level, experience, and more.
     */
    public void Stats() {
        // Create a new frame for character stats
        JFrame statsFrame = new JFrame("Charecter Stats");

        // Text area to display stats
        JTextArea statsArea = new JTextArea();

        // Button to close the stats window
        JButton closeStats = new JButton("Ok");

        // Add action listener to close the stats window
        closeStats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statsFrame.dispose(); // Close the stats frame
            }
        });

        // Add components to the frame
        statsFrame.add(statsArea, BorderLayout.CENTER);
        statsFrame.add(closeStats, BorderLayout.SOUTH);

        // Configure text area font and colors
        statsArea.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
        statsArea.setForeground(myGamePreferences.getColorWhite());
        statsArea.setBackground(myGamePreferences.getColorBlack());
        statsArea.setVisible(true);

        // Configure frame size and visibility
        statsFrame.setSize(400, 600);
        statsFrame.setLocationRelativeTo(null);
        statsFrame.setVisible(true);
        statsFrame.toFront();
        statsFrame.requestFocus();

        // Populate the stats area with character information
        statsArea.setText("Name: " + myChar.CharInfo.get(0) + "\n");
        statsArea.append("Class: " + myChar.CharInfo.get(1) + "\n");
        statsArea.append("Level: " + myChar.CharInfo.get(2) + "\n");
        statsArea.append("Experience: " + myChar.CharInfo.get(3) + "\n");
        statsArea.append("Location:  \n"
        		+ "\tDungeon level: "+(int)myChar.getZ() +"\n"
        		+ "\tX: " + (int)myChar.getX() + "\n"
                + "\tY: " + (int)myChar.getY() +  "\n");
        statsArea.append("Hit Points: " + myChar.CharInfo.get(4) + "\n");
        statsArea.append("Stamina: " + myChar.CharInfo.get(5) + "\n");
        statsArea.append("Charisma: " + myChar.CharInfo.get(6) + "\n");
        statsArea.append("Strength: " + myChar.CharInfo.get(7) + "\n");
        statsArea.append("Intelligence: " + myChar.CharInfo.get(8) + "\n");
        statsArea.append("Wisdom: " + myChar.CharInfo.get(9) + "\n");
        statsArea.append("Agility: " + myChar.CharInfo.get(10) + "\n");
        statsArea.append("Alignment: " + myChar.CharInfo.get(21) + "\n");
        statsArea.append("Equipped Weapon: " + myChar.CharInfo.get(18) + "\n");
        statsArea.append("Equipped Armor: " + myChar.CharInfo.get(19) + "\n");
        statsArea.append("Equipped Shield: " + myChar.CharInfo.get(20) + "\n");
        
    }

    /**
     * Displays the character's inventory in a new window.
     * The inventory includes items such as gold, gems, food, weapons, and armor.
     */
    public void Inventory() {
        // Create a new frame for inventory
        JFrame invFrame = new JFrame("Inventory");

        // Text area to display inventory
        JTextArea invArea = new JTextArea();

        // Button to close the inventory window
        JButton closeStats = new JButton("Ok");

        // Add action listener to close the inventory window
        closeStats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                invFrame.dispose(); // Close the inventory frame
            }
        });

        // Add components to the frame
        invFrame.add(invArea, BorderLayout.CENTER);
        invFrame.add(closeStats, BorderLayout.SOUTH);

        // Configure text area font and colors
        invArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));

        // Background and text colors
        Color sab = new Color(25, 50, 75); // Background color
        Color sabText = new Color(255, 255, 255); // Text color
        invArea.setForeground(sabText);
        invArea.setBackground(sab);
        invArea.setVisible(true);

        // Configure frame size and visibility
        invFrame.setLocationRelativeTo(null);
        invFrame.setSize(400, 600);
        invFrame.setVisible(true);
        invFrame.toFront();
        invFrame.requestFocus();

        // Populate the inventory area with character inventory information


StringBuilder sb = new StringBuilder();
int width = 40;
sb.append(String.format("%" + ((width + "Money".length()) / 2) + "s", "Money") + "\n");
sb.append("Gold: ").append(myChar.CharInfo.get(12)).append("\n");
sb.append("Gems: ").append(myChar.CharInfo.get(15)).append("\n");
sb.append(String.format("%" + ((width + "Misc".length()) / 2) + "s", "Misc") + "\n");
sb.append("Food: ").append(myChar.CharInfo.get(13)).append("\n");
sb.append("Torches: ").append(myChar.CharInfo.get(14)).append("\n");
sb.append(String.format("%" + ((width + "Weapons and Armour".length()) / 2) + "s", "Weapons and Armour") + "\n");


// Equipped items and alignment
sb.append("Equipped Weapon: ").append(myChar.CharInfo.get(18)).append("\n");
sb.append("Equipped Armor: ").append(myChar.CharInfo.get(19)).append("\n");
sb.append("Equipped Shield: ").append(myChar.CharInfo.get(20)).append("\n");


// Display learned spells
sb.append(String.format("%" + ((width + "Your Spells".length()) / 2) + "s", "Your Spells") + "\n");
if (myChar.SpellsLearned != null && !myChar.SpellsLearned.isEmpty()) {
    for (String spell : myChar.SpellsLearned) {
        sb.append(spell).append("\n");
    }
} else {
    sb.append("No spells learned yet.\n");
}

invArea.setText(sb.toString());
    }
}
