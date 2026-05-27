
// File: `src/DungeonoftheBrutalKing/Guild/CelestialArcaneOrder/CelestialArcaneOrder.java`
package DungeonoftheBrutalKing.Guild.CelestialArcaneOrder;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Spells.SpellFactory;
import DungeonoftheBrutalKing.Spells.SpellBalanceManager;
import DungeonoftheBrutalKing.Guild.CelestialArcaneOrder.Spells.CelestialArcaneOrderGuildSpellsManager;

public class CelestialArcaneOrder extends JPanel {

    private static final long serialVersionUID = 1L;

    private final String guildName = "Celestial Arcane Order";
    private boolean isMember;
    private final String description;
    private final Alignment alignment = Alignment.GOOD;
    private final GuildType guildType = GuildType.CLERIC;

    public CelestialArcaneOrder(boolean isMember) throws IOException, InterruptedException, ParseException {
        this.isMember = isMember;
        this.description =
                "The Celestial Arcane Order is a guild of clerics who study the stars and wield cosmic magic for the good of the realm.";

        setLayout(new BorderLayout());

        Character character = Character.getInstance();
        ArrayList<String> inventory = new ArrayList<>(character.getCharInventory());

        if (!this.isMember || !isGood(character.getAlignment())) {
        
            MainGameScreen.getInstance().setMessageTextPane(description);
        }

        if (!this.isMember && !inventory.contains("Celestial Arcane Order Guild Ring")) {
            if (!isGood(character.getAlignment())) {
                JOptionPane.showMessageDialog(
                        this,
                        "You are not good (`alignment >= 0`). The Celestial Arcane Order refuses you."
                );
                return;
            }

            int choice = JOptionPane.showOptionDialog(
                    this,
                    "You are not a member of the Celestial Arcane Order. Would you like to join?",
                    "Join Guild",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[] { "Join", "Stay/Leave" },
                    "Join"
            );

            if (choice == JOptionPane.YES_OPTION) {
                this.isMember = true;
                character.addToInventory("Celestial Arcane Order Guild Ring");
                JOptionPane.showMessageDialog(
                        this,
                        "You have joined the Celestial Arcane Order and received the Celestial Arcane Order Guild Ring!"
                );
            } else {
                JOptionPane.showMessageDialog(this, "You chose not to join the guild.");
                return;
            }
        }

        JLabel imageLabel = new JLabel(new ImageIcon(
                getClass().getResource("/DungeonoftheBrutalKing/Images/CelestialArcaneOrder.jpg")));
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

        if (!this.isMember) {
            JButton joinGuildButton = new JButton("Join Guild");
            joinGuildButton.addActionListener(e -> {
                Character ch = Character.getInstance();
                if (!isGood(ch.getAlignment())) {
                    JOptionPane.showMessageDialog(
                            this,
                            "You are not good (`alignment >= 0`). The Celestial Arcane Order refuses you."
                    );
                    return;
                }

                this.isMember = true;
                ch.addToInventory("Celestial Arcane Order Guild Ring");
                JOptionPane.showMessageDialog(this, "You have joined the Celestial Arcane Order!");
                try {
                    reloadPanel();
                } catch (IOException | InterruptedException | ParseException ex) {
                    ex.printStackTrace();
                }
            });
            buttonPanel.add(joinGuildButton);
        } else {
            if (!isGood(character.getAlignment())) {
                JOptionPane.showMessageDialog(
                        this,
                        "You are not good (`alignment >= 0`). You cannot use Celestial Arcane Order services."
                );
            } else {
                buttonPanel.add(buySpellsButton);
                buttonPanel.add(stargazeButton);
                buttonPanel.add(removeCurseButton);
                buttonPanel.add(sellItemsButton);
                buttonPanel.add(enterStorageButton);
                buttonPanel.add(eatFoodButton);
                buttonPanel.add(sleepBedButton);
            }
        }

        buttonPanel.add(exitRoomButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Keep ONLY one listener for Buy Spells.
        buySpellsButton.addActionListener(e -> buyGuildSpell());

        stargazeButton.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "You gaze at the stars and gain cosmic insight. (Celestial Arcane Order exclusive service)"
        ));

        removeCurseButton.addActionListener(e -> {
            removeCursesAndEffects();
            JOptionPane.showMessageDialog(this, "All curses and negative effects have been removed!");
        });

        sellItemsButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Selling items..."));
        enterStorageButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Accessing guild storage..."));
        eatFoodButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "You eat a nourishing meal and feel revitalized."));
        sleepBedButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "You rest in a celestial bed and recover your strength."));

        exitRoomButton.addActionListener(e -> {

try {
	MainGameScreen.getInstance().restoreOriginalPanel();
} catch (IOException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
} catch (InterruptedException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
} catch (ParseException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}

        });
    }
    private static boolean isGood(int alignmentValue) {
        return alignmentValue >= 0;
    }

    private void removeCursesAndEffects() {
        Character character = Character.getInstance();
        character.clearCurses();
        character.clearNegativeEffects();
    }

    private void buyGuildSpell() {
        // unchanged
    }

    private void reloadPanel() throws IOException, InterruptedException, ParseException {
        // NOTE: This is still conceptually problematic (self-nesting).
        // A proper fix is to ask MainGameScreen to swap the content panel.
        removeAll();
        revalidate();
        repaint();
        add(new CelestialArcaneOrder(isMember));
    }

    public Alignment getAlignment() { return alignment; }
    public String getDescription() { return description; }
    public String getGuildName() { return guildName; }
    public GuildType getGuildType() { return guildType; }
    public int getGuildSpellsCount() { return Character.getInstance().getGuildSpells().size(); }
    public void addGuildSpell(String spell) { /* unchanged */ }
    public boolean removeGuildSpell(String spell) { return Character.getInstance().getGuildSpells().remove(spell); }
    public ArrayList<String> getGuildSpells() { return new ArrayList<>(Character.getInstance().getGuildSpells()); }
}
