
package Guild;

import javax.swing.*;
import DungeonoftheBrutalKing.MainGameScreen;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.ParseException;

public class SilverwardSentinels extends JPanel {
    private boolean isMember;
    private final boolean isGood = true; // Designates this guild as evil

    public SilverwardSentinels(boolean isMember) {
        this.isMember = isMember;
        setLayout(new BorderLayout());

        // Add an image to the panel
        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/DungeonoftheBrutalKing/Images/SilverwardSentinels.jpg")));
        add(imageLabel, BorderLayout.CENTER);

        // Create buttons
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        JButton buySpellsButton = new JButton("Buy Spells");
        JButton sellItemsButton = new JButton("Sell Items");
        JButton enterStorageButton = new JButton("Enter Storage");
        JButton exitRoomButton = new JButton("Exit Room");

        // Add buttons based on membership
        if (!isMember) {
            JButton joinGuildButton = new JButton("Join Guild");
            joinGuildButton.addActionListener(e -> {
                this.isMember = true;
                JOptionPane.showMessageDialog(this, "You have joined the Silverward Sentinels!");
                reloadPanel();
            });
            buttonPanel.add(joinGuildButton);
        } else {
            buttonPanel.add(buySpellsButton);
            buttonPanel.add(sellItemsButton);
            buttonPanel.add(enterStorageButton);
        }
        buttonPanel.add(exitRoomButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Add button actions
        buySpellsButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Buying spells..."));
        sellItemsButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Selling items..."));
        enterStorageButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Entering storage..."));
        exitRoomButton.addActionListener(e -> {
            try {
                MainGameScreen.getInstance().restoreOriginalPanel();
            } catch (IOException | InterruptedException | ParseException e1) {
                e1.printStackTrace();
            }
        });
    }

    private void reloadPanel() {
        removeAll();
        revalidate();
        repaint();
        new SilverwardSentinels(isMember);
    }

    public boolean isGood() {
        return isGood;
    }
}
