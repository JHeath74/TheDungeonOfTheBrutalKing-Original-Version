
// `src/Guild/CrimsonBlades/CrimsonBlades.java`
package Guild.CrimsonBlades;

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

public class CrimsonBlades extends JPanel {

    private static final long serialVersionUID = 1L;

    private final String guildName = "Crimson Blades";
    private boolean isMember;
    private final String description;
    private final Alignment alignment = Alignment.EVIL;
    private final GuildType guildType = GuildType.WARRIOR;

    public CrimsonBlades(boolean isMember) throws IOException, InterruptedException, ParseException {
        this.isMember = isMember;
        this.description = "The Crimson Blades are a fierce guild of warriors, renowned for their skill and honor in battle.";

        setLayout(new BorderLayout());

        Charecter character = Charecter.getInstance();
        ArrayList<String> inventory = new ArrayList<>(character.getCharInventory());

        // Show description on entry for non-members (and also for GOOD characters entering an EVIL-only guild).
        if (!this.isMember || !isEvil(character.getAlignment())) {
            MainGameScreen.getInstance().setMessageTextPane(description);
        }

        // EVIL-only guild: block join prompt early if player is GOOD.
        if (!this.isMember && !inventory.contains("Crimson Blades Guild Ring")) {
            if (!isEvil(character.getAlignment())) {
                JOptionPane.showMessageDialog(
                        this,
                        "You are not evil (`alignment < 0`). The Crimson Blades reject you."
                );
                return;
            }

            int choice = JOptionPane.showOptionDialog(
                    this,
                    "You are not a member of the Crimson Blades. Would you like to join?",
                    "Join Guild",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[] { "Join", "Stay/Leave" },
                    "Join"
            );

            if (choice == JOptionPane.YES_OPTION) {
                this.isMember = true;
                character.addToInventory("Crimson Blades Guild Ring");
                JOptionPane.showMessageDialog(
                        this,
                        "You have joined the Crimson Blades and received the Crimson Blades Guild Ring!"
                );
            } else {
                JOptionPane.showMessageDialog(this, "You chose not to join the guild.");
                return;
            }
        }

        JLabel imageLabel = new JLabel(new ImageIcon(
                getClass().getResource("/DungeonoftheBrutalKing/Images/CrimsonBlades.jpg")));
        add(imageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(9, 1, 10, 10));
        JButton buySpellsButton = new JButton("Buy Spells");
        JButton sharpenBladeButton = new JButton("Sharpen Blade");
        JButton removeCurseButton = new JButton("Remove Curses/Effects");
        JButton sellItemsButton = new JButton("Sell Items");
        JButton enterStorageButton = new JButton("Guild Storage");
        JButton eatFoodButton = new JButton("Eat Food");
        JButton sleepBedButton = new JButton("Sleep in Bed");
        JButton trainButton = new JButton("Train Skills");
        JButton exitRoomButton = new JButton("Exit Room");

        if (!this.isMember) {
            JButton joinGuildButton = new JButton("Join Guild");
            joinGuildButton.addActionListener(evt -> {
                Charecter ch = Charecter.getInstance();
                if (!isEvil(ch.getAlignment())) {
                    JOptionPane.showMessageDialog(
                            this,
                            "You are not evil (`alignment < 0`). The Crimson Blades reject you."
                    );
                    return;
                }

                this.isMember = true;
                ch.addToInventory("Crimson Blades Guild Ring");
                JOptionPane.showMessageDialog(this, "You have joined the Crimson Blades!");
                try {
                    reloadPanel();
                } catch (IOException | InterruptedException | ParseException ex) {
                    ex.printStackTrace();
                }
            });
            buttonPanel.add(joinGuildButton);
        } else {
            if (!isEvil(character.getAlignment())) {
                JOptionPane.showMessageDialog(
                        this,
                        "You are not evil (`alignment < 0`). You cannot use Crimson Blades services."
                );
            } else {
                buttonPanel.add(buySpellsButton);
                buttonPanel.add(sharpenBladeButton);
                buttonPanel.add(removeCurseButton);
                buttonPanel.add(sellItemsButton);
                buttonPanel.add(enterStorageButton);
                buttonPanel.add(eatFoodButton);
                buttonPanel.add(sleepBedButton);
                buttonPanel.add(trainButton);
            }
        }

        buttonPanel.add(exitRoomButton);
        add(buttonPanel, BorderLayout.SOUTH);

        buySpellsButton.addActionListener(evt -> buyGuildSpell());

        sharpenBladeButton.addActionListener(evt -> JOptionPane.showMessageDialog(
                this,
                "You sharpen your blade, ready for battle! (Crimson Blades exclusive service)"
        ));

        removeCurseButton.addActionListener(evt -> {
            removeCursesAndEffects();
            JOptionPane.showMessageDialog(this, "All curses and negative effects have been removed!");
        });

        sellItemsButton.addActionListener(evt -> JOptionPane.showMessageDialog(this, "Selling items..."));
        enterStorageButton.addActionListener(evt -> JOptionPane.showMessageDialog(this, "Accessing guild storage..."));
        eatFoodButton.addActionListener(evt -> JOptionPane.showMessageDialog(this, "You eat a hearty meal and feel invigorated."));
        sleepBedButton.addActionListener(evt -> JOptionPane.showMessageDialog(this, "You rest in a sturdy bed and recover your strength."));
        trainButton.addActionListener(evt -> JOptionPane.showMessageDialog(this, "You train rigorously, improving your skills."));

        exitRoomButton.addActionListener(evt -> {
            try {
                MainGameScreen.getInstance().restoreOriginalPanel();
            } catch (IOException | InterruptedException | ParseException ex) {
                ex.printStackTrace();
            }
        });
    }

    // Project-wide alignment rule: alignment < 0 == EVIL, alignment >= 0 == GOOD.
    private static boolean isEvil(int alignmentValue) {
        return alignmentValue < 0;
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
                    "You must be a member of the Crimson Blades to buy guild spells.");
            return;
        }

        if (!isEvil(character.getAlignment())) {
            JOptionPane.showMessageDialog(this,
                    "You are not evil (`alignment < 0`). You cannot buy guild spells here.");
            return;
        }

        if (!inventory.contains("Crimson Blades Guild Ring")) {
            JOptionPane.showMessageDialog(this,
                    "You need the Crimson Blades Guild Ring to buy guild spells.");
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

    public GuildType getGuildType() {
        return guildType;
    }

    public boolean removeGuildSpell(String spell) {
        return Charecter.getInstance().getGuildSpells().remove(spell);
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

    public ArrayList<String> getGuildSpells() {
        return new ArrayList<>(Charecter.getInstance().getGuildSpells());
    }
}
