
package Guild;

import javax.swing.*;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.Charecter;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class CrimsonBlades extends JPanel {

    private static final long serialVersionUID = 1L; // Added serialVersionUID

    private final String guildName = "Crimson Blades";

    // Indicates whether the player is a member of the guild
    private boolean isMember;

    // Designates this guild as evil
    private final boolean isEvil = true;

    private String description = "";

    /**
     * Constructor for the CrimsonBlades class.
     * Initializes the guild's description and sets up the UI components.
     *
     * @param isMember Indicates if the player is already a member of the guild.
     * @throws ParseException
     * @throws InterruptedException
     * @throws IOException
     */
    public CrimsonBlades(boolean isMember) throws IOException, InterruptedException, ParseException {
        this.isMember = isMember;
        this.description = "The Crimson Blades is a guild of skilled warriors who thrive on combat and power.";
        setLayout(new BorderLayout());

        // Access the character's inventory and action points
        Charecter character = Charecter.Singleton();
        ArrayList<String> inventory = character.CharInventory;
        final int[] actionPoints = {character.getActionPoints()}; // Use a mutable wrapper

        // Check for guild ring in inventory
        if (!isMember && !inventory.contains("Crimson Blades Guild Ring")) {
            int choice = JOptionPane.showOptionDialog(
                this,
                "You are not a member of the Crimson Blades. Would you like to join?",
                "Join Guild",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Join", "Stay/Leave"},
                "Join"
            );

            if (choice == JOptionPane.YES_OPTION) {
                this.isMember = true;
                inventory.add("Crimson Blades Guild Ring"); // Add the guild ring with the correct name
                JOptionPane.showMessageDialog(this, "You have joined the Crimson Blades and received the Crimson Blades Guild Ring!");
            } else {
                JOptionPane.showMessageDialog(this, "You chose not to join the guild.");
                return; // Exit the constructor if the player doesn't join
            }
        }

        // If the player is not a member, display the description in the MessageTextPane
        if (!isMember) {
            MainGameScreen.getInstance().setMessageTextPane(description);
        }

        // Add an image to the panel
        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/DungeonoftheBrutalKing/Images/CrimsonBlades.jpg")));
        add(imageLabel, BorderLayout.CENTER);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        JButton useSkillsButton = new JButton("Use Skills");
        JButton sellItemsButton = new JButton("Sell Items");
        JButton enterStorageButton = new JButton("Enter Storage");
        JButton exitRoomButton = new JButton("Exit Room");

        // Add buttons based on membership status
        if (!isMember) {
            // If the player is not a member, show the "Join Guild" button
            JButton joinGuildButton = new JButton("Join Guild");
            joinGuildButton.addActionListener(e -> {
                this.isMember = true;
                inventory.add("Crimson Blades Guild Ring"); // Add the guild ring with the correct name
                JOptionPane.showMessageDialog(this, "You have joined the Crimson Blades!");
                try {
                    reloadPanel();
                } catch (IOException | InterruptedException | ParseException e1) {
                    e1.printStackTrace();
                } // Reload the panel to update the UI
            });
            buttonPanel.add(joinGuildButton);
        } else {
            // If the player is a member, show other guild-related buttons
            buttonPanel.add(useSkillsButton);
            buttonPanel.add(sellItemsButton);
            buttonPanel.add(enterStorageButton);
        }
        buttonPanel.add(exitRoomButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Add actions for the buttons
        useSkillsButton.addActionListener(e -> {
            if (actionPoints[0] >= 10) { // Example: Each skill use costs 10 action points
                actionPoints[0] -= 10;
                character.updateActionPoints(actionPoints[0]); // Update the character's action points
                JOptionPane.showMessageDialog(this, "You used a skill! Remaining Action Points: " + actionPoints[0]);
            } else {
                JOptionPane.showMessageDialog(this, "Not enough Action Points to use a skill!");
            }
        });

        sellItemsButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Selling items..."));
        enterStorageButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Entering storage..."));
        exitRoomButton.addActionListener(e -> {
            try {
                // Restore the original panel in the main game screen
                MainGameScreen.getInstance().restoreOriginalPanel();
            } catch (IOException | InterruptedException | ParseException e1) {
                e1.printStackTrace();
            }
        });
    }

    /**
     * Reloads the panel to reflect changes in membership status.
     * @throws ParseException
     * @throws InterruptedException
     * @throws IOException
     */
    private void reloadPanel() throws IOException, InterruptedException, ParseException {
        removeAll(); // Remove all components from the panel
        revalidate(); // Revalidate the panel
        repaint(); // Repaint the panel
        new CrimsonBlades(isMember); // Create a new instance of the panel
    }

    /**
     * Returns whether the guild is evil.
     *
     * @return true if the guild is evil, false otherwise.
     */
    public boolean isEvil() {
        return isEvil;
    }

    /**
     * Returns the description of the guild.
     *
     * @return The guild's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the name of the guild.
     *
     * @return The guild's name.
     */
    public String getGuildName() {
        return guildName;
    }
}
