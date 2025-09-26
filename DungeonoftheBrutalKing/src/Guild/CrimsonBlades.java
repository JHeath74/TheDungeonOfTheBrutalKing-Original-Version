
package Guild;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.Alignment;

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

        Charecter character = Charecter.getInstance();
        ArrayList<String> inventory = character.getCharInventory();

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
        Charecter character = Charecter.getInstance();
        ArrayList<String> guildSpells = character.getGuildSpells();
        if (guildSpells.contains(spell)) {
            guildSpells.remove(spell);
            return true;
        }
        return false;
    }

    public int getGuildSpellsCount() {
        Charecter character = Charecter.getInstance();
        return character.getGuildSpells().size();
    }

    public void addGuildSpell(String spell) {
        Charecter character = Charecter.getInstance();
        ArrayList<String> guildSpells = character.getGuildSpells();
        if (guildSpells.size() < 6) {
            guildSpells.add(spell);
        } else {
            JOptionPane.showMessageDialog(this, "You cannot add more than 6 guild spells.");
        }
    }

    public ArrayList<String> getGuildSpells() {
        Charecter character = Charecter.getInstance();
        return new ArrayList<>(character.getGuildSpells());
    }
    

private void useSkill() {
    Charecter character = Charecter.getInstance();
    int actionPoints = character.getActionPoints();
    int magicPoints = character.getMagicPoints();

    if (!isMember) {
        JOptionPane.showMessageDialog(this, "You must be a member of the Crimson Blades to use skills.");
        return;
    }

    if (actionPoints >= 10) {
        character.setActionPoints(actionPoints - 10);
        JOptionPane.showMessageDialog(this, "You used a skill! Remaining Action Points: " + (actionPoints - 10));
    } else if (magicPoints >= 10) {
        character.setMagicPoints(magicPoints - 10);
        JOptionPane.showMessageDialog(this, "You used a skill! Remaining Magic Points: " + (magicPoints - 10));
    } else {
        JOptionPane.showMessageDialog(this, "Not enough Action Points or Magic Points to use a skill!");
    }
}

}
