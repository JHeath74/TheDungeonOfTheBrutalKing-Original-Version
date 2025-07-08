
// File: CrimsonBlades.java
package Guild;

import javax.swing.*;
import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.Alignment;
import DungeonoftheBrutalKing.Charecter;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;

public class DirgeweaversChorus extends JPanel {

    private static final long serialVersionUID = 1L;

    private final String guildName = "Aurora Arcanum";
    private boolean isMember;
    private String description = "";
    private Alignment alignment;

    /**
     * Constructor for the Dirgeweavers Chorus class.
     * Initializes the guild's description and dungeon level.
     *
     * @param isMember Indicates if the player is already a member of the guild.
     * @throws ParseException
     * @throws InterruptedException
     * @throws IOException
     */
    public DirgeweaversChorus(boolean isMember) throws IOException, InterruptedException, ParseException {
        this.isMember = isMember;
        this.alignment = Alignment.GOOD;

     // Adding description to DirgeweaversChorus
     this.description = "Dirgeweavers Chorus is a guild of enlightened sorcerers who harness the power of celestial magic to bring balance and wisdom to the realm.";

        
        setLayout(new BorderLayout());

        // Access the character's inventory and action points
        Charecter character = Charecter.Singleton();
        ArrayList<String> inventory = character.CharInventory;
        final int[] actionPoints = {character.getActionPoints()};

        // Check for guild ring in inventory
        if (!isMember && !inventory.contains("Dirgeweavers Chorus Guild Ring")) {
            int choice = JOptionPane.showOptionDialog(
                this,
                "You are not a member of the Dirgeweavers Chorus. Would you like to join?",
                "Join Guild",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Join", "Stay/Leave"},
                "Join"
            );

            if (choice == JOptionPane.YES_OPTION) {
                this.isMember = true;
                inventory.add("Dirgeweavers Chorus Guild Ring");
                JOptionPane.showMessageDialog(this, "You have joined the Dirgeweavers Chorus and received the Dirgeweavers Chorus Guild Ring!");
            } else {
                JOptionPane.showMessageDialog(this, "You chose not to join the guild.");
                return;
            }
        }

        // If the player is not a member, display the description in the MessageTextPane
        if (!isMember) {
            MainGameScreen.getInstance().setMessageTextPane(description);
        }

        // Add an image to the panel
        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/DungeonoftheBrutalKing/Images/DirgeweaversChorus.jpg")));
        add(imageLabel, BorderLayout.CENTER);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        JButton useSkillsButton = new JButton("Use Skills");
        JButton sellItemsButton = new JButton("Sell Items");
        JButton enterStorageButton = new JButton("Enter Storage");
        JButton exitRoomButton = new JButton("Exit Room");

        // Add buttons based on membership status
        if (!isMember) {
            JButton joinGuildButton = new JButton("Join Guild");
            joinGuildButton.addActionListener(event -> {
                this.isMember = true;
                inventory.add("Dirgeweavers Chorus Guild Ring");
                JOptionPane.showMessageDialog(this, "You have joined the Dirgeweavers Chorus!");
                try {
                    reloadPanel();
                } catch (IOException | InterruptedException | ParseException ex) {
                    ex.printStackTrace();
                }
            });
            buttonPanel.add(joinGuildButton);
        } else {
            buttonPanel.add(useSkillsButton);
            buttonPanel.add(sellItemsButton);
            buttonPanel.add(enterStorageButton);
        }
        buttonPanel.add(exitRoomButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Add actions for the buttons
        useSkillsButton.addActionListener(event -> {
            if (actionPoints[0] >= 10) {
                actionPoints[0] -= 10;
                character.updateActionPoints(actionPoints[0]);
                JOptionPane.showMessageDialog(this, "You used a skill! Remaining Action Points: " + actionPoints[0]);
            } else {
                JOptionPane.showMessageDialog(this, "Not enough Action Points to use a skill!");
            }
        });

        sellItemsButton.addActionListener(event -> JOptionPane.showMessageDialog(this, "Selling items..."));
        enterStorageButton.addActionListener(event -> JOptionPane.showMessageDialog(this, "Entering storage..."));
        exitRoomButton.addActionListener(event -> {
            try {
                MainGameScreen.getInstance().restoreOriginalPanel();
            } catch (IOException | InterruptedException | ParseException ex) {
                ex.printStackTrace();
            }
        });
    }



    /**
     * Reloads the panel to reflect changes in membership status.
     *
     * @throws ParseException
     * @throws InterruptedException
     * @throws IOException
     */
    private void reloadPanel() throws IOException, InterruptedException, ParseException {
        removeAll();
        revalidate();
        repaint();
        add(new DirgeweaversChorus(isMember));
    }



    /**a
     * Returns the description of the guild.
     *
     * @return The guild's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the alignment of the guild.
     *
     * @return The guild's alignment.
     */
    public Alignment getAlignment() {
        return alignment;
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
