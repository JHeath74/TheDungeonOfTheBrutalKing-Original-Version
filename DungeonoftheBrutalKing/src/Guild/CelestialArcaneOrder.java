
// src/Guild/CelestialArcaneOrder.java
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

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.Alignment;
import SharedData.GuildType;

public class CelestialArcaneOrder extends JPanel {

    private static final long serialVersionUID = 1L;

    private final String guildName = "Celestial Arcane Order";
    private boolean isMember;
    private String description = "";
    private Alignment alignment;

    public CelestialArcaneOrder(boolean isMember) throws IOException, InterruptedException, ParseException {
        this.isMember = isMember;
        this.alignment = Alignment.GOOD;
        GuildType guildType = GuildType.CLERIC;

        this.description = "The Celestial Arcane Order is a guild of benevolent mages dedicated to protecting the realm with powerful magic.";

        setLayout(new BorderLayout());

        Character character = Character.getInstance();
        ArrayList<String> inventory = new ArrayList<>(character.getCharInventory());

        if (!isMember && !inventory.contains("Celestial Arcane Order Guild Amulet")) {
            int choice = JOptionPane.showOptionDialog(
                this,
                "You are not a member of the Celestial Arcane Order. Would you like to join?",
                "Join Guild",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Join", "Stay/Leave"},
                "Join"
            );

            if (choice == JOptionPane.YES_OPTION) {
                this.isMember = true;
                character.addToInventory("Celestial Arcane Order Guild Amulet");
                JOptionPane.showMessageDialog(this, "You have joined the Celestial Arcane Order and received the Guild Amulet!");
            } else {
                JOptionPane.showMessageDialog(this, "You chose not to join the guild.");
                return;
            }
        }

        if (!isMember) {
            MainGameScreen.getInstance().setMessageTextPane(description);
        }

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/DungeonoftheBrutalKing/Images/CelestialArcaneOrder.jpg")));
        add(imageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        JButton buySpellsButton = new JButton("Buy Spells");
        JButton studyTomesButton = new JButton("Study Tomes");
        JButton enterLibraryButton = new JButton("Enter Library");
        JButton exitRoomButton = new JButton("Exit Room");

        if (!isMember) {
            JButton joinGuildButton = new JButton("Join Guild");
            joinGuildButton.addActionListener(event -> {
                this.isMember = true;
                Character.getInstance().addToInventory("Celestial Arcane Order Guild Amulet");
                JOptionPane.showMessageDialog(this, "You have joined the Celestial Arcane Order!");
                try {
                    reloadPanel();
                } catch (IOException | InterruptedException | ParseException ex) {
                    ex.printStackTrace();
                }
            });
            buttonPanel.add(joinGuildButton);
        } else {
            buttonPanel.add(buySpellsButton);
            buttonPanel.add(studyTomesButton);
            buttonPanel.add(enterLibraryButton);
        }
        buttonPanel.add(exitRoomButton);

        add(buttonPanel, BorderLayout.SOUTH);

        buySpellsButton.addActionListener(event -> buyGuildSpell());
        studyTomesButton.addActionListener(event -> JOptionPane.showMessageDialog(this, "You study ancient tomes and gain knowledge."));
        enterLibraryButton.addActionListener(event -> JOptionPane.showMessageDialog(this, "You enter the grand library of the Order."));
        exitRoomButton.addActionListener(event -> {
            try {
                MainGameScreen.getInstance().restoreOriginalPanel();
            } catch (IOException | InterruptedException | ParseException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void buyGuildSpell() {
        Character character = Character.getInstance();
        ArrayList<String> inventory = new ArrayList<>(character.getCharInventory());
        int intelligence = character.getIntelligence();
        int magicPoints = character.getMagicPoints();
        int alignmentValue = character.getAlignment();
        int maxSpells = 8;
        int currentGuildSpells = getGuildSpellsCount();

        if (!isMember) {
            JOptionPane.showMessageDialog(this, "You must be a member of the Celestial Arcane Order to buy guild spells.");
            return;
        }

        if (!inventory.contains("Celestial Arcane Order Guild Amulet")) {
            JOptionPane.showMessageDialog(this, "You need the Celestial Arcane Order Guild Amulet to buy guild spells.");
            return;
        }

        if (currentGuildSpells >= maxSpells) {
            JOptionPane.showMessageDialog(this, "You cannot have more than " + maxSpells + " guild spells.");
            return;
        }

        if (intelligence <= 0) {
            JOptionPane.showMessageDialog(this, "You need sufficient intelligence to buy guild spells.");
            return;
        }

        if (alignmentValue > 100) {
            JOptionPane.showMessageDialog(this, "Your alignment is good. You can buy guild spells.");
        } else if (alignmentValue < 100) {
            JOptionPane.showMessageDialog(this, "Your alignment is evil. You cannot buy guild spells.");
            return;
        }

        if (magicPoints < 10) {
            JOptionPane.showMessageDialog(this, "Not enough Magic Points to buy a guild spell!");
            return;
        }

        String newSpell = "Celestial Guild Spell";
        addGuildSpell(newSpell);
        character.setMagicPoints(magicPoints - 10);
        JOptionPane.showMessageDialog(this, "You have successfully bought the guild spell: " + newSpell + ". Remaining Magic Points: " + character.getMagicPoints());
    }

    private void reloadPanel() throws IOException, InterruptedException, ParseException {
        removeAll();
        revalidate();
        repaint();
        add(new CelestialArcaneOrder(isMember));
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
        if (Character.getInstance().getGuildSpells().remove(spell)) {
            return true;
        }
        return false;
    }

    public int getGuildSpellsCount() {
        return Character.getInstance().getGuildSpells().size();
    }

    public void addGuildSpell(String spell) {
        if (Character.getInstance().getGuildSpells().size() < 8) {
            Character.getInstance().getGuildSpells().add(spell);
        } else {
            JOptionPane.showMessageDialog(this, "You cannot add more than 8 guild spells.");
        }
    }

    public ArrayList<String> getGuildSpells() {
        return new ArrayList<>(Character.getInstance().getGuildSpells());
    }
}
