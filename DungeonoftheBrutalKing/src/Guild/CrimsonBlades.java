
package Guild;

import javax.swing.*;
import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.Alignment;
import DungeonoftheBrutalKing.Charecter;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class CrimsonBlades extends JPanel {

    private static final long serialVersionUID = 1L;

    private final String guildName = "Crimson Blades";
    private boolean isMember;
    private String description = "";
    private Alignment alignment;

    public CrimsonBlades(boolean isMember) throws IOException, InterruptedException, ParseException {
        this.isMember = isMember;
        this.alignment = Alignment.EVIL;
        this.description = "The Crimson Blades is a guild of skilled warriors who thrive on combat and power.";

        setLayout(new BorderLayout());

        Charecter character = Charecter.Singleton();
        ArrayList<String> inventory = character.CharInventory;

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
                inventory.add("Crimson Blades Guild Ring");
                JOptionPane.showMessageDialog(this, "You have joined the Crimson Blades and received the Crimson Blades Guild Ring!");
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
        JButton useSkillsButton = new JButton("Use Skills");
        JButton sellItemsButton = new JButton("Sell Items");
        JButton enterStorageButton = new JButton("Enter Storage");
        JButton exitRoomButton = new JButton("Exit Room");

        if (!isMember) {
            JButton joinGuildButton = new JButton("Join Guild");
            joinGuildButton.addActionListener(event -> {
                this.isMember = true;
                inventory.add("Crimson Blades Guild Ring");
                JOptionPane.showMessageDialog(this, "You have joined the Crimson Blades!");
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

        useSkillsButton.addActionListener(event -> useSkill());
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

    private void useSkill() {
        Charecter character = Charecter.Singleton();
        int actionPoints = character.getActionPoints();

        if (!isMember) {
            JOptionPane.showMessageDialog(this, "You must be a member of the Crimson Blades to use skills.");
            return;
        }

        if (actionPoints >= 10) {
            actionPoints -= 10;
            character.setActionPoints(actionPoints);
            JOptionPane.showMessageDialog(this, "You used a skill! Remaining Action Points: " + actionPoints);
        } else {
            JOptionPane.showMessageDialog(this, "Not enough Action Points to use a skill!");
        }
    }

    private void reloadPanel() throws IOException, InterruptedException, ParseException {
        removeAll();
        revalidate();
        repaint();
        add(new CrimsonBlades(isMember));
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
        if (guildSpells.size() < 6) {
            guildSpells.add(spell);
        } else {
            JOptionPane.showMessageDialog(this, "You cannot add more than 6 guild spells.");
        }
    }

    public ArrayList<String> getGuildSpells() {
        return new ArrayList<>(Charecter.Singleton().GuildSpells);
    }
}
