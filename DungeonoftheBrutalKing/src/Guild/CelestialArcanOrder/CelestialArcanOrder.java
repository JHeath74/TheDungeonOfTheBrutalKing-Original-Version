
// src/Guild/CelestialArcanOrder.java
package Guild.CelestialArcanOrder;

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
import SharedData.GuildType;

public class CelestialArcanOrder extends JPanel {

    private static final long serialVersionUID = 1L;

    private final String guildName = "Celestial Arcan Order";
    private boolean isMember;
    private final String description;
    private final Alignment alignment = Alignment.GOOD;
    private final GuildType guildType = GuildType.CLERIC;

    public CelestialArcanOrder(boolean isMember) throws IOException, InterruptedException, ParseException {
        this.isMember = isMember;
        this.description = "The Celestial Arcan Order is a guild of clerics who study the stars and wield cosmic magic for the good of the realm.";

        setLayout(new BorderLayout());

        Charecter character = Charecter.getInstance();
        ArrayList<String> inventory = new ArrayList<>(character.getCharInventory());

        if (!isMember && !inventory.contains("Celestial Arcan Order Guild Ring")) {
            int choice = JOptionPane.showOptionDialog(
                this,
                "You are not a member of the Celestial Arcan Order. Would you like to join?",
                "Join Guild",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Join", "Stay/Leave"},
                "Join"
            );

            if (choice == JOptionPane.YES_OPTION) {
                this.isMember = true;
                character.addToInventory("Celestial Arcan Order Guild Ring");
                JOptionPane.showMessageDialog(this, "You have joined the Celestial Arcan Order and received the Celestial Arcan Order Guild Ring!");
            } else {
                JOptionPane.showMessageDialog(this, "You chose not to join the guild.");
                return;
            }
        }

        if (!isMember) {
            MainGameScreen.getInstance().setMessageTextPane(description);
        }

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/DungeonoftheBrutalKing/Images/CelestialArcanOrder.jpg")));
        add(imageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(9, 1, 10, 10));
        JButton buySpellsButton = new JButton("Buy Spells");
        JButton stargazeButton = new JButton("Stargaze (Celestial Insight)");
        JButton removeCurseButton = new JButton("Remove Curses/Effects");
        JButton sellItemsButton = new JButton("Sell Items");
        JButton enterStorageButton = new JButton("Guild Storage");
        JButton eatFoodButton = new JButton("Eat Food");
        JButton sleepBedButton = new JButton("Sleep in Bed");
        JButton exitRoomButton = new JButton("Exit Room");

        if (!isMember) {
            JButton joinGuildButton = new JButton("Join Guild");
            joinGuildButton.addActionListener(event -> {
                this.isMember = true;
                Charecter.getInstance().addToInventory("Celestial Arcan Order Guild Ring");
                JOptionPane.showMessageDialog(this, "You have joined the Celestial Arcan Order!");
                try {
                    reloadPanel();
                } catch (IOException | InterruptedException | ParseException ex) {
                    ex.printStackTrace();
                }
            });
            buttonPanel.add(joinGuildButton);
        } else {
            buttonPanel.add(buySpellsButton);
            buttonPanel.add(stargazeButton);
            buttonPanel.add(removeCurseButton);
            buttonPanel.add(sellItemsButton);
            buttonPanel.add(enterStorageButton);
            buttonPanel.add(eatFoodButton);
            buttonPanel.add(sleepBedButton);
        }
        buttonPanel.add(exitRoomButton);

        add(buttonPanel, BorderLayout.SOUTH);

        buySpellsButton.addActionListener(event -> buyGuildSpell());
        stargazeButton.addActionListener(event -> JOptionPane.showMessageDialog(this, "You gaze at the stars and gain cosmic insight. (Celestial Arcan Order exclusive service)"));
        removeCurseButton.addActionListener(event -> {
            removeCursesAndEffects();
            JOptionPane.showMessageDialog(this, "All curses and negative effects have been removed!");
        });
        sellItemsButton.addActionListener(event -> JOptionPane.showMessageDialog(this, "Selling items..."));
        enterStorageButton.addActionListener(event -> JOptionPane.showMessageDialog(this, "Accessing guild storage..."));
        eatFoodButton.addActionListener(event -> JOptionPane.showMessageDialog(this, "You eat a nourishing meal and feel revitalized."));
        sleepBedButton.addActionListener(event -> JOptionPane.showMessageDialog(this, "You rest in a celestial bed and recover your strength."));
        exitRoomButton.addActionListener(event -> {
            try {
                MainGameScreen.getInstance().restoreOriginalPanel();
            } catch (IOException | InterruptedException | ParseException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void removeCursesAndEffects() {
        Charecter character = Charecter.getInstance();
        character.clearCurses();
        character.clearNegativeEffects();
    }

    private void buyGuildSpell() {
        Charecter character = Charecter.getInstance();
        ArrayList<String> inventory = new ArrayList<>(character.getCharInventory());
        int wisdom = character.getWisdom();
        int alignmentValue = character.getAlignment();
        int maxSpells = 6;
        int currentGuildSpells = getGuildSpellsCount();

        if (!isMember) {
            JOptionPane.showMessageDialog(this, "You must be a member of the Celestial Arcan Order to buy guild spells.");
            return;
        }

        if (!inventory.contains("Celestial Arcan Order Guild Ring")) {
            JOptionPane.showMessageDialog(this, "You need the Celestial Arcan Order Guild Ring to buy guild spells.");
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
        add(new CelestialArcanOrder(isMember));
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

    public GuildType getGuildType() {
        return guildType;
    }

    public int getGuildSpellsCount() {
        return Charecter.getInstance().getGuildSpells().size();
    }

    public void addGuildSpell(String spell) {
        if (Charecter.getInstance().getGuildSpells().size() < 6) {
            Charecter.getInstance().getGuildSpells().add(spell);
        } else {
            JOptionPane.showMessageDialog(this, "You cannot add more than 6 guild spells.");
        }
    }

    public boolean removeGuildSpell(String spell) {
        return Charecter.getInstance().getGuildSpells().remove(spell);
    }

    public ArrayList<String> getGuildSpells() {
        return new ArrayList<>(Charecter.getInstance().getGuildSpells());
    }
}
