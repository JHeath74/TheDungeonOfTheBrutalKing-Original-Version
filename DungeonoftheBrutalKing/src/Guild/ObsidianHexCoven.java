
package Guild;

import javax.swing.*;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.Charecter;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class ObsidianHexCoven extends JPanel {
    private static final long serialVersionUID = 1L;

    private final String guildName = "Obsidian Hex Coven";
    private boolean isMember;
    private final Alignment alignment = Alignment.EVIL;
    private final String description;

    public ObsidianHexCoven(boolean isMember) throws IOException, InterruptedException, ParseException {
        this.isMember = isMember;
        this.description = "The Obsidian Hex Coven is a guild of dark magic users who embrace chaos and power.";
        setLayout(new BorderLayout());

        Charecter character = Charecter.Singleton();
        ArrayList<String> inventory = character.CharInventory;

        if (!isMember && !inventory.contains("Obsidian Hex Coven Guild Ring")) {
            int choice = JOptionPane.showOptionDialog(
                this,
                "You are not a member of the Obsidian Hex Coven. Would you like to join?",
                "Join Guild",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Join", "Stay/Leave"},
                "Join"
            );

            if (choice == JOptionPane.YES_OPTION) {
                this.isMember = true;
                inventory.add("Obsidian Hex Coven Guild Ring");
                JOptionPane.showMessageDialog(this, "You have joined the Obsidian Hex Coven and received the Obsidian Hex Coven Guild Ring!");
            } else {
                JOptionPane.showMessageDialog(this, "You chose not to join the guild.");
                return;
            }
        }

        if (!isMember) {
            MainGameScreen.getInstance().setMessageTextPane(description);
        }

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/DungeonoftheBrutalKing/Images/ObsidianHexCovenRoom.jpg")));
        add(imageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        JButton buySpellsButton = new JButton("Buy Spells");
        JButton sellItemsButton = new JButton("Sell Items");
        JButton enterStorageButton = new JButton("Enter Storage");
        JButton exitRoomButton = new JButton("Exit Room");

        if (!isMember) {
            JButton joinGuildButton = new JButton("Join Guild");
            joinGuildButton.addActionListener(event -> {
                this.isMember = true;
                inventory.add("Obsidian Hex Coven Guild Ring");
                JOptionPane.showMessageDialog(this, "You have joined the Obsidian Hex Coven!");
                try {
                    reloadPanel();
                } catch (IOException | InterruptedException | ParseException e1) {
                    e1.printStackTrace();
                }
            });
            buttonPanel.add(joinGuildButton);
        } else {
            buttonPanel.add(buySpellsButton);
            buttonPanel.add(sellItemsButton);
            buttonPanel.add(enterStorageButton);
        }
        buttonPanel.add(exitRoomButton);

        add(buttonPanel, BorderLayout.SOUTH);

        buySpellsButton.addActionListener(event -> JOptionPane.showMessageDialog(this, "Buying spells..."));
        sellItemsButton.addActionListener(event -> JOptionPane.showMessageDialog(this, "Selling items..."));
        enterStorageButton.addActionListener(event -> JOptionPane.showMessageDialog(this, "Entering storage..."));
        exitRoomButton.addActionListener(event -> {
            try {
                MainGameScreen.getInstance().restoreOriginalPanel();
            } catch (IOException | InterruptedException | ParseException e1) {
                e1.printStackTrace();
            }
        });
    }

    private void reloadPanel() throws IOException, InterruptedException, ParseException {
        removeAll();
        revalidate();
        repaint();
        new ObsidianHexCoven(isMember);
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public String getDescription() {
        return description;
    }

    public String getGuildName() {
        return guildName;
    }
}
