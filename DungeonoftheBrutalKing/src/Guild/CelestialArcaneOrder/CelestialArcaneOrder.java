
// `src/Guild/CelestialArcaneOrder/CelestialArcaneOrder.java`
package Guild.CelestialArcaneOrder;

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

        Charecter character = Charecter.getInstance();
        ArrayList<String> inventory = new ArrayList<>(character.getCharInventory());

        // Show description for non-members and also for EVIL characters entering a GOOD-only guild.
        if (!this.isMember || !isGood(character.getAlignment())) {
            MainGameScreen.getInstance().setMessageTextPane(description);
        }

        // GOOD-only guild: block join prompt early if player is EVIL.
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
            joinGuildButton.addActionListener(evt -> {
                Charecter ch = Charecter.getInstance();
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

        buySpellsButton.addActionListener(evt -> buyGuildSpell());

        stargazeButton.addActionListener(evt -> JOptionPane.showMessageDialog(
                this,
                "You gaze at the stars and gain cosmic insight. (Celestial Arcane Order exclusive service)"
        ));

        removeCurseButton.addActionListener(evt -> {
            removeCursesAndEffects();
            JOptionPane.showMessageDialog(this, "All curses and negative effects have been removed!");
        });

        sellItemsButton.addActionListener(evt -> JOptionPane.showMessageDialog(this, "Selling items..."));
        enterStorageButton.addActionListener(evt -> JOptionPane.showMessageDialog(this, "Accessing guild storage..."));
        eatFoodButton.addActionListener(evt -> JOptionPane.showMessageDialog(this, "You eat a nourishing meal and feel revitalized."));
        sleepBedButton.addActionListener(evt -> JOptionPane.showMessageDialog(this, "You rest in a celestial bed and recover your strength."));

        exitRoomButton.addActionListener(evt -> {
            try {
                MainGameScreen.getInstance().restoreOriginalPanel();
            } catch (IOException | InterruptedException | ParseException ex) {
                ex.printStackTrace();
            }
        });
    }

    // Project-wide alignment rule: alignment >= 0 == GOOD, alignment < 0 == EVIL.
    private static boolean isGood(int alignmentValue) {
        return alignmentValue >= 0;
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
        int maxSpells = 6;
        int currentGuildSpells = getGuildSpellsCount();

        if (!isMember) {
            JOptionPane.showMessageDialog(this,
                    "You must be a member of the Celestial Arcane Order to buy guild spells.");
            return;
        }

        if (!isGood(character.getAlignment())) {
            JOptionPane.showMessageDialog(this,
                    "You are not good (`alignment >= 0`). You cannot buy guild spells here.");
            return;
        }

        if (!inventory.contains("Celestial Arcane Order Guild Ring")) {
            JOptionPane.showMessageDialog(this,
                    "You need the Celestial Arcane Order Guild Ring to buy guild spells.");
            return;
        }

        if (currentGuildSpells >= maxSpells) {
            JOptionPane.showMessageDialog(this,
                    "You cannot have more than " + maxSpells + " guild spells.");
            return;
        }

        if (wisdom <= 0) {
            JOptionPane.showMessageDialog(this, "You need sufficient wisdom to buy guild spells.");
            return;
        }

        String newSpell = "New Guild Spell";
        addGuildSpell(newSpell);
        JOptionPane.showMessageDialog(this,
                "You have successfully bought the guild spell: " + newSpell);
    }

    private void reloadPanel() throws IOException, InterruptedException, ParseException {
        removeAll();
        revalidate();
        repaint();
        add(new CelestialArcaneOrder(isMember));
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
