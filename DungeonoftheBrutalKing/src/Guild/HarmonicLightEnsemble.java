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

import Alignment.Alignment;
import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;

public class HarmonicLightEnsemble extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int BASE_SPELL_LIMIT = 0;
    private static final int MAX_SPELL_LIMIT = 6;
    private static final int SPELL_COST = 50;

    private final String guildName = "Harmonic Light Ensemble";
    private boolean isMember;
    private String description = "The Harmonic Light Ensemble is a guild of celestial sorcerers who bring balance and harmony to the realm.";
    private Alignment alignment;

    public HarmonicLightEnsemble(boolean isMember) throws IOException, InterruptedException, ParseException {
        this.isMember = isMember;
        this.alignment = Alignment.POSITIVE;

        setLayout(new BorderLayout());

        Charecter character = Charecter.getInstance();
        ArrayList<String> inventory = new ArrayList<>(character.getCharInventory());

        if (!isMember && !inventory.contains("Harmonic Light Ensemble Guild Ring")) {
            int choice = JOptionPane.showOptionDialog(
                this,
                "You are not a member of the Harmonic Light Ensemble. Would you like to join?",
                "Join Guild",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Join", "Stay/Leave"},
                "Join"
            );

            if (choice == JOptionPane.YES_OPTION) {
                this.isMember = true;
                character.addToInventory("Harmonic Light Ensemble Guild Ring");
                JOptionPane.showMessageDialog(this, "You have joined the Harmonic Light Ensemble and received the Harmonic Light Ensemble Guild Ring!");
            } else {
                JOptionPane.showMessageDialog(this, "You chose not to join the guild.");
                return;
            }
        }

        if (!isMember) {
            MainGameScreen.getInstance().setMessageTextPane(description);
        }

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/DungeonoftheBrutalKing/Images/HarmonicLightEnsemble.jpg")));
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
                Charecter.getInstance().addToInventory("Harmonic Light Ensemble Guild Ring");
                JOptionPane.showMessageDialog(this, "You have joined the Harmonic Light Ensemble!");
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
        Charecter character = Charecter.getInstance();
        ArrayList<String> spells = new ArrayList<>(character.getGuildSpells());
        int wisdom = character.getWisdom();
        int gold = character.getGold();

        if (!isMember) {
            JOptionPane.showMessageDialog(this, "You must be a member of the Harmonic Light Ensemble to buy guild spells.");
            return;
        }

        if (!character.getCharInventory().contains("Harmonic Light Ensemble Guild Ring")) {
            JOptionPane.showMessageDialog(this, "You need the Harmonic Light Ensemble Guild Ring to buy guild spells.");
            return;
        }

        if (gold < SPELL_COST) {
            JOptionPane.showMessageDialog(this, "You do not have enough gold to buy a spell.");
            return;
        }

        int spellLimit = BASE_SPELL_LIMIT + Math.min(wisdom / 8, MAX_SPELL_LIMIT - BASE_SPELL_LIMIT);

        if (spells.size() >= spellLimit) {
            JOptionPane.showMessageDialog(this, "You cannot carry more spells.");
            return;
        }

        String newSpell = JOptionPane.showInputDialog(this, "Enter the name of the spell to buy:");
        if (newSpell != null && !newSpell.isEmpty()) {
            character.getGuildSpells().add(newSpell);
            character.setGold(gold - SPELL_COST);
            JOptionPane.showMessageDialog(this, "You purchased the spell: " + newSpell);
        }
    }

    private void reloadPanel() throws IOException, InterruptedException, ParseException {
        removeAll();
        revalidate();
        repaint();
        add(new HarmonicLightEnsemble(isMember));
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

    public int getGuildSpellsCount() {
        return Charecter.getInstance().getGuildSpells().size();
    }

    public void addGuildSpell(String spell) {
        if (Charecter.getInstance().getGuildSpells().size() < MAX_SPELL_LIMIT) {
            Charecter.getInstance().getGuildSpells().add(spell);
        } else {
            JOptionPane.showMessageDialog(this, "You cannot add more than " + MAX_SPELL_LIMIT + " guild spells.");
        }
    }

    public boolean removeGuildSpell(String spell) {
        return Charecter.getInstance().getGuildSpells().remove(spell);
    }

    public ArrayList<String> getGuildSpells() {
        return new ArrayList<>(Charecter.getInstance().getGuildSpells());
    }
}
