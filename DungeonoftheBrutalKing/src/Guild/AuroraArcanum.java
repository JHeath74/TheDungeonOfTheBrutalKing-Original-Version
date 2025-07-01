
package Guild;

import javax.swing.*;
import DungeonoftheBrutalKing.MainGameScreen;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.ParseException;

public class AuroraArcanum extends JPanel {
    // Indicates whether the player is a member of the guild
    private boolean isMember;

    // Designates this guild as good
    private final boolean isGood = true;

    // Description of the guild
    private final String description;

    /**
     * Constructor for the AuroraArcanum class.
     * Initializes the guild's description and sets up the UI components.
     *
     * @param isMember Indicates if the player is already a member of the guild.
     * @throws ParseException 
     * @throws InterruptedException 
     * @throws IOException 
     */
    public AuroraArcanum(boolean isMember) throws IOException, InterruptedException, ParseException {
        this.isMember = isMember;
        this.description = "The Aurora Arcanum is a guild of benevolent magic users dedicated to the pursuit of light and order.";
        setLayout(new BorderLayout());

        // If the player is not a member, display the description in the MessageTextPane
        if (!isMember) {

        	MainGameScreen.getInstance().setMessageTextPane(description);

        }

        // Add an image to the panel
        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/DungeonoftheBrutalKing/Images/AuroraArcanum.jpg")));
        add(imageLabel, BorderLayout.CENTER);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        JButton buySpellsButton = new JButton("Buy Spells");
        JButton sellItemsButton = new JButton("Sell Items");
        JButton enterStorageButton = new JButton("Enter Storage");
        JButton exitRoomButton = new JButton("Exit Room");

        // Add buttons based on membership status
        if (!isMember) {
            // If the player is not a member, show the "Join Guild" button
            JButton joinGuildButton = new JButton("Join Guild");
            joinGuildButton.addActionListener(e -> {
                this.isMember = true;
                JOptionPane.showMessageDialog(this, "You have joined the Aurora Arcanum!");
                try {
					reloadPanel();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // Reload the panel to update the UI
            });
            buttonPanel.add(joinGuildButton);
        } else {
            // If the player is a member, show other guild-related buttons
            buttonPanel.add(buySpellsButton);
            buttonPanel.add(sellItemsButton);
            buttonPanel.add(enterStorageButton);
        }
        buttonPanel.add(exitRoomButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Add actions for the buttons
        buySpellsButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Buying spells..."));
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
        new AuroraArcanum(isMember); // Create a new instance of the panel
    }

    /**
     * Returns whether the guild is good.
     *
     * @return true if the guild is good, false otherwise.
     */
    public boolean isGood() {
        return isGood;
    }

    /**
     * Returns the description of the guild.
     *
     * @return The guild's description.
     */
    public String getDescription() {
        return description;
    }
}
