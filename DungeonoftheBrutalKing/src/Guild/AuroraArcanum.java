
package Guild;

import javax.swing.*;
import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.Alignment;
import DungeonoftheBrutalKing.Charecter;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class AuroraArcanum extends JPanel {

    private static final long serialVersionUID = 1L;

    private final String guildName = "Aurora Arcanum";
    private boolean isMember;
    private String description = "";
    private Alignment alignment;

    public AuroraArcanum(boolean isMember) throws IOException, InterruptedException, ParseException {
        this.isMember = isMember;
        this.alignment = Alignment.GOOD;

        this.description = "The Aurora Arcanum is a guild of enlightened sorcerers who harness the power of celestial magic to bring balance and wisdom to the realm.";

        setLayout(new BorderLayout());

        Charecter character = Charecter.Singleton();
        ArrayList<String> inventory = character.CharInventory;

        if (!isMember && !inventory.contains("Aurora Arcanum Guild Ring")) {
            int choice = JOptionPane.showOptionDialog(
                this,
                "You are not a member of the Aurora Arcanum. Would you like to join?",
                "Join Guild",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Join", "Stay/Leave"},
                "Join"
            );

            if (choice == JOptionPane.YES_OPTION) {
                this.isMember = true;
                inventory.add("Aurora Arcanum Guild Ring");
                JOptionPane.showMessageDialog(this, "You have joined the Aurora Arcanum and received the Aurora Arcanum Guild Ring!");
            } else {
                JOptionPane.showMessageDialog(this, "You chose not to join the guild.");
                return;
            }
        }

        if (!isMember) {
            MainGameScreen.getInstance().setMessageTextPane(description);
        }

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/DungeonoftheBrutalKing/Images/CrimsonBlades.jpg")));
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
                inventory.add("Aurora Arcanum Guild Ring");
                JOptionPane.showMessageDialog(this, "You have joined the Aurora Arcanum!");
                try {
                    reloadPanel();
                } catch (IOException | InterruptedException | ParseException ex) {
                    ex.printStackTrace();
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

        buySpellsButton.addActionListener(event -> buyGuildSpell());
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

    private void buyGuildSpell() {
        Charecter character = Charecter.Singleton();
        ArrayList<String> inventory = character.CharInventory;
        int wisdom = character.getWisdom();
        int alignmentValue = character.getAlignment();
        int maxSpells = 6;
        int currentGuildSpells = getGuildSpellsCount();

        if (!isMember) {
            JOptionPane.showMessageDialog(this, "You must be a member of the Aurora Arcanum to buy guild spells.");
            return;
        }

        if (!inventory.contains("Aurora Arcanum Guild Ring")) {
            JOptionPane.showMessageDialog(this, "You need the Aurora Arcanum Guild Ring to buy guild spells.");
            return;
        }

        if (currentGuildSpells >= maxSpells) {
            JOptionPane.showMessageDialog(this, "You cannot have more than " + maxSpells + " guild spells.");
            return;
        }

        if (wisdom <= 0) {
            JOptionPane.showMessageDialog(this, "You need sufficient wisdom to buy guild spells.");
            return;
        }

        if (alignmentValue > 100) {
            JOptionPane.showMessageDialog(this, "Your alignment is good. You can buy guild spells.");
        } else if (alignmentValue < 100) {
            JOptionPane.showMessageDialog(this, "Your alignment is evil. You cannot buy guild spells.");
            return;
        }

        String newSpell = "New Guild Spell";
        addGuildSpell(newSpell);
        JOptionPane.showMessageDialog(this, "You have successfully bought the guild spell: " + newSpell);
    }

    private void reloadPanel() throws IOException, InterruptedException, ParseException {
        removeAll();
        revalidate();
        repaint();
        add(new AuroraArcanum(isMember));
    }

    public String getDescription() {
        return description;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public String getGuildName() {
        return guildName;
    }

    public boolean removeGuildSpell(String spell) {
        Charecter character = Charecter.Singleton();
        ArrayList<String> guildSpells = getGuildSpells();
        if (guildSpells.contains(spell)) {
            guildSpells.remove(spell);
            return true;
        }
        return false;
    }

    public int getGuildSpellsCount() {
        Charecter character = Charecter.Singleton();
        return character.GuildSpells.size();
    }

    public void addGuildSpell(String spell) {
        Charecter character = Charecter.Singleton();
        ArrayList<String> guildSpells = character.GuildSpells;
        if (guildSpells.size() < 6) { // Assuming a maximum of 6 spells
            guildSpells.add(spell);
        } else {
            JOptionPane.showMessageDialog(this, "You cannot add more than 6 guild spells.");
        }
    }

    public ArrayList<String> getGuildSpells() {
        return new ArrayList<>(Charecter.Singleton().GuildSpells); // Return a copy of the GuildSpells list
    }
}
